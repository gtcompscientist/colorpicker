Color Picker
-------------
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Color%20Picker-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1693)
![https://img.shields.io/github/tag/QuadFlask/colorpicker.svg?label=maven](https://img.shields.io/github/tag/QuadFlask/colorpicker.svg?label=maven)

![icon](https://github.com/QuadFlask/colorpicker/blob/master/app/src/main/res/drawable-xxxhdpi/ic_launcher.png)

A modern, Compose-first Android color picker library with interactive color wheels, sliders, and comprehensive Material Design 3 support.

[<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Get_it_on_Google_play.svg/800px-Get_it_on_Google_play.svg.png" width="150px">](https://play.google.com/store/apps/details?id=co.csadev.colorpicker.sample)

[market link](https://play.google.com/store/apps/details?id=co.csadev.colorpicker.sample)

## Demo video

[Youtube](https://youtu.be/MwWi9X7eqNI)

## Screenshot

### WHEEL_TYPE.FLOWER

![screenshot3.png](https://github.com/QuadFlask/colorpicker/blob/master/screenshot/screenshot3.png)

### WHEEL_TYPE.CIRCLE

![screenshot.png](https://github.com/QuadFlask/colorpicker/blob/master/screenshot/screenshot.png)

## Features

- **100% Jetpack Compose** - Modern declarative UI
- **Reactive State Management** - Compose-native state with `@Stable` optimization
- **Flow-Based Events** - Reactive event handling with Kotlin SharedFlow
- **Material Design 3** - Full Material3 theming support
- **Multiple Wheel Types** - Flower (organic) and Circle (precise) rendering styles
- **Complete Customization** - Alpha, lightness, density, colors all configurable
- **Dialog & Preference Support** - Ready-to-use dialog and DataStore integration
- **Comprehensive Testing** - 50+ snapshot tests with Roborazzi
- **RTL Support** - Right-to-left layout support
- **Accessibility** - Full semantic annotations for screen readers

## Architecture

This library uses modern Android architecture patterns:

### State Management
- **ColorPickerState** - Immutable data class with `@Stable` annotation for Compose optimization
- **rememberColorPickerState()** - Composable state factory following Compose best practices
- Smart recomposition ensures only affected UI components update

### Event System
- **Flow-based events** - Uses Kotlin `SharedFlow` for reactive event handling
- **Two event types**:
  - `ColorChanged` - Emitted during user interaction (dragging, sliding)
  - `ColorSelected` - Emitted when user finalizes selection (touch release)
- Coroutine-friendly with suspend functions

### Rendering Engine
- **Strategy Pattern** - Pluggable renderers for different wheel types
- **Canvas-based** - High-performance hardware-accelerated drawing
- **HSV Color Space** - Intuitive hue-saturation-value selection
- Two implementations:
  - `FlowerColorWheelRenderer` - Organic petal-like appearance
  - `SimpleColorWheelRenderer` - Clean uniform circles

### UI Components
All components are Composable functions following Material Design 3:
- `ColorPicker` - Complete color picking interface
- `ColorWheel` - Interactive HSV color wheel
- `LightnessSlider` - Brightness adjustment
- `AlphaSlider` - Transparency control
- `ColorPreviewBox` - Visual color display
- `ColorPickerDialog` - Material3 AlertDialog wrapper
- `ColorPickerPreference` - DataStore preference integration

## Requirements

- **Minimum SDK**: Android API 24+ (Android 7.0)
- **Kotlin**: 2.2.20+
- **Compose BOM**: 2025.09.01+
- **Java**: 17+

## Installation

This library is not released in Maven Central, but you can use [JitPack](https://jitpack.io).

### Gradle (Kotlin DSL)

Add JitPack repository in your root `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Then add the library dependency in your module `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.QuadFlask:colorpicker:0.0.13")

    // Required dependencies
    implementation(platform("androidx.compose:compose-bom:2025.09.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
}
```

### Gradle (Groovy)

In `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```

In `build.gradle`:

```groovy
dependencies {
    implementation 'com.github.QuadFlask:colorpicker:0.0.13'

    // Required dependencies
    implementation platform('androidx.compose:compose-bom:2025.09.01')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.material3:material3'
    implementation 'androidx.datastore:datastore-preferences:1.1.1'
}
```

### Manual Installation

Alternatively, download the `.aar` from [releases](https://github.com/QuadFlask/colorpicker/releases) and add it to your project:

```kotlin
dependencies {
    implementation(files("libs/colorpicker.aar"))
}
```

## Usage

### Quick Start - Simple Color Picker

```kotlin
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import co.csadev.colorpicker.compose.ColorPicker

@Composable
fun MyScreen() {
    var selectedColor by remember { mutableStateOf(Color.Blue) }

    ColorPicker(
        initialColor = selectedColor,
        showAlphaSlider = true,
        showLightnessSlider = true,
        onColorSelected = { color ->
            selectedColor = color
        }
    )
}
```

### As a Dialog

```kotlin
@Composable
fun DialogExample() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.Green) }

    Button(onClick = { showDialog = true }) {
        Text("Pick Color")
    }

    if (showDialog) {
        ColorPickerDialog(
            onDismissRequest = { showDialog = false },
            onColorSelected = { color ->
                selectedColor = color
                showDialog = false
            },
            initialColor = selectedColor,
            title = "Choose a Color"
        )
    }
}
```

### With Event Handling

```kotlin
@Composable
fun EventHandlingExample() {
    var currentColor by remember { mutableStateOf(Color.Yellow) }
    var previewColor by remember { mutableStateOf(Color.Yellow) }

    Column {
        // Live preview while dragging
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(previewColor)
        )

        ColorPicker(
            initialColor = currentColor,
            onColorChanged = { color ->
                // Called while dragging
                previewColor = color
            },
            onColorSelected = { color ->
                // Called when released
                currentColor = color
                previewColor = color
            }
        )
    }
}
```

### As a Preference

```kotlin
@Composable
fun PreferencesExample() {
    val context = LocalContext.current
    val dataStore = remember { context.dataStore }
    val themeColor by dataStore.getColorFlow(
        key = "theme_color",
        defaultColor = Color.Blue
    ).collectAsState(initial = Color.Blue)
    val scope = rememberCoroutineScope()

    ColorPickerPreferenceItem(
        title = "Theme Color",
        summary = "Choose your app theme color",
        color = themeColor,
        onColorChange = { newColor ->
            scope.launch {
                dataStore.saveColor("theme_color", newColor)
            }
        }
    )
}
```

### Just the Color Wheel

```kotlin
@Composable
fun ColorWheelExample() {
    var selectedColor by remember { mutableStateOf(Color.Red) }

    ColorWheel(
        wheelType = ColorPickerState.WheelType.FLOWER,
        density = 12,
        lightness = 1f,
        alpha = 1f,
        onColorSelected = { color ->
            selectedColor = color
        }
    )
}
```

## Testing

This library includes comprehensive snapshot testing using [Roborazzi](https://github.com/takahirom/roborazzi) and the official Android Compose screenshot testing approach to ensure UI consistency:

- ✅ **~50 snapshot tests** covering all UI components
- ✅ **Multiple device types** (phones, tablets, various screen densities)
- ✅ **RTL support** (Arabic, Hebrew)
- ✅ **Light and dark themes** Material3 themes
- ✅ **Automated CI/CD** with GitHub Actions
- ✅ **PR-based snapshot updates** via `/record-snapshots` command

### Running Tests

```bash
# Record reference snapshots
./gradlew :library:recordSnapshots

# Verify snapshots match
./gradlew :library:verifySnapshots

# Generate snapshot report
./gradlew :library:snapshotReport
```

### Updating Snapshots in PRs

When snapshot tests fail in a PR and changes are intentional, simply comment:
```
/record-snapshots
```

GitHub Actions will automatically record and commit new snapshots to your PR!

For detailed testing documentation, see [library/SNAPSHOT_TESTING.md](library/SNAPSHOT_TESTING.md).

## Development

### Snapshot Testing

The library uses Roborazzi for snapshot testing to prevent visual regressions. All UI components are tested across multiple configurations using JVM-based tests (no emulator required).

**Test Coverage:**
- Component Tests (~25): Individual UI elements (ColorWheel, Sliders, PreviewBox)
- ColorPicker Tests (~10): Full picker configurations
- Dialog & Config Tests (~15): Dialogs, preferences, devices, themes, locales

See the [Snapshot Testing Guide](library/SNAPSHOT_TESTING.md) for more information.

## Documentation

For comprehensive guides and detailed information, see:

- **[HOW_TO.md](HOW_TO.md)** - Complete usage guide with all features and examples
  - Installation and setup
  - Basic and advanced usage patterns
  - State management techniques
  - Event handling
  - Preferences integration with DataStore
  - Migration from View-based library
  - Best practices and troubleshooting

- **[COLORWHEEL_USAGE.md](COLORWHEEL_USAGE.md)** - Detailed color wheel documentation
  - Flower vs Circle wheel types
  - Customization options (density, lightness, alpha)
  - Synchronized sliders
  - Performance tips
  - Responsive layouts

- **[library/SNAPSHOT_TESTING.md](library/SNAPSHOT_TESTING.md)** - Testing guide
  - Running snapshot tests
  - PR-based snapshot updates
  - Test coverage details
  - CI/CD integration
  - Contributing guidelines

- **[UpdateCompose.md](UpdateCompose.md)** - Architecture migration details
  - Migration strategy from View to Compose
  - Technical implementation details
  - Phase-by-phase breakdown
  - Breaking changes and compatibility

## Sample App

The repository includes a comprehensive sample app (`app/` module) demonstrating all features across 8 screens:

1. **SimplestExampleScreen** - Minimal usage
2. **FullFeaturedScreen** - All options enabled
3. **CustomizableScreen** - Individual components
4. **DialogsScreen** - Dialog configurations
5. **SlidersExampleScreen** - Standalone sliders
6. **EventHandlingScreen** - Live vs confirmed colors
7. **ComparisonScreen** - Wheel type comparison
8. **PreferencesScreen** - DataStore persistence

Run the sample app to see all features in action!

## Project Status

**✅ Completed:**
- ✅ Migrated to Jetpack Compose
- ✅ Modern Kotlin architecture with coroutines and Flow
- ✅ Material Design 3 integration
- ✅ Comprehensive snapshot testing (50+ tests)
- ✅ DataStore preferences support
- ✅ CI/CD with GitHub Actions
- ✅ Full documentation

**🚧 In Progress / Future:**
- Performance optimization for high-density wheels (100+ density)
- Gradle Maven Central publishing
- Compose Multiplatform support
- Custom color palette management
- Color harmony suggestions (complementary, triadic, etc.)

## License

```
Copyright 2014-2017 QuadFlask

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
