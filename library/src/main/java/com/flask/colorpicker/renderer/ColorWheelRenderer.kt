package com.flask.colorpicker.renderer

import com.flask.colorpicker.ColorCircle

interface ColorWheelRenderer {

    var renderOption: ColorWheelRenderOption

    val colorCircleList: MutableList<ColorCircle>

    fun draw()

    companion object {
        const val GAP_PERCENTAGE = 0.025f
    }
}
