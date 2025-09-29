# Color Picker Library - How To Use

This guide explains how to use the modernized Jetpack Compose color picker library.

## Table of Contents

- [Installation](#installation)
- [Quick Start](#quick-start)
- [Basic Usage](#basic-usage)
  - [Simple Color Picker](#simple-color-picker)
  - [Color Picker Dialog](#color-picker-dialog)
  - [Color Sliders](#color-sliders)
- [Advanced Usage](#advanced-usage)
  - [Custom Configuration](#custom-configuration)
  - [Event Handling](#event-handling)
  - [State Management](#state-management)
- [Preferences Integration](#preferences-integration)
- [Customization](#customization)
- [Migration from View-Based Library](#migration-from-view-based-library)

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("co.csadev:colorpicker:1.0.0")
}
```

Minimum Requirements:
- **Android API 24+**
- **Kotlin 1.9+**
- **Jetpack Compose BOM 2025.09.01+**

## Quick Start

### Simplest Example

```kotlin
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import co.csadev.colorpicker.compose.ColorPicker

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

## Basic Usage

### Simple Color Picker

The `ColorPicker` composable provides a complete color picking interface with sliders:

```kotlin
@Composable
fun ColorPickerExample() {
    var color by remember { mutableStateOf(Color.Red) }

    ColorPicker(
        initialColor = color,
        showAlphaSlider = true,
        showLightnessSlider = true,
        showColorEdit = false,
        onColorChanged = { newColor ->
            // Called while dragging sliders
            println("Color changing: ${newColor.hexString}")
        },
        onColorSelected = { finalColor ->
            // Called when user releases slider
            color = finalColor
            println("Final color: ${finalColor.hexString}")
        }
    )
}
```

### Color Picker Dialog

Show a color picker in a Material3 dialog:

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
            title = "Choose a Color",
            confirmText = "Select",
            dismissText = "Cancel"
        )
    }
}
```

### Using Dialog State

For more control, use `ColorPickerDialogState`:

```kotlin
@Composable
fun DialogStateExample() {
    val dialogState = rememberColorPickerDialogState(initialColor = Color.Cyan)
    var selectedColor by remember { mutableStateOf(Color.Cyan) }

    Button(onClick = { dialogState.show() }) {
        Text("Pick Color")
    }

    ColorPickerDialogHost(
        state = dialogState,
        onColorSelected = { color ->
            selectedColor = color
        },
        title = "Select Color",
        showAlphaSlider = true,
        showLightnessSlider = true,
        showColorEdit = true
    )
}
```

### Color Sliders

Use individual sliders separately:

```kotlin
@Composable
fun SlidersExample() {
    var color by remember { mutableStateOf(Color.Blue) }
    var lightness by remember { mutableStateOf(0.5f) }
    var alpha by remember { mutableStateOf(1f) }

    Column {
        // Color preview
        ColorPreviewBox(
            color = color.applyLightness(lightness).copy(alpha = alpha),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

        // Lightness slider
        LightnessSlider(
            color = color,
            lightness = lightness,
            onLightnessChange = { newLightness ->
                lightness = newLightness
            }
        )

        // Alpha slider
        AlphaSlider(
            color = color,
            alpha = alpha,
            onAlphaChange = { newAlpha ->
                alpha = newAlpha
            }
        )
    }
}
```

## Advanced Usage

### Custom Configuration

Configure the color picker to show only specific controls:

```kotlin
@Composable
fun CustomConfigExample() {
    ColorPicker(
        initialColor = Color.Magenta,
        showAlphaSlider = false,        // Hide alpha control
        showLightnessSlider = true,     // Show lightness control
        showColorEdit = true,           // Show hex input field
        onColorSelected = { color ->
            // Handle selection
        }
    )
}
```

### Event Handling

React to color changes in real-time:

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

        // Confirmed color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(currentColor)
        )

        ColorPicker(
            initialColor = currentColor,
            onColorChanged = { color ->
                // Update preview while dragging
                previewColor = color
            },
            onColorSelected = { color ->
                // Update final color on release
                currentColor = color
                previewColor = color
            }
        )
    }
}
```

### State Management

Manage color picker state explicitly:

```kotlin
@Composable
fun StateManagementExample() {
    val state = rememberColorPickerState(
        initialColor = Color.Red,
        initialAlpha = 0.8f,
        initialLightness = 0.6f
    )

    Column {
        Text("Current Color: ${state.value.selectedColor.hexStringWithAlpha}")
        Text("Alpha: ${state.value.alpha}")
        Text("Lightness: ${state.value.lightness}")

        Button(onClick = {
            // Programmatically update state
            state.value = state.value.copy(
                selectedColor = Color.Random(),
                alpha = 1f,
                lightness = 0.5f
            )
        }) {
            Text("Random Color")
        }
    }
}

fun Color.Companion.Random() = Color(
    red = kotlin.random.Random.nextFloat(),
    green = kotlin.random.Random.nextFloat(),
    blue = kotlin.random.Random.nextFloat()
)
```

## Preferences Integration

### Using DataStore

Integrate with Jetpack DataStore for persistent color preferences:

```kotlin
// In your composable
@Composable
fun PreferencesExample() {
    val context = LocalContext.current
    val dataStore = remember {
        context.createDataStore(name = "settings")
    }

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
        },
        showAlphaSlider = false,
        showLightnessSlider = true
    )
}
```

### Multiple Color Preferences

```kotlin
@Composable
fun MultiplePreferencesExample() {
    val context = LocalContext.current
    val dataStore = remember {
        context.createDataStore(name = "settings")
    }

    val primaryColor by dataStore.getColorFlow("primary_color", Color.Blue)
        .collectAsState(initial = Color.Blue)
    val accentColor by dataStore.getColorFlow("accent_color", Color.Cyan)
        .collectAsState(initial = Color.Cyan)

    val scope = rememberCoroutineScope()

    Column {
        ColorPickerPreferenceItem(
            title = "Primary Color",
            color = primaryColor,
            onColorChange = { color ->
                scope.launch { dataStore.saveColor("primary_color", color) }
            }
        )

        ColorPickerPreferenceItem(
            title = "Accent Color",
            color = accentColor,
            onColorChange = { color ->
                scope.launch { dataStore.saveColor("accent_color", color) }
            }
        )
    }
}
```

## Customization

### Color Extensions

The library provides useful extension functions:

```kotlin
import co.csadev.colorpicker.compose.*

// Convert to/from Android color
val composeColor = Color.Red
val androidColor: Int = composeColor.toAndroidColor()
val backToCompose = androidColor.toComposeColor()

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

// Alpha conversions
val alphaInt: Int = 0.5f.toAlphaInt() // 127
val alphaFloat: Float = 127.toAlphaFloat() // 0.5f
```

### Custom Styling

Apply custom modifiers and styling:

```kotlin
@Composable
fun CustomStyledExample() {
    ColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ),
        initialColor = Color.Red
    )
}
```

## Migration from View-Based Library

### Before (View-based)

```kotlin
// Old View-based approach
class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ColorPickerDialogBuilder
            .with(this)
            .setTitle("Choose color")
            .initialColor(Color.RED)
            .wheelType(ColorPickerView.WheelType.FLOWER)
            .density(12)
            .setOnColorSelectedListener { selectedColor ->
                // Handle color
            }
            .setPositiveButton("ok") { dialog, selectedColor, allColors ->
                // Handle confirmation
            }
            .setNegativeButton("cancel", null)
            .build()
            .show()
    }
}
```

### After (Compose)

```kotlin
// New Compose approach
@Composable
fun MyScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.Red) }

    Button(onClick = { showDialog = true }) {
        Text("Pick Color")
    }

    if (showDialog) {
        ColorPickerDialog(
            onDismissRequest = { showDialog = false },
            onColorSelected = { color ->
                selectedColor = color
            },
            initialColor = selectedColor,
            title = "Choose color"
        )
    }
}
```

### Key Differences

1. **State Management**: Use `remember` and `mutableStateOf` instead of instance variables
2. **Builder Pattern**: Replaced with composable parameters
3. **Callbacks**: Use lambda parameters instead of listener interfaces
4. **Dialog Management**: Use boolean state instead of `.show()` method
5. **Color Type**: Use `androidx.compose.ui.graphics.Color` instead of `Int`

### Migration Checklist

- [ ] Replace `ColorPickerDialogBuilder` with `ColorPickerDialog`
- [ ] Convert color integers to `Color` objects
- [ ] Replace listeners with lambda callbacks
- [ ] Use `remember` for state management
- [ ] Replace `Preference` with `ColorPickerPreferenceItem` and DataStore
- [ ] Update imports to `co.csadev.colorpicker.compose.*`

## Best Practices

### 1. State Hoisting

Always hoist state to the appropriate level:

```kotlin
@Composable
fun ParentScreen() {
    var color by remember { mutableStateOf(Color.Blue) }

    // State is hoisted - multiple components can share it
    ColorDisplay(color = color)
    ColorPickerButton(
        currentColor = color,
        onColorChange = { color = it }
    )
}
```

### 2. Use rememberCoroutineScope for Async Operations

```kotlin
@Composable
fun AsyncExample() {
    val scope = rememberCoroutineScope()
    val dataStore = LocalDataStore.current

    ColorPickerPreferenceItem(
        title = "Color",
        color = Color.Red,
        onColorChange = { color ->
            scope.launch {
                dataStore.saveColor("key", color)
            }
        }
    )
}
```

### 3. Optimize Recomposition

Use `derivedStateOf` for expensive calculations:

```kotlin
@Composable
fun OptimizedExample() {
    var color by remember { mutableStateOf(Color.Red) }

    val hexString by remember {
        derivedStateOf { color.hexString }
    }

    Text("Hex: $hexString")
    ColorPicker(
        initialColor = color,
        onColorSelected = { color = it }
    )
}
```

## Troubleshooting

### Issue: Colors appear incorrect

**Solution**: Ensure you're using `androidx.compose.ui.graphics.Color`, not `android.graphics.Color`.

### Issue: Dialog doesn't dismiss

**Solution**: Make sure to call `onDismissRequest` or set your dialog state to false.

```kotlin
ColorPickerDialog(
    onDismissRequest = { showDialog = false }, // This is required
    onColorSelected = { color ->
        handleColor(color)
        showDialog = false // Also dismiss on selection
    }
)
```

### Issue: DataStore colors not persisting

**Solution**: Verify you're using `suspend` functions in a coroutine scope:

```kotlin
val scope = rememberCoroutineScope()
scope.launch {
    dataStore.saveColor("key", color) // Must be in coroutine
}
```

## Examples Repository

For more examples, check the sample app in the repository:
- Basic color picker usage
- Dialog integration
- Preferences screen
- Custom themes
- Real-world scenarios

## Support

For issues, questions, or contributions:
- GitHub Issues: [github.com/yourrepo/colorpicker/issues](https://github.com)
- Documentation: [Complete API documentation](https://docs.example.com)

## License

This library is released under the Apache 2.0 License.