package co.csadev.colorpicker.slider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet

import co.csadev.colorpicker.ColorPickerView
import co.csadev.colorpicker.alpha
import co.csadev.colorpicker.builder.PaintBuilder
import kotlin.math.roundToInt

class AlphaSlider : AbsCustomSlider {
    var color: Int = 0
        set(value) {
            field = value
            this.value = field.alpha
            if (bar != null) {
                updateBar()
                invalidate()
            }
        }
    private val alphaPatternPaint = PaintBuilder.newPaint().build()
    private val barPaint = PaintBuilder.newPaint().build()
    private val solid = PaintBuilder.newPaint().build()
    private val clearingStroke = PaintBuilder.newPaint().color(-0x1).xPerMode(PorterDuff.Mode.CLEAR).build()

    private var clearStroke = PaintBuilder.newPaint().build()
    private var clearBitmap: Bitmap? = null
    private var clearBitmapCanvas: Canvas? = null

    var colorPicker: ColorPickerView? = null

    @JvmOverloads
    constructor(context: Context? = null, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    override fun createBitmaps() {
        super.createBitmaps()
        alphaPatternPaint.shader = PaintBuilder.createAlphaPatternShader(barHeight * 2)
        clearBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        clearBitmapCanvas = Canvas(clearBitmap!!)
    }

    override fun drawBar(barCanvas: Canvas) {
        val width = barCanvas.width
        val height = barCanvas.height

        barCanvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), alphaPatternPaint)
        val l = Math.max(2, width / 256)
        var x = 0
        while (x <= width) {
            val alpha = x.toFloat() / (width - 1)
            barPaint.color = color
            barPaint.alpha = Math.round(alpha * 255)
            barCanvas.drawRect(x.toFloat(), 0f, (x + l).toFloat(), height.toFloat(), barPaint)
            x += l
        }
    }

    override fun onValueChanged(value: Float) {
        if (colorPicker != null)
            colorPicker!!.setAlphaValue(value)
    }

    override fun drawHandle(canvas: Canvas, x: Float, y: Float) {
        solid.color = color
        solid.alpha = (value * 255).roundToInt()
        if (showBorder) canvas.drawCircle(x, y, handleRadius.toFloat(), clearingStroke)
        if (value < 1) {
            // this fixes the same artifact issue from ColorPickerView
            // happens when alpha pattern is drawn underneath a circle with the same size
            clearBitmapCanvas!!.drawColor(0, PorterDuff.Mode.CLEAR)
            clearBitmapCanvas!!.drawCircle(x, y, handleRadius * 0.75f + 4, alphaPatternPaint)
            clearBitmapCanvas!!.drawCircle(x, y, handleRadius * 0.75f + 4, solid)

            clearStroke = PaintBuilder.newPaint().color(-0x1).style(Paint.Style.STROKE).stroke(6f).xPerMode(PorterDuff.Mode.CLEAR).build()
            clearBitmapCanvas!!.drawCircle(x, y, handleRadius * 0.75f + clearStroke.strokeWidth / 2, clearStroke)
            canvas.drawBitmap(clearBitmap!!, 0f, 0f, null)
        } else {
            canvas.drawCircle(x, y, handleRadius * 0.75f, solid)
        }
    }
}