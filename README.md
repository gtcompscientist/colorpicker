# Color Picker for Jetpack Compose

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Color%20Picker-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1693)
![https://img.shields.io/github/tag/QuadFlask/colorpicker.svg?label=maven](https://img.shields.io/github/tag/QuadFlask/colorpicker.svg?label=maven)

![icon](https://github.com/QuadFlask/colorpicker/blob/master/app/src/main/res/drawable-xxxhdpi/ic_launcher.png)

Modern Jetpack Compose color picker library with color wheel, lightness slider, and alpha control.

**Pure Compose implementation** - No View-based components!

[<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Get_it_on_Google_play.svg/800px-Get_it_on_Google_play.svg.png" width="150px">](https://play.google.com/store/apps/details?id=co.csadev.colorpicker.sample)

[market link](https://play.google.com/store/apps/details?id=co.csadev.colorpicker.sample)

## Demo video

[Youtube](https://youtu.be/MwWi9X7eqNI)

## Screenshots

### WHEEL_TYPE.FLOWER

![screenshot3.png](https://github.com/QuadFlask/colorpicker/blob/master/screenshot/screenshot3.png)

### WHEEL_TYPE.CIRCLE

![screenshot.png](https://github.com/QuadFlask/colorpicker/blob/master/screenshot/screenshot.png)

## Features

- **Pure Jetpack Compose** - No XML layouts or View-based code
- **Material 3 Design** - Follows latest Material Design guidelines
- **Two wheel styles** - FLOWER (organic) and CIRCLE (uniform)
- **Full color control** - Hue, saturation, lightness, and alpha
- **State management** - Built-in state handling with Compose
- **Customizable** - Extensive customization options
- **Kotlin-first** - Modern Kotlin API with coroutines support
- **DataStore integration** - Easy persistence with Jetpack DataStore
- **Accessibility** - Semantic properties for screen readers

## Requirements

- **Android API 24+**
- **Kotlin 1.9+**
- **Jetpack Compose BOM 2025.09.01+**

## How to add dependency?

This library is not released in Maven Central, but instead you can use [JitPack](https://jitpack.io)

Add remote maven url in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Then add the library dependency in `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.QuadFlask:colorpicker:1.0.0-compose")
}
```

> Check out latest version at [releases](https://github.com/QuadFlask/colorpicker/releases)

## Quick Start

### Simple Color Picker

```kotlin
@Composable
fun MyScreen() {
    var selectedColor by remember { mutableStateOf(Color.Blue) }

    ColorPicker(
        initialColor = selectedColor,
        onColorSelected = { color ->
            selectedColor = color
        }
    )
}
```

### Color Picker Dialog

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

### Individual Components

Use components separately for custom layouts:

```kotlin
@Composable
fun CustomLayout() {
    var color by remember { mutableStateOf(Color.Red) }
    var lightness by remember { mutableFloatStateOf(1f) }
    var alpha by remember { mutableFloatStateOf(1f) }

    Column {
        // Color wheel
        ColorWheel(
            wheelType = ColorPickerState.WheelType.FLOWER,
            density = 12,
            lightness = lightness,
            alpha = alpha,
            onColorSelected = { color = it }
        )

        // Lightness slider
        LightnessSlider(
            color = color,
            lightness = lightness,
            onLightnessChange = { lightness = it }
        )

        // Alpha slider
        AlphaSlider(
            color = color,
            alpha = alpha,
            onAlphaChange = { alpha = it }
        )
    }
}
```

## Complete Documentation

For comprehensive documentation including:
- Advanced usage patterns
- State management strategies
- DataStore integration
- Event handling
- Customization options
- Migration guide from View-based library

See [HOW_TO.md](HOW_TO.md)

## API Overview

### ColorPicker

Main composable with full color picking interface:

```kotlin
ColorPicker(
    modifier: Modifier = Modifier,
    initialColor: Color = Color.White,
    wheelType: ColorPickerState.WheelType = WheelType.FLOWER,
    density: Int = 10,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false,
    onColorChanged: ((Color) -> Unit)? = null,
    onColorSelected: ((Color) -> Unit)? = null
)
```

### ColorPickerDialog

Dialog wrapper for color picker:

```kotlin
ColorPickerDialog(
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit,
    initialColor: Color = Color.White,
    title: String = "Choose Color",
    confirmText: String = "OK",
    dismissText: String = "Cancel",
    wheelType: ColorPickerState.WheelType = WheelType.FLOWER,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false
)
```

### ColorWheel

Standalone color wheel component:

```kotlin
ColorWheel(
    modifier: Modifier = Modifier,
    wheelType: ColorPickerState.WheelType = WheelType.FLOWER,
    density: Int = 10,
    lightness: Float = 1f,
    alpha: Float = 1f,
    onColorChange: ((Color) -> Unit)? = null,
    onColorSelected: ((Color) -> Unit)? = null
)
```

## Wheel Types

**FLOWER** - Organic, petal-like appearance with varying circle sizes
**CIRCLE** - Clean, uniform circles for precise color selection

## Color Extensions

Utility functions for color manipulation:

```kotlin
// Convert to/from Android color Int
val composeColor = Color.Red
val androidColor: Int = composeColor.toAndroidColor()

// HSV color space
val hsv: HSV = Color.Blue.toHSV()
val color: Color = HSV(hue = 180f, saturation = 1f, value = 1f).toColor()

// Lightness operations
val lightness: Float = Color.Green.lightness
val darkerGreen = Color.Green.applyLightness(0.3f)

// Hex strings
val hex: String = Color.Red.hexString // "#FF0000"
val hexWithAlpha: String = Color.Red.hexStringWithAlpha // "#FFFF0000"

// Parse hex strings
val parsedColor: Color? = "#FF5733".parseColor()
```

## Migration from View-Based Version

If you're upgrading from the old View-based library:

1. **Replace imports**: Change `co.csadev.colorpicker.ColorPickerView` to `co.csadev.colorpicker.compose.ColorPicker`
2. **Use Composables**: Replace XML layouts with Compose functions
3. **State management**: Use `remember` and `mutableStateOf` instead of instance variables
4. **Color type**: Use `androidx.compose.ui.graphics.Color` instead of `Int`
5. **Callbacks**: Lambda parameters instead of listener interfaces

See [HOW_TO.md](HOW_TO.md) for detailed migration guide.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

```
Copyright 2014-2025 QuadFlask

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