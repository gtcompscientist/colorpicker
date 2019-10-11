package com.flask.colorpicker.renderer

import com.flask.colorpicker.ColorCircle
import kotlin.math.asin
import kotlin.math.roundToInt

abstract class AbsColorWheelRenderer : ColorWheelRenderer {
    private val circleList = arrayListOf<ColorCircle>()

    override var renderOption: ColorWheelRenderOption = ColorWheelRenderOption()

    protected val alphaValueAsInt: Int
        get() = (renderOption.alpha * 255).roundToInt()

    override val colorCircleList
        get() = circleList

    protected fun calcTotalCount(radius: Float, size: Float): Int {
        return 1.coerceAtLeast(((1f - ColorWheelRenderer.GAP_PERCENTAGE) * Math.PI / asin((size / radius).toDouble()) + 0.5f).toInt())
    }
}