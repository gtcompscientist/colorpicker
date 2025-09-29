package co.csadev.colorpicker.builder

import android.graphics.*
import kotlin.math.roundToInt

object PaintBuilder {
    fun newPaint(): PaintHolder {
        return PaintHolder()
    }

    class PaintHolder {
        private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

        fun color(color: Int): PaintHolder {
            this.paint.color = color
            return this
        }

        fun antiAlias(flag: Boolean): PaintHolder {
            this.paint.isAntiAlias = flag
            return this
        }

        fun style(style: Paint.Style): PaintHolder {
            this.paint.style = style
            return this
        }

        fun mode(mode: PorterDuff.Mode): PaintHolder {
            this.paint.xfermode = PorterDuffXfermode(mode)
            return this
        }

        fun stroke(width: Float): PaintHolder {
            this.paint.strokeWidth = width
            return this
        }

        fun xPerMode(mode: PorterDuff.Mode): PaintHolder {
            this.paint.xfermode = PorterDuffXfermode(mode)
            return this
        }

        fun shader(shader: Shader): PaintHolder {
            this.paint.shader = shader
            return this
        }

        fun build() = paint
    }

    fun createAlphaPatternShader(size: Int): Shader {
        var size = size
        size /= 2
        size = 8.coerceAtLeast(size * 2)
        return BitmapShader(createAlphaBackgroundPattern(size), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }

    private fun createAlphaBackgroundPattern(size: Int): Bitmap {
        val alphaPatternPaint = newPaint().build()
        val bm = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val s = (size / 2f).roundToInt()
        for (i in 0..1)
            for (j in 0..1) {
                if ((i + j) % 2 == 0)
                    alphaPatternPaint.color = -0x1
                else
                    alphaPatternPaint.color = -0x2f2f30
                c.drawRect((i * s).toFloat(), (j * s).toFloat(), ((i + 1) * s).toFloat(), ((j + 1) * s).toFloat(), alphaPatternPaint)
            }
        return bm
    }
}
