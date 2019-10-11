package com.flask.colorpicker.renderer

import android.graphics.Color
import com.flask.colorpicker.ColorCircle
import com.flask.colorpicker.builder.PaintBuilder
import kotlin.math.cos
import kotlin.math.sin

class FlowerColorWheelRenderer : AbsColorWheelRenderer() {
    private val selectorFill = PaintBuilder.newPaint().build()
    private val hsv = FloatArray(3)
    private val sizeJitter = 1.2f

    override fun draw() {
        val setSize = colorCircleList.size
        var currentCount = 0
        val half = (renderOption.targetCanvas?.width ?: 0) / 2f
        val density = renderOption.density
        val strokeWidth = renderOption.strokeWidth
        val maxRadius = renderOption.maxRadius
        val cSize = renderOption.cSize

        for (i in 0 until density) {
            val p = i.toFloat() / (density - 1) // 0~1
            val jitter = (i - density / 2f) / density // -0.5 ~ 0.5
            val radius = maxRadius * p
            val size = (1.5f + strokeWidth).coerceAtLeast(cSize + (if (i == 0) 0f else cSize * sizeJitter * jitter))
            val total = calcTotalCount(radius, size).coerceAtMost(density * 2)

            for (j in 0 until total) {
                val angle = Math.PI * 2.0 * j.toDouble() / total + Math.PI / total * ((i + 1) % 2)
                val x = half + (radius * cos(angle)).toFloat()
                val y = half + (radius * sin(angle)).toFloat()
                hsv[0] = (angle * 180 / Math.PI).toFloat()
                hsv[1] = radius / maxRadius
                hsv[2] = renderOption.lightness
                selectorFill.color = Color.HSVToColor(hsv)
                selectorFill.alpha = alphaValueAsInt

                renderOption.targetCanvas?.drawCircle(x, y, size - strokeWidth, selectorFill)

                if (currentCount >= setSize) {
                    colorCircleList.add(ColorCircle(x, y, hsv))
                } else
                    colorCircleList[currentCount][x, y] = hsv
                currentCount++
            }
        }
    }
}