package co.csadev.colorpicker.renderer

import android.graphics.Color
import co.csadev.colorpicker.builder.PaintBuilder
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Simple circular color wheel renderer.
 *
 * Draws color circles in concentric rings with uniform size.
 * More performant than FlowerColorWheelRenderer due to simpler calculations.
 */
class SimpleColorWheelRenderer : AbsColorWheelRenderer() {
    private val paint = PaintBuilder.newPaint().build()
    private val hsvBuffer = FloatArray(3)

    override fun draw() {
        val canvas = renderOption.targetCanvas ?: return
        val centerX = renderOption.centerX ?: (canvas.width / 2f)
        val centerY = renderOption.centerY ?: (canvas.height / 2f)
        val density = renderOption.density
        val maxRadius = renderOption.maxRadius
        val circleSize = renderOption.cSize
        val strokeWidth = renderOption.strokeWidth
        val lightness = renderOption.lightness

        var circleIndex = 0

        // Draw concentric rings from center outward
        for (ring in 0 until density) {
            // Calculate radius for this ring (0 to 1)
            val radiusRatio = if (density > 1) {
                ring.toFloat() / (density - 1)
            } else {
                0f
            }
            val radius = maxRadius * radiusRatio

            // Calculate number of circles for this ring
            val circlesInRing = calcTotalCount(radius, circleSize)

            // Offset alternate rings by half circle for better distribution
            val angleOffset = if (ring % 2 == 0) 0.0 else PI / circlesInRing

            // Draw each circle in this ring
            for (position in 0 until circlesInRing) {
                val angle = 2.0 * PI * position / circlesInRing + angleOffset

                // Calculate position
                val x = centerX + (radius * cos(angle)).toFloat()
                val y = centerY + (radius * sin(angle)).toFloat()

                // Calculate HSV color
                // Hue is based on angle (0-360 degrees)
                hsvBuffer[0] = ((angle * 180.0 / PI) % 360.0).toFloat()
                // Saturation is based on distance from center (0-1)
                hsvBuffer[1] = radiusRatio
                // Value/lightness from render options
                hsvBuffer[2] = lightness

                // Set paint color
                paint.color = Color.HSVToColor(hsvBuffer)
                paint.alpha = alphaValueAsInt

                // Draw the circle
                canvas.drawCircle(x, y, circleSize - strokeWidth, paint)

                // Update color circle list for hit detection
                updateOrAddCircle(circleIndex, x, y, hsvBuffer)
                circleIndex++
            }
        }
    }
}
