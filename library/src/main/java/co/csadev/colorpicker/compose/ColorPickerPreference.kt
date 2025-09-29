package co.csadev.colorpicker.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * A preference item that opens a color picker dialog.
 *
 * This composable provides a modern replacement for the deprecated Preference class,
 * designed to work with Jetpack Compose and DataStore.
 *
 * @param title The title of the preference item
 * @param summary Optional summary text
 * @param color The current color value
 * @param onColorChange Callback when the color is changed
 * @param enabled Whether the preference is enabled
 * @param showAlphaSlider Whether to show alpha slider in the dialog
 * @param showLightnessSlider Whether to show lightness slider in the dialog
 * @param showColorEdit Whether to show hex color input in the dialog
 * @param modifier The modifier to apply to this composable
 * @param content Optional theme wrapper - defaults to MaterialTheme
 */
@Composable
fun ColorPickerPreferenceItem(
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    summary: String? = null,
    onColorChange: (Color) -> Unit,
    enabled: Boolean = true,
    showAlphaSlider: Boolean = true,
    showLightnessSlider: Boolean = true,
    showColorEdit: Boolean = false,
    content: @Composable (@Composable () -> Unit) -> Unit = { innerContent ->
        MaterialTheme { innerContent() }
    }
) {
    val dialogState = rememberColorPickerDialogState(initialColor = color)

    ListItem(
        headlineContent = { Text(title) },
        supportingContent = summary?.let { { Text(it) } },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (enabled) color else color.copy(alpha = 0.5f))
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
        },
        modifier = modifier
            .clickable(enabled = enabled) { dialogState.show() }
            .alpha(if (enabled) 1f else 0.5f)
    )

    ColorPickerDialogHost(
        state = dialogState,
        onColorSelected = onColorChange,
        title = title,
        showAlphaSlider = showAlphaSlider,
        showLightnessSlider = showLightnessSlider,
        showColorEdit = showColorEdit,
        content = content
    )
}

/**
 * Extension function to save a color to DataStore.
 *
 * @param key The preference key
 * @param color The color to save
 */
suspend fun DataStore<Preferences>.saveColor(key: String, color: Color) {
    edit { preferences ->
        preferences[intPreferencesKey(key)] = color.toArgb()
    }
}

/**
 * Extension function to get a color Flow from DataStore.
 *
 * @param key The preference key
 * @param defaultColor The default color if none is saved
 * @return A Flow emitting the color value
 */
fun DataStore<Preferences>.getColorFlow(
    key: String,
    defaultColor: Color = Color.White
): Flow<Color> = data.map { preferences ->
    val colorInt = preferences[intPreferencesKey(key)] ?: defaultColor.toArgb()
    Color(colorInt)
}

// Previews

@Preview(showBackground = true)
@Composable
private fun ColorPickerPreferenceItemPreview() {
    MaterialTheme {
        ColorPickerPreferenceItem(
            title = "Accent Color",
            color = Color.Cyan,
            summary = "Choose your accent color",
            onColorChange = {}
        )
    }
}

@Preview(showBackground = true, name = "Disabled")
@Composable
private fun ColorPickerPreferenceItemDisabledPreview() {
    MaterialTheme {
        ColorPickerPreferenceItem(
            title = "Theme Color",
            color = Color.Red,
            summary = "This preference is disabled",
            onColorChange = {},
            enabled = false
        )
    }
}