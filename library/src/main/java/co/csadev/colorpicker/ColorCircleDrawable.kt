package co.csadev.colorpicker

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable

import co.csadev.colorpicker.builder.PaintBuilder

class ColorCircleDrawable(color: Int) : ColorDrawable(color) {
    private var strokeWidth: Float = 0.toFloat()
    private val strokePaint = PaintBuilder.newPaint().style(Paint.Style.STROKE).stroke(strokeWidth).color(-0x616162).build()
    private val fillPaint = PaintBuilder.newPaint().style(Paint.Style.FILL).color(0).build()
    private val fillBackPaint = PaintBuilder.newPaint().shader(PaintBuilder.createAlphaPatternShader(26)).build()

    override fun draw(canvas: Canvas) {
        canvas.drawColor(0)

        val width = canvas.width
        val radius = width / 2f
        strokeWidth = radius / 8f

        this.strokePaint.strokeWidth = strokeWidth
        this.fillPaint.color = color
        canvas.drawCircle(radius, radius, radius - strokeWidth, fillBackPaint)
        canvas.drawCircle(radius, radius, radius - strokeWidth, fillPaint)
        canvas.drawCircle(radius, radius, radius - strokeWidth, strokePaint)
    }

    override fun setColor(color: Int) {
        super.setColor(color)
        invalidateSelf()
    }
}
