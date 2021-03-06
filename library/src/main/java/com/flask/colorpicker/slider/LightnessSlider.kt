package com.flask.colorpicker.slider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet

import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.applyLightness
import com.flask.colorpicker.builder.PaintBuilder
import com.flask.colorpicker.lightness

@Suppress("ConvertSecondaryConstructorToPrimary")
class LightnessSlider : AbsCustomSlider {
    private var color: Int = 0
    private val barPaint = PaintBuilder.newPaint().build()
    private val solid = PaintBuilder.newPaint().build()
    private val clearingStroke = PaintBuilder.newPaint().color(-0x1).xPerMode(PorterDuff.Mode.CLEAR).build()

    var colorPicker: ColorPickerView? = null

    @JvmOverloads
    constructor(context: Context? = null, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    override fun drawBar(barCanvas: Canvas) {
        val width = barCanvas.width
        val height = barCanvas.height

        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        val l = 2.coerceAtLeast(width / 256)
        var x = 0
        while (x <= width) {
            hsv[2] = x.toFloat() / (width - 1)
            barPaint.color = Color.HSVToColor(hsv)
            barCanvas.drawRect(x.toFloat(), 0f, (x + l).toFloat(), height.toFloat(), barPaint)
            x += l
        }
    }

    override fun onValueChanged(value: Float) {
        colorPicker?.setLightness(value)
    }

    override fun drawHandle(canvas: Canvas, x: Float, y: Float) {
        solid.color = color.applyLightness(value)
        if (showBorder) {
            canvas.drawCircle(x, y, handleRadius.toFloat(), clearingStroke)
        }
        canvas.drawCircle(x, y, handleRadius * 0.75f, solid)
    }

    fun setColor(input: Int) {
        color = input
        value = input.lightness
        if (bar != null) {
            updateBar()
            invalidate()
        }
    }
}