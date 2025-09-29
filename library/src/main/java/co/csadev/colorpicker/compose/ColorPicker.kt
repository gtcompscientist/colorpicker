package co.csadev.colorpicker.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.ColorPickerEventHandler
import co.csadev.colorpicker.LaunchedColorPickerEventListener
import co.csadev.colorpicker.state.ColorPickerState
import co.csadev.colorpicker.state.rememberColorPickerState
import kotlinx.coroutines.launch

/**
 * A complete color picker component with sliders and optional text input.
 *
 * @param modifier The modifier to apply to this composable
 * @param initialColor The initial color to display
 * @param showAlphaSlider Whether to show the alpha/transparency slider
 * @param showLightnessSlider Whether to show the lightness/brightness slider
 * @param showColorEdit Whether to show the hex color input field
 * @param onColorChanged Callback invoked when the color changes during interaction
 * @param onColorSelected Callback invoked when a color is finalized
 */
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    initialColor: Color = Color.White,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false,
    onColorChanged: ((Color) -> Unit)? = null,
    onColorSelected: ((Color) -> Unit)? = null
) {
    val state = rememberColorPickerState(
        initialColor = initialColor,
        initialAlpha = initialColor.alpha,
        initialLightness = initialColor.lightness
    )

    val scope = rememberCoroutineScope()
    val eventHandler = remember { ColorPickerEventHandler() }

    // Handle events
    LaunchedColorPickerEventListener(
        eventHandler = eventHandler,
        onColorChanged = onColorChanged?.let { callback ->
            { color, _, _ -> callback(color) }
        },
        onColorSelected = onColorSelected
    )

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Color preview
        ColorPreviewBox(
            color = state.value.selectedColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        // Lightness Slider
        if (showLightnessSlider) {
            LightnessSlider(
                color = state.value.selectedColor,
                lightness = state.value.lightness,
                onLightnessChange = { newLightness ->
                    val newColor = state.value.selectedColor.applyLightness(newLightness)
                    state.value = state.value.copy(
                        selectedColor = newColor,
                        lightness = newLightness
                    )
                    scope.launch {
                        eventHandler.emitColorChanged(
                            newColor,
                            state.value.alpha,
                            newLightness
                        )
                    }
                }
            )
        }

        // Alpha Slider
        if (showAlphaSlider) {
            AlphaSlider(
                color = state.value.selectedColor,
                alpha = state.value.alpha,
                onAlphaChange = { newAlpha ->
                    val newColor = state.value.selectedColor.copy(alpha = newAlpha)
                    state.value = state.value.copy(
                        selectedColor = newColor,
                        alpha = newAlpha
                    )
                    scope.launch {
                        eventHandler.emitColorChanged(
                            newColor,
                            newAlpha,
                            state.value.lightness
                        )
                    }
                }
            )
        }

        // Color Edit TextField
        if (showColorEdit) {
            ColorEditField(
                color = state.value.selectedColor,
                showAlpha = showAlphaSlider,
                onColorChange = { newColor ->
                    state.value = state.value.copy(
                        selectedColor = newColor,
                        alpha = newColor.alpha,
                        lightness = newColor.lightness
                    )
                }
            )
        }
    }
}

/**
 * A text field for editing color hex values.
 */
@Composable
private fun ColorEditField(
    color: Color,
    showAlpha: Boolean,
    onColorChange: (Color) -> Unit
) {
    var text by remember(color) {
        mutableStateOf(if (showAlpha) color.hexStringWithAlpha else color.hexString)
    }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText.uppercase()
            // Try to parse color
            newText.parseColor()?.let { parsedColor ->
                onColorChange(parsedColor)
            }
        },
        label = { Text("Color") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters
        ),
        isError = text.parseColor() == null && text.isNotEmpty()
    )
}

// Previews

@Preview(showBackground = true)
@Composable
private fun ColorPickerPreview() {
    MaterialTheme {
        ColorPicker(
            initialColor = Color.Blue,
            showAlphaSlider = true,
            showLightnessSlider = true,
            showColorEdit = true
        )
    }
}

@Preview(showBackground = true, name = "No Sliders")
@Composable
private fun ColorPickerNoSlidersPreview() {
    MaterialTheme {
        ColorPicker(
            initialColor = Color.Green,
            showAlphaSlider = false,
            showLightnessSlider = false,
            showColorEdit = true
        )
    }
}

@Preview(showBackground = true, name = "Alpha Only")
@Composable
private fun ColorPickerAlphaOnlyPreview() {
    MaterialTheme {
        ColorPicker(
            initialColor = Color.Red,
            showAlphaSlider = true,
            showLightnessSlider = false,
            showColorEdit = false
        )
    }
}