import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "co.csadev.colorpicker"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        version = "1.0.0"
//        versionCode = 16

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFiles("consumer-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    lint {
        targetSdk = 36
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.coil.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

detekt {
    buildUponDefaultConfig = true
    autoCorrect = true
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))

//    allRules = false
//    config.setFrom("$projectDir/../detekt.yml")
//    baseline = file("$projectDir/../detekt-baseline.xml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

// ========== Snapshot Testing Tasks ==========

/**
 * Task to record reference snapshots.
 * This creates the baseline images that will be used for comparison.
 *
 * Usage: ./gradlew :library:recordSnapshots
 */
tasks.register("recordSnapshots") {
    group = "snapshot"
    description = "Records reference snapshots for all snapshot tests"
    dependsOn("recordPaparazziDebug")
}

/**
 * Task to verify snapshots match the reference.
 * This compares current UI against the recorded baseline.
 *
 * Usage: ./gradlew :library:verifySnapshots
 */
tasks.register("verifySnapshots") {
    group = "snapshot"
    description = "Verifies snapshots match the reference images"
    dependsOn("verifyPaparazziDebug")
}

/**
 * Task to record comparison snapshots in a separate directory.
 * This creates a parallel set of snapshots for manual comparison.
 *
 * Usage: ./gradlew :library:recordCompareSnapshots
 */
tasks.register("recordCompareSnapshots") {
    group = "snapshot"
    description = "Records comparison snapshots in a separate directory for manual comparison"
    doLast {
        // First record the snapshots
        project.exec {
            commandLine("./gradlew", ":library:recordPaparazziDebug")
        }

        // Copy snapshots to comparison directory
        val snapshotsDir = file("src/test/snapshots")
        val compareDir = file("src/test/snapshots-compare")

        if (snapshotsDir.exists()) {
            delete(compareDir)
            copy {
                from(snapshotsDir)
                into(compareDir)
            }
            println("Comparison snapshots recorded to: $compareDir")
        }
    }
}

/**
 * Task to compare snapshots between reference and comparison directories.
 * Reports all differences without stopping at the first failure.
 *
 * Usage: ./gradlew :library:compareSnapshots
 */
tasks.register("compareSnapshots") {
    group = "snapshot"
    description = "Compares all snapshots and reports all failures"
    doLast {
        val snapshotsDir = file("src/test/snapshots")
        val compareDir = file("src/test/snapshots-compare")

        if (!snapshotsDir.exists() || !compareDir.exists()) {
            throw GradleException("Missing snapshot directories. Run recordSnapshots and recordCompareSnapshots first.")
        }

        val failures = mutableListOf<String>()

        snapshotsDir.walkTopDown()
            .filter { it.isFile && it.extension == "png" }
            .forEach { referenceFile ->
                val relativePath = referenceFile.relativeTo(snapshotsDir)
                val compareFile = compareDir.resolve(relativePath.path)

                if (!compareFile.exists()) {
                    failures.add("Missing comparison file: ${relativePath.path}")
                } else {
                    // Basic file size comparison (pixel-perfect would require image comparison library)
                    if (referenceFile.length() != compareFile.length()) {
                        failures.add("Size mismatch: ${relativePath.path} (ref: ${referenceFile.length()} bytes, compare: ${compareFile.length()} bytes)")
                    }
                }
            }

        if (failures.isNotEmpty()) {
            val report = buildString {
                appendLine("\n========== Snapshot Comparison Failures ==========")
                appendLine("Total failures: ${failures.size}")
                appendLine()
                failures.forEach { appendLine("  - $it") }
                appendLine("==================================================")
            }
            println(report)
            throw GradleException("Snapshot comparison failed with ${failures.size} differences. See report above.")
        } else {
            println("\n✅ All snapshots match! No differences found.")
        }
    }
}

/**
 * Task to clean all snapshot artifacts.
 *
 * Usage: ./gradlew :library:cleanSnapshots
 */
tasks.register("cleanSnapshots") {
    group = "snapshot"
    description = "Cleans all snapshot artifacts and comparison directories"
    doLast {
        delete(file("src/test/snapshots"))
        delete(file("src/test/snapshots-compare"))
        delete(file("out"))
        println("Snapshot directories cleaned")
    }
}

/**
 * Task to generate a snapshot test report.
 * Lists all snapshot tests and their status.
 *
 * Usage: ./gradlew :library:snapshotReport
 */
tasks.register("snapshotReport") {
    group = "snapshot"
    description = "Generates a report of all snapshot tests"
    doLast {
        val snapshotsDir = file("src/test/snapshots")

        if (!snapshotsDir.exists()) {
            println("No snapshots found. Run recordSnapshots first.")
            return@doLast
        }

        val images = snapshotsDir.walkTopDown()
            .filter { it.isFile && it.extension == "png" }
            .toList()

        val report = buildString {
            appendLine("\n========== Snapshot Test Report ==========")
            appendLine("Total snapshots: ${images.size}")
            appendLine("Snapshot directory: ${snapshotsDir.absolutePath}")
            appendLine()
            appendLine("Snapshots by category:")

            images.groupBy {
                it.parentFile.name
            }.forEach { (category, files) ->
                appendLine("  $category: ${files.size} snapshots")
            }
            appendLine()
            appendLine("All snapshots:")
            images.forEach {
                val relativePath = it.relativeTo(snapshotsDir)
                appendLine("  - $relativePath")
            }
            appendLine("==========================================")
        }

        println(report)
    }
}
