package co.csadev.colorpicker.renderer

import co.csadev.colorpicker.ColorCircle
import kotlin.math.PI
import kotlin.math.roundToInt

/**
 * Abstract base class for color wheel renderers.
 * Provides common functionality for circle-based color wheels.
 */
abstract class AbsColorWheelRenderer : ColorWheelRenderer {
    private val circleList = arrayListOf<ColorCircle>()

    override var renderOption: ColorWheelRenderOption = ColorWheelRenderOption()

    /**
     * Converts the float alpha value (0-1) to an integer (0-255).
     */
    protected val alphaValueAsInt: Int
        get() = (renderOption.alpha * 255f).roundToInt().coerceIn(0, 255)

    override val colorCircleList: MutableList<ColorCircle>
        get() = circleList

    /**
     * Calculates the total number of color circles that can fit in a ring
     * at the given radius with the specified circle size.
     *
     * Uses geometry to prevent overlapping circles while accounting for gaps.
     *
     * @param radius The radius of the ring
     * @param size The diameter of individual circles
     * @return Number of circles that fit in the ring (minimum 1)
     */
    protected fun calcTotalCount(radius: Float, size: Float): Int {
        if (radius <= 0f || size <= 0f) return 1

        // Calculate circumference with gap factor
        val effectiveCircumference = 2f * PI * radius * (1f - ColorWheelRenderer.GAP_PERCENTAGE)

        // Calculate how many circles fit
        val count = (effectiveCircumference / size).toInt()

        return count.coerceAtLeast(1)
    }

    /**
     * Updates or adds a color circle at the specified index.
     * Reuses existing circles to reduce allocations.
     *
     * @param index The index in the color circle list
     * @param x X coordinate
     * @param y Y coordinate
     * @param hsv HSV color values
     */
    protected fun updateOrAddCircle(index: Int, x: Float, y: Float, hsv: FloatArray) {
        if (index >= circleList.size) {
            circleList.add(ColorCircle(x, y, hsv.copyOf()))
        } else {
            circleList[index][x, y] = hsv
        }
    }
}
