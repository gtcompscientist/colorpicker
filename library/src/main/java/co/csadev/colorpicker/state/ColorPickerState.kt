package co.csadev.colorpicker.state

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

/**
 * Represents the state of a color picker component.
 *
 * @property selectedColor The currently selected color
 * @property alpha The alpha/transparency value (0f to 1f)
 * @property lightness The lightness/brightness value (0f to 1f)
 * @property colors List of preset colors for multi-color selection
 * @property wheelType The type of color wheel to display
 * @property density The density of color points on the wheel
 */
@Suppress("ANNOTATION_WILL_BE_APPLIED_ALSO_TO_PROPERTY_OR_FIELD")
@Stable
data class ColorPickerState(
    val selectedColor: Color,
    @FloatRange(0.0, 1.0) val alpha: Float = 1f,
    @FloatRange(0.0, 1.0) val lightness: Float = 1f,
    val colors: List<Color> = emptyList(),
    val wheelType: WheelType = WheelType.FLOWER,
    @IntRange(2L) val density: Int = 8
) {
    /**
     * Types of color wheels available.
     */
    enum class WheelType {
        /** A flower-shaped color wheel with petals */
        FLOWER,

        /** A simple circular color wheel */
        CIRCLE
    }

    init {
        require(alpha in 0f..1f) { "Alpha must be between 0 and 1" }
        require(lightness in 0f..1f) { "Lightness must be between 0 and 1" }
        require(density >= 2) { "Density must be at least 2" }
    }
}

/**
 * Creates and remembers a [ColorPickerState].
 *
 * @param initialColor The initial color to display
 * @param initialAlpha The initial alpha value
 * @param initialLightness The initial lightness value
 * @param wheelType The type of color wheel to use
 * @param density The density of color points
 * @return A remembered mutable state containing the ColorPickerState
 */
@Composable
fun rememberColorPickerState(
    initialColor: Color = Color.White,
    initialAlpha: Float = 1f,
    initialLightness: Float = 1f,
    wheelType: ColorPickerState.WheelType = ColorPickerState.WheelType.FLOWER,
    density: Int = 8
): MutableState<ColorPickerState> {
    return remember {
        mutableStateOf(
            ColorPickerState(
                selectedColor = initialColor,
                alpha = initialAlpha,
                lightness = initialLightness,
                wheelType = wheelType,
                density = density
            )
        )
    }
}
