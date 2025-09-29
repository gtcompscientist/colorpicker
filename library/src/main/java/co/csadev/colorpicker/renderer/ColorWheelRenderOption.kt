package co.csadev.colorpicker.renderer

import android.graphics.Canvas

data class ColorWheelRenderOption(
    var density: Int = 0,
    var maxRadius: Float = 0f,
    var cSize: Float = 0f,
    var strokeWidth: Float = 0f,
    var alpha: Float = 0f,
    var lightness: Float = 0f,
    var targetCanvas: Canvas? = null
)