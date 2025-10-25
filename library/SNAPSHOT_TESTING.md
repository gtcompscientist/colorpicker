# Snapshot Testing Guide

This document describes the comprehensive snapshot testing setup for the ColorPicker library using Paparazzi.

## Table of Contents

- [Overview](#overview)
- [What is Snapshot Testing?](#what-is-snapshot-testing)
- [Test Coverage](#test-coverage)
- [Directory Structure](#directory-structure)
- [Running Tests](#running-tests)
- [Gradle Tasks](#gradle-tasks)
- [CI/CD Integration](#cicd-integration)
- [Adding New Snapshot Tests](#adding-new-snapshot-tests)
- [Troubleshooting](#troubleshooting)

## Overview

The ColorPicker library uses [Paparazzi](https://github.com/cashapp/paparazzi) for snapshot testing. Paparazzi is a JVM-based snapshot testing library that renders Android UI components without requiring an emulator or physical device.

**Key Features:**
- ✅ Fast execution (JVM-based, no emulator required)
- ✅ Pixel-perfect UI regression detection
- ✅ Multiple device configurations
- ✅ Theme and locale testing
- ✅ Automated CI/CD integration

## What is Snapshot Testing?

Snapshot testing captures rendered images (snapshots) of UI components and compares them against reference images. If the UI changes, the test will fail, alerting developers to visual regressions.

**Benefits:**
- Catch unintended visual changes
- Document expected UI appearance
- Verify UI across different configurations
- Prevent regressions when refactoring

## Test Coverage

Our snapshot tests provide comprehensive coverage across multiple dimensions:

### 1. Component Tests (`ComponentSnapshotTests.kt`)

Tests individual UI components in isolation:

**ColorWheel:**
- Flower wheel type (default, low density, high density, various lightness)
- Circle wheel type (default, low density, high density, various lightness)
- With transparency

**Sliders:**
- LightnessSlider (full, medium, low brightness, different colors)
- AlphaSlider (full, half, low opacity, different colors)

**ColorPreviewBox:**
- Various solid colors (red, blue, green, custom)
- Transparent colors
- Black and white

**Total:** ~35 component snapshots

### 2. ColorPicker Tests (`ColorPickerSnapshotTests.kt`)

Tests the complete ColorPicker component with various configurations:

- Full-featured (all options enabled)
- Flower vs. Circle wheel types
- Without color edit field
- Without alpha slider
- Without lightness slider
- Without sliders
- Wheel only
- Without wheel (sliders + preview only)
- Low and high density settings
- Various initial colors (RGB, custom, black, white, transparent)

**Total:** ~15 ColorPicker snapshots

### 3. Dialog Tests (`DialogSnapshotTests.kt`)

Tests ColorPicker dialogs and preference items:

**ColorPickerDialog:**
- Full-featured with flower/circle wheels
- Without color edit
- Without alpha slider
- Minimal configuration
- Custom button text
- Various initial colors

**ColorPickerPreferenceItem:**
- Enabled with summary
- Enabled without summary
- Disabled state
- Multiple items in a list
- Various colors

**Total:** ~15 dialog and preference snapshots

### 4. Device Configuration Tests (`DeviceConfigurationSnapshotTests.kt`)

Tests rendering across different devices and configurations:

**Devices:**
- Pixel 5 (default)
- Nexus 4 (small phone)
- Pixel 6 Pro (large phone)
- Nexus 7 (tablet)
- Nexus 10 (large tablet)

**Orientations:**
- Portrait
- Landscape

**Screen Densities:**
- HDPI
- XXHDPI
- XXXHDPI

**Total:** ~20 device configuration snapshots

### 5. Locale Tests (`LocaleSnapshotTests.kt`)

Tests rendering with different languages and locales:

**Languages Covered:**
- English (US)
- Spanish (Spain)
- French (France)
- German (Germany)
- Italian (Italy)
- Portuguese (Brazil)
- Japanese (Japan)
- Korean (South Korea)
- Chinese Simplified (China)
- Chinese Traditional (Taiwan)
- Arabic (Saudi Arabia) - RTL
- Hebrew (Israel) - RTL
- Russian (Russia)
- Hindi (India)

**Total:** ~14 locale snapshots

### 6. Theme Tests (`ThemeSnapshotTests.kt`)

Tests rendering with different Material3 themes:

**Themes:**
- Light theme (default)
- Dark theme
- Custom blue theme
- Custom purple theme
- Custom green theme
- Custom dark blue theme
- High contrast theme

**Total:** ~12 theme snapshots

## Grand Total: ~110+ Snapshot Tests

## Directory Structure

```
library/
├── src/
│   └── test/
│       ├── java/
│       │   └── co/csadev/colorpicker/
│       │       └── snapshots/
│       │           ├── ComponentSnapshotTests.kt
│       │           ├── ColorPickerSnapshotTests.kt
│       │           ├── DialogSnapshotTests.kt
│       │           ├── DeviceConfigurationSnapshotTests.kt
│       │           ├── LocaleSnapshotTests.kt
│       │           └── ThemeSnapshotTests.kt
│       ├── snapshots/               # Reference snapshots (baseline)
│       │   ├── images/
│       │   │   ├── co.csadev.colorpicker.snapshots_ComponentSnapshotTests_colorWheel_flowerType_default.png
│       │   │   ├── co.csadev.colorpicker.snapshots_ComponentSnapshotTests_colorWheel_circleType_default.png
│       │   │   └── ... (110+ PNG files)
│       │   └── ...
│       └── snapshots-compare/       # Comparison snapshots (for manual review)
│           └── ... (same structure as snapshots/)
└── SNAPSHOT_TESTING.md              # This file
```

## Running Tests

### Prerequisites

Ensure you have:
- JDK 17 or higher
- Gradle 8.x

No emulator or Android device is required!

### Basic Commands

#### 1. Record Reference Snapshots

Create or update the baseline snapshots:

```bash
./gradlew :library:recordSnapshots
```

This generates PNG images in `library/src/test/snapshots/`.

#### 2. Verify Snapshots

Compare current UI against the baseline:

```bash
./gradlew :library:verifySnapshots
```

This will fail if any snapshot doesn't match the reference.

#### 3. View Test Results

After running tests, view the HTML report:

```bash
open library/build/reports/paparazzi/index.html
```

## Gradle Tasks

All snapshot-related tasks are in the `snapshot` group:

### Core Tasks

| Task | Description |
|------|-------------|
| `recordSnapshots` | Records reference snapshots (baseline) |
| `verifySnapshots` | Verifies snapshots match references |
| `recordCompareSnapshots` | Records snapshots in a separate directory for comparison |
| `compareSnapshots` | Compares reference vs. comparison snapshots |
| `cleanSnapshots` | Removes all snapshot artifacts |
| `snapshotReport` | Generates a report of all snapshots |

### Task Examples

#### Record baseline snapshots:
```bash
./gradlew :library:recordSnapshots
```

#### Verify snapshots match:
```bash
./gradlew :library:verifySnapshots
```

#### Record comparison snapshots:
```bash
./gradlew :library:recordCompareSnapshots
```

#### Compare all snapshots and report failures:
```bash
./gradlew :library:compareSnapshots
```

This task reports **all** failures, not just the first one.

#### Clean snapshot directories:
```bash
./gradlew :library:cleanSnapshots
```

#### Generate snapshot report:
```bash
./gradlew :library:snapshotReport
```

### Workflow for Manual Comparison

1. Record current baseline:
   ```bash
   ./gradlew :library:recordSnapshots
   ```

2. Make UI changes in your code

3. Record comparison snapshots:
   ```bash
   ./gradlew :library:recordCompareSnapshots
   ```

4. Compare snapshots:
   ```bash
   ./gradlew :library:compareSnapshots
   ```

5. If changes are intentional, update baseline:
   ```bash
   ./gradlew :library:recordSnapshots
   ```

## CI/CD Integration

### GitHub Actions

The project includes a GitHub Actions workflow (`.github/workflows/snapshot-tests.yml`) that:

1. Runs on every pull request
2. Verifies all snapshots match the baseline
3. Reports all failures (doesn't stop at first failure)
4. Uploads failure reports as artifacts
5. Fails the build if any snapshot doesn't match

### CI Workflow Steps

```yaml
- Checkout code
- Setup JDK 17
- Grant execute permissions to gradlew
- Run snapshot verification
- Upload failure reports (if any)
- Fail build on mismatch
```

### Viewing CI Results

If snapshot tests fail in CI:

1. Check the workflow run logs
2. Download the failure report artifacts
3. Review the differences
4. Either fix the regression or update the baseline

## Adding New Snapshot Tests

### 1. Choose the Appropriate Test File

- **Individual components** → `ComponentSnapshotTests.kt`
- **Full ColorPicker** → `ColorPickerSnapshotTests.kt`
- **Dialogs/Preferences** → `DialogSnapshotTests.kt`
- **Device configs** → `DeviceConfigurationSnapshotTests.kt`
- **Locales** → `LocaleSnapshotTests.kt`
- **Themes** → `ThemeSnapshotTests.kt`

### 2. Add a New Test Function

Example:

```kotlin
@Test
fun colorWheel_newConfiguration() {
    paparazzi.snapshot {
        MaterialTheme {
            Surface {
                Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                    ColorWheel(
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        density = 12,
                        lightness = 0.8f,
                        alpha = 1f
                    )
                }
            }
        }
    }
}
```

### 3. Record the New Snapshot

```bash
./gradlew :library:recordSnapshots
```

### 4. Verify the Snapshot

```bash
./gradlew :library:verifySnapshots
```

### 5. Commit the Test and Snapshot

```bash
git add library/src/test/java/co/csadev/colorpicker/snapshots/
git add library/src/test/snapshots/
git commit -m "Add snapshot test for new configuration"
```

## Best Practices

### 1. Descriptive Test Names

Use clear, descriptive test names that indicate what's being tested:

✅ Good:
```kotlin
@Test
fun colorWheel_flowerType_darkLightness()
```

❌ Bad:
```kotlin
@Test
fun test1()
```

### 2. Test One Thing at a Time

Each test should focus on a single variation:

✅ Good:
```kotlin
@Test
fun lightnessSlider_mediumBrightness()

@Test
fun lightnessSlider_lowBrightness()
```

❌ Bad:
```kotlin
@Test
fun allSliderVariations() {
    // Tests 10 different configurations
}
```

### 3. Use Consistent Sizing

Ensure components have appropriate sizes for snapshot rendering:

```kotlin
Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
    // Component here
}
```

### 4. Keep Snapshots Organized

- Group related tests in the same file
- Use consistent naming conventions
- Document what each test verifies

### 5. Update Snapshots Intentionally

Only update baseline snapshots when visual changes are intentional:

1. Review the diff
2. Verify the change is correct
3. Update the baseline
4. Commit both code and snapshots

## Troubleshooting

### Problem: Snapshots don't match after UI changes

**Solution:** If the changes are intentional, update the baseline:

```bash
./gradlew :library:recordSnapshots
```

### Problem: Tests fail in CI but pass locally

**Possible causes:**
- Different JDK versions
- Different OS (Paparazzi is JVM-based and should be consistent)
- Uncommitted snapshot changes

**Solution:** Ensure JDK 17 is used both locally and in CI.

### Problem: Can't find snapshot images

**Location:** Snapshots are stored in `library/src/test/snapshots/images/`

**Solution:** Run `recordSnapshots` to generate them:

```bash
./gradlew :library:recordSnapshots
```

### Problem: Test names are too long

Paparazzi generates filenames from test class and method names. If too long:

**Solution:** Shorten test method names or use `@TestName` annotation.

### Problem: Snapshot directory is huge

**Cause:** Many high-resolution snapshots

**Solutions:**
- Add `snapshots/` to `.gitattributes` with LFS
- Compress PNG files (Paparazzi already optimizes them)
- Ensure only necessary snapshots are committed

### Problem: Flaky snapshot tests

**Possible causes:**
- Animations
- Random data
- Time-dependent rendering

**Solutions:**
- Disable animations in tests
- Use fixed seeds for random data
- Mock time-dependent values

## Performance Tips

### 1. Run Tests in Parallel

Paparazzi tests run on the JVM and can execute in parallel:

```bash
./gradlew :library:verifySnapshots --parallel
```

### 2. Run Specific Test Classes

To run only one test file:

```bash
./gradlew :library:testDebugUnitTest --tests "co.csadev.colorpicker.snapshots.ComponentSnapshotTests"
```

### 3. Use Gradle Build Cache

Enable the build cache for faster subsequent runs:

```bash
./gradlew :library:verifySnapshots --build-cache
```

## Resources

- [Paparazzi Documentation](https://github.com/cashapp/paparazzi)
- [Material3 Theme Documentation](https://m3.material.io/)
- [Compose Testing Documentation](https://developer.android.com/jetpack/compose/testing)

## Snapshot Test Statistics

| Category | Test Count | Description |
|----------|-----------|-------------|
| **Component Tests** | ~35 | Individual UI components |
| **ColorPicker Tests** | ~15 | Full ColorPicker configurations |
| **Dialog Tests** | ~15 | Dialogs and preferences |
| **Device Config Tests** | ~20 | Various devices and configurations |
| **Locale Tests** | ~14 | Multiple languages and RTL |
| **Theme Tests** | ~12 | Light, dark, and custom themes |
| **TOTAL** | **~110+** | **Comprehensive coverage** |

## Contributing

When adding new features or modifying UI:

1. Write snapshot tests for new components
2. Update existing snapshots if visual changes are intentional
3. Run all snapshot tests before submitting PR
4. Include both test code and snapshot images in commits
5. Document any new test categories in this guide

## License

The snapshot tests are part of the ColorPicker library and follow the same license.
