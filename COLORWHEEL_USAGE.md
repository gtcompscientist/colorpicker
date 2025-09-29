# Using the Color Wheel in Compose

This guide shows you how to use the interactive color wheel components in the Compose library.

## Quick Start

### Basic Color Wheel

```kotlin
import co.csadev.colorpicker.compose.ColorWheel
import co.csadev.colorpicker.state.ColorPickerState

@Composable
fun MyScreen() {
    var selectedColor by remember { mutableStateOf(Color.Red) }

    ColorWheel(
        wheelType = ColorPickerState.WheelType.FLOWER,
        onColorSelected = { color ->
            selectedColor = color
        }
    )
}
```

## Color Wheel Types

### Flower Wheel

The flower wheel creates a petal-like appearance with varying circle sizes:

```kotlin
ColorWheel(
    wheelType = ColorPickerState.WheelType.FLOWER,
    density = 10,  // Number of rings (2-20 recommended)
    lightness = 1f,  // Brightness (0-1)
    alpha = 1f,      // Transparency (0-1)
    onColorChange = { color ->
        // Called while dragging
        println("Color changing: $color")
    },
    onColorSelected = { color ->
        // Called when touch is released
        println("Final color: $color")
    }
)
```

### Circle Wheel

The circle wheel uses uniform circle sizes for a cleaner look:

```kotlin
ColorWheel(
    wheelType = ColorPickerState.WheelType.CIRCLE,
    density = 12,
    onColorSelected = { color ->
        selectedColor = color
    }
)
```

## Complete Color Picker with Wheel

The `ColorPicker` composable now includes an integrated color wheel:

```kotlin
import co.csadev.colorpicker.compose.ColorPicker

@Composable
fun FullColorPicker() {
    var color by remember { mutableStateOf(Color.Blue) }

    ColorPicker(
        initialColor = color,
        wheelType = ColorPickerState.WheelType.FLOWER,
        density = 10,
        showColorWheel = true,          // Show the interactive wheel
        showAlphaSlider = true,         // Show transparency control
        showLightnessSlider = true,     // Show brightness control
        showColorEdit = true,           // Show hex input field
        onColorSelected = { newColor ->
            color = newColor
        }
    )
}
```

## Customization Options

### Density

Control the number of concentric rings in the wheel:

```kotlin
// Low density (faster rendering, fewer colors)
ColorWheel(density = 6)

// Medium density (balanced)
ColorWheel(density = 10)

// High density (more colors, slower rendering)
ColorWheel(density = 15)
```

### Lightness

Control the brightness of all colors in the wheel:

```kotlin
// Bright colors
ColorWheel(lightness = 1f)

// Medium brightness
ColorWheel(lightness = 0.5f)

// Dark colors
ColorWheel(lightness = 0.2f)
```

### Alpha/Transparency

Control the transparency of the wheel:

```kotlin
// Fully opaque
ColorWheel(alpha = 1f)

// Semi-transparent
ColorWheel(alpha = 0.5f)

// Nearly transparent
ColorWheel(alpha = 0.2f)
```

## Advanced Usage

### Synchronized Sliders

Combine the color wheel with sliders for full control:

```kotlin
@Composable
fun AdvancedColorPicker() {
    var baseColor by remember { mutableStateOf(Color.Red) }
    var lightness by remember { mutableStateOf(1f) }
    var alpha by remember { mutableStateOf(1f) }

    Column(spacing = 16.dp) {
        // Color wheel responds to slider changes
        ColorWheel(
            wheelType = ColorPickerState.WheelType.FLOWER,
            lightness = lightness,
            alpha = alpha,
            onColorSelected = { color ->
                baseColor = color
            }
        )

        // Lightness slider affects wheel appearance
        LightnessSlider(
            color = baseColor,
            lightness = lightness,
            onLightnessChange = { newLightness ->
                lightness = newLightness
            }
        )

        // Alpha slider affects wheel transparency
        AlphaSlider(
            color = baseColor,
            alpha = alpha,
            onAlphaChange = { newAlpha ->
                alpha = newAlpha
            }
        )

        // Show final color
        Text("Selected: ${baseColor.hexStringWithAlpha}")
    }
}
```

### Custom Modifier

Apply custom styling to the color wheel:

```kotlin
ColorWheel(
    modifier = Modifier
        .size(300.dp)
        .padding(16.dp)
        .border(2.dp, Color.Gray, CircleShape)
        .clip(CircleShape),
    wheelType = ColorPickerState.WheelType.CIRCLE
)
```

### Responsive Sizing

Make the wheel responsive to screen size:

```kotlin
@Composable
fun ResponsiveColorWheel() {
    BoxWithConstraints {
        val wheelSize = minOf(maxWidth, maxHeight) * 0.8f

        ColorWheel(
            modifier = Modifier.size(wheelSize),
            wheelType = ColorPickerState.WheelType.FLOWER
        )
    }
}
```

## Without Color Wheel

If you prefer just sliders without the wheel:

```kotlin
ColorPicker(
    initialColor = Color.Green,
    showColorWheel = false,  // Hide the wheel
    showAlphaSlider = true,
    showLightnessSlider = true,
    showColorEdit = true
)
```

## Performance Tips

1. **Use appropriate density**: Higher density (15+) looks better but renders slower
2. **Prefer CIRCLE wheel**: SimpleColorWheelRenderer is faster than FlowerColorWheelRenderer
3. **Reuse state**: Don't create new color pickers frequently
4. **Use remember**: Always use `remember` for color state

```kotlin
// Good - state is remembered
@Composable
fun GoodExample() {
    var color by remember { mutableStateOf(Color.Red) }
    ColorWheel(onColorSelected = { color = it })
}

// Bad - creates new state every recomposition
@Composable
fun BadExample() {
    var color = Color.Red  // Don't do this!
    ColorWheel(onColorSelected = { color = it })
}
```

## In Dialogs

Use the color wheel in dialogs:

```kotlin
@Composable
fun ColorPickerDialogExample() {
    var showDialog by remember { mutableStateOf(false) }
    var color by remember { mutableStateOf(Color.Blue) }

    Button(onClick = { showDialog = true }) {
        Text("Pick Color")
    }

    if (showDialog) {
        ColorPickerDialog(
            onDismissRequest = { showDialog = false },
            onColorSelected = { newColor ->
                color = newColor
                showDialog = false
            },
            initialColor = color,
            // Dialog automatically includes the wheel
            wheelType = ColorPickerState.WheelType.FLOWER
        )
    }
}
```

## Comparison: Flower vs Circle

| Feature | Flower Wheel | Circle Wheel |
|---------|--------------|--------------|
| **Appearance** | Organic, petal-like | Clean, uniform |
| **Performance** | Slightly slower | Faster |
| **Visual Appeal** | More decorative | More precise |
| **Best For** | Creative apps | Professional tools |
| **Circle Sizes** | Varies by ring | Uniform |

## Migration from View-Based

If you're migrating from the old View-based color picker:

### Before (View)
```kotlin
val colorPickerView = ColorPickerView(context)
colorPickerView.setRenderer(FlowerColorWheelRenderer())
colorPickerView.density = 10
```

### After (Compose)
```kotlin
ColorWheel(
    wheelType = ColorPickerState.WheelType.FLOWER,
    density = 10
)
```

## Troubleshooting

### Issue: Wheel not responding to touch

**Solution**: Make sure you have the `onColorSelected` callback:

```kotlin
ColorWheel(
    onColorSelected = { color ->
        // This callback is required for interaction
        println("Selected: $color")
    }
)
```

### Issue: Wheel looks pixelated

**Solution**: Increase the density:

```kotlin
ColorWheel(density = 12) // Try values between 10-15
```

### Issue: Performance is slow

**Solution**: Reduce density or use CIRCLE wheel:

```kotlin
ColorWheel(
    wheelType = ColorPickerState.WheelType.CIRCLE, // Faster
    density = 8 // Lower density
)
```

## Examples

See the sample app for complete examples:
- Basic color wheel usage
- Integrated with sliders
- In dialogs
- With custom styling
- Responsive layouts