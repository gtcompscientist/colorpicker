package co.csadev.colorpicker.renderer

import co.csadev.colorpicker.ColorCircle

interface ColorWheelRenderer {

    var renderOption: ColorWheelRenderOption

    val colorCircleList: MutableList<ColorCircle>

    fun draw()

    companion object {
        const val GAP_PERCENTAGE = 0.025f
    }
}
