package com.flask.colorpicker.builder

import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.renderer.ColorWheelRenderer
import com.flask.colorpicker.renderer.FlowerColorWheelRenderer
import com.flask.colorpicker.renderer.SimpleColorWheelRenderer

fun ColorPickerView.WheelType.getRenderer(): ColorWheelRenderer =
        when (this) {
            ColorPickerView.WheelType.CIRCLE -> SimpleColorWheelRenderer()
            ColorPickerView.WheelType.FLOWER -> FlowerColorWheelRenderer()
        }
