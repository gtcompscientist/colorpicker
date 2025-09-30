package co.csadev.colorpicker.builder

import co.csadev.colorpicker.ColorPickerView
import co.csadev.colorpicker.renderer.ColorWheelRenderer
import co.csadev.colorpicker.renderer.FlowerColorWheelRenderer
import co.csadev.colorpicker.renderer.SimpleColorWheelRenderer

fun ColorPickerView.WheelType.getRenderer(): ColorWheelRenderer =
    when (this) {
        ColorPickerView.WheelType.CIRCLE -> SimpleColorWheelRenderer()
        ColorPickerView.WheelType.FLOWER -> FlowerColorWheelRenderer()
    }
