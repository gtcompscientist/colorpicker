package co.csadev.colorpicker.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import co.csadev.colorpicker.state.rememberColorPickerState

/**
 * A Material3 dialog containing a color picker.
 *
 * @param onDismissRequest Callback when the dialog should be dismissed
 * @param onColorSelected Callback when a color is selected and confirmed
 * @param initialColor The initial color to display
 * @param title The dialog title
 * @param confirmText The text for the confirm button
 * @param dismissText The text for the dismiss button
 * @param wheelType The type of color wheel (FLOWER or CIRCLE)
 * @param showAlphaSlider Whether to show the alpha slider
 * @param showLightnessSlider Whether to show the lightness slider
 * @param showColorEdit Whether to show the hex color input
 * @param content Optional theme wrapper - defaults to MaterialTheme
 */
@Composable
fun ColorPickerDialog(
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit,
    initialColor: Color = Color.White,
    title: String = "Choose Color",
    confirmText: String = "OK",
    dismissText: String = "Cancel",
    wheelType: co.csadev.colorpicker.state.ColorPickerState.WheelType = co.csadev.colorpicker.state.ColorPickerState.WheelType.FLOWER,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false,
    properties: DialogProperties = DialogProperties(),
    content: @Composable (@Composable () -> Unit) -> Unit = { innerContent ->
        MaterialTheme { innerContent() }
    }
) {
    val state = rememberColorPickerState(initialColor = initialColor)

    content {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(title) },
            text = {
                ColorPicker(
                    modifier = Modifier.fillMaxWidth(),
                    initialColor = initialColor,
                    wheelType = wheelType,
                    showAlphaSlider = showAlphaSlider,
                    showLightnessSlider = showLightnessSlider,
                    showColorEdit = showColorEdit,
                    onColorChanged = { newColor ->
                        state.value = state.value.copy(selectedColor = newColor)
                    },
                    content = { it() } // Pass through theme from parent
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onColorSelected(state.value.selectedColor)
                    onDismissRequest()
                }) {
                    Text(confirmText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(dismissText)
                }
            },
            properties = properties
        )
    }
}

/**
 * State holder for ColorPickerDialog.
 */
class ColorPickerDialogState(
    initialColor: Color
) {
    var isShowing by mutableStateOf(false)
        private set

    var selectedColor by mutableStateOf(initialColor)
        private set

    fun show() {
        isShowing = true
    }

    fun hide() {
        isShowing = false
    }

    fun updateColor(color: Color) {
        selectedColor = color
    }
}

/**
 * Creates and remembers a [ColorPickerDialogState].
 *
 * @param initialColor The initial color
 * @return A remembered dialog state
 */
@Composable
fun rememberColorPickerDialogState(
    initialColor: Color = Color.White
): ColorPickerDialogState {
    return remember { ColorPickerDialogState(initialColor) }
}

/**
 * A composable that manages showing/hiding a ColorPickerDialog based on state.
 *
 * @param state The dialog state
 * @param onColorSelected Callback when a color is selected
 * @param title The dialog title
 * @param confirmText The confirm button text
 * @param dismissText The dismiss button text
 * @param showAlphaSlider Whether to show alpha slider
 * @param showLightnessSlider Whether to show lightness slider
 * @param showColorEdit Whether to show hex color input
 * @param content Optional theme wrapper - defaults to MaterialTheme
 */
@Composable
fun ColorPickerDialogHost(
    state: ColorPickerDialogState,
    onColorSelected: (Color) -> Unit,
    title: String = "Choose Color",
    confirmText: String = "OK",
    dismissText: String = "Cancel",
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false,
    content: @Composable (@Composable () -> Unit) -> Unit = { innerContent ->
        MaterialTheme { innerContent() }
    }
) {
    if (state.isShowing) {
        ColorPickerDialog(
            onDismissRequest = { state.hide() },
            onColorSelected = { color ->
                state.updateColor(color)
                onColorSelected(color)
            },
            initialColor = state.selectedColor,
            title = title,
            confirmText = confirmText,
            dismissText = dismissText,
            showAlphaSlider = showAlphaSlider,
            showLightnessSlider = showLightnessSlider,
            showColorEdit = showColorEdit,
            content = content
        )
    }
}

// Previews

@Preview
@Composable
private fun ColorPickerDialogPreview() {
    MaterialTheme {
        ColorPickerDialog(
            onDismissRequest = {},
            onColorSelected = {},
            initialColor = Color.Green,
            title = "Pick a Color",
            showAlphaSlider = true,
            showLightnessSlider = true,
            showColorEdit = true
        )
    }
}
