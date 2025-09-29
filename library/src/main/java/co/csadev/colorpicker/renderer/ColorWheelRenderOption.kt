package co.csadev.colorpicker.renderer

import android.graphics.Canvas

/**
 * Configuration options for rendering a color wheel.
 *
 * @property density Number of rings/circles in the color wheel (2+)
 * @property maxRadius Maximum radius of the color wheel in pixels
 * @property cSize Size of individual color circles in pixels
 * @property strokeWidth Width of stroke/border around circles
 * @property alpha Alpha/transparency value (0f to 1f)
 * @property lightness Lightness/brightness value (0f to 1f)
 * @property targetCanvas Canvas to draw on
 */
data class ColorWheelRenderOption(
    var density: Int = 8,
    var maxRadius: Float = 0f,
    var cSize: Float = 0f,
    var strokeWidth: Float = 0f,
    var alpha: Float = 1f,
    var lightness: Float = 1f,
    var targetCanvas: Canvas? = null
) {
    init {
        require(density >= 2) { "Density must be at least 2" }
        require(alpha in 0f..1f) { "Alpha must be between 0 and 1" }
        require(lightness in 0f..1f) { "Lightness must be between 0 and 1" }
    }
}