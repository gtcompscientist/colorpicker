package co.csadev.colorpicker.renderer

import android.graphics.Color
import co.csadev.colorpicker.builder.PaintBuilder
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Flower-style color wheel renderer with petal-like appearance.
 *
 * Creates a more organic, flower-like visual by varying circle sizes
 * based on their distance from the center, creating "petals".
 * Slightly less performant than SimpleColorWheelRenderer due to size variations.
 */
class FlowerColorWheelRenderer : AbsColorWheelRenderer() {
    private val paint = PaintBuilder.newPaint().build()
    private val hsvBuffer = FloatArray(3)

    /**
     * Size variation factor for creating petal effect.
     * Higher values create more pronounced size differences.
     */
    private val sizeJitterFactor = 1.2f

    override fun draw() {
        val canvas = renderOption.targetCanvas ?: return
        val centerX = renderOption.centerX ?: (canvas.width / 2f)
        val centerY = renderOption.centerY ?: (canvas.height / 2f)
        val density = renderOption.density
        val strokeWidth = renderOption.strokeWidth
        val maxRadius = renderOption.maxRadius
        val baseCircleSize = renderOption.cSize
        val lightness = renderOption.lightness

        var circleIndex = 0

        // Draw concentric rings with size variation
        for (ring in 0 until density) {
            // Calculate radius ratio for this ring (0 to 1)
            val radiusRatio = if (density > 1) {
                ring.toFloat() / (density - 1)
            } else {
                0f
            }
            val radius = maxRadius * radiusRatio

            // Calculate size jitter based on distance from center
            // Creates smaller circles near center, larger on outer rings
            val jitter = (ring - density / 2f) / density // Range: -0.5 to 0.5
            val sizeVariation = if (ring == 0) {
                0f // No variation for center circle
            } else {
                baseCircleSize * sizeJitterFactor * jitter
            }
            val circleSize = (baseCircleSize + sizeVariation).coerceAtLeast(1.5f + strokeWidth)

            // Calculate circles in this ring with size-adjusted spacing
            val circlesInRing = calcTotalCount(radius, circleSize).coerceAtMost(density * 2)

            // Offset alternate rings for better distribution
            val angleOffset = if (ring % 2 == 0) 0.0 else PI / circlesInRing

            // Draw each circle in this ring
            for (position in 0 until circlesInRing) {
                val angle = 2.0 * PI * position / circlesInRing + angleOffset

                // Calculate position
                val x = centerX + (radius * cos(angle)).toFloat()
                val y = centerY + (radius * sin(angle)).toFloat()

                // Calculate HSV color
                hsvBuffer[0] = ((angle * 180.0 / PI) % 360.0).toFloat() // Hue (0-360)
                hsvBuffer[1] = radiusRatio // Saturation (0-1)
                hsvBuffer[2] = lightness // Value/Brightness

                // Set paint color with alpha
                paint.color = Color.HSVToColor(hsvBuffer)
                paint.alpha = alphaValueAsInt

                // Draw the circle with size variation
                canvas.drawCircle(x, y, circleSize - strokeWidth, paint)

                // Update color circle list for hit detection
                updateOrAddCircle(circleIndex, x, y, hsvBuffer)
                circleIndex++
            }
        }
    }
}
