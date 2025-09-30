package co.csadev.colorpicker.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import java.util.Locale
import kotlin.math.roundToInt

/**
 * Converts a Compose Color to an Android Color integer.
 */
fun Color.toAndroidColor(): Int = toArgb()

/**
 * Converts an Android Color integer to a Compose Color.
 */
fun Int.toComposeColor(): Color = Color(this)

/**
 * Data class representing HSV (Hue, Saturation, Value) color components.
 *
 * @property hue The hue component (0-360 degrees)
 * @property saturation The saturation component (0-1)
 * @property value The value/brightness component (0-1)
 */
data class HSV(
    val hue: Float,
    val saturation: Float,
    val value: Float
) {
    init {
        require(hue in 0f..360f) { "Hue must be between 0 and 360" }
        require(saturation in 0f..1f) { "Saturation must be between 0 and 1" }
        require(value in 0f..1f) { "Value must be between 0 and 1" }
    }
}

/**
 * Converts a Compose Color to HSV color space.
 */
fun Color.toHSV(): HSV {
    val hsv = FloatArray(3)
    android.graphics.Color.colorToHSV(this.toArgb(), hsv)
    return HSV(hsv[0], hsv[1], hsv[2])
}

/**
 * Converts HSV color space to a Compose Color.
 *
 * @param alpha The alpha/opacity value (0-1)
 */
fun HSV.toColor(alpha: Float = 1f): Color {
    require(alpha in 0f..1f) { "Alpha must be between 0 and 1" }
    val color = android.graphics.Color.HSVToColor(floatArrayOf(hue, saturation, value))
    return Color(color).copy(alpha = alpha)
}

/**
 * Gets the lightness/value component of this color (0-1).
 */
val Color.lightness: Float
    get() = toHSV().value

/**
 * Applies a new lightness/value to this color.
 *
 * @param lightness The new lightness value (0-1)
 */
fun Color.applyLightness(lightness: Float): Color {
    require(lightness in 0f..1f) { "Lightness must be between 0 and 1" }
    val hsv = toHSV()
    return hsv.copy(value = lightness).toColor(alpha)
}

/**
 * Gets the hex string representation of this color without alpha.
 * Format: #RRGGBB
 */
val Color.hexString: String
    get() {
        val argb = toArgb()
        return "#%06X".format(0xFFFFFF and argb).uppercase(Locale.getDefault())
    }

/**
 * Gets the hex string representation of this color with alpha.
 * Format: #AARRGGBB
 */
val Color.hexStringWithAlpha: String
    get() {
        val argb = toArgb()
        return "#%08X".format(argb).uppercase(Locale.getDefault())
    }

/**
 * Parses a hex color string to a Compose Color.
 * Supports formats: #RGB, #ARGB, #RRGGBB, #AARRGGBB
 *
 * @return The parsed Color, or null if the string is invalid
 */
fun String.parseColor(): Color? {
    return try {
        Color(toColorInt())
    } catch (e: IllegalArgumentException) {
        null
    }
}

/**
 * Converts a Float alpha value (0-1) to an Int (0-255).
 */
fun Float.toAlphaInt(): Int = (this * 255f).roundToInt().coerceIn(0, 255)

/**
 * Converts an Int alpha value (0-255) to a Float (0-1).
 */
fun Int.toAlphaFloat(): Float = (this / 255f).coerceIn(0f, 1f)
