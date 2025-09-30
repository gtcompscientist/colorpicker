package co.csadev.colorpicker

import android.graphics.Color

class ColorCircle(x: Float, y: Float, hsv: FloatArray) {
    var x: Float = 0.toFloat()
        private set
    var y: Float = 0.toFloat()
        private set
    val hsv = FloatArray(3)
    private var hsvClone: FloatArray? = null
    var color: Int = 0
        private set

    init {
        set(x, y, hsv)
    }

    fun sqDist(x: Float, y: Float): Double {
        val dx = (this.x - x).toDouble()
        val dy = (this.y - y).toDouble()
        return dx * dx + dy * dy
    }

    fun getHsvWithLightness(lightness: Float): FloatArray {
        val clone = hsvClone ?: hsv.clone()
        clone[0] = hsv[0]
        clone[1] = hsv[1]
        clone[2] = lightness
        hsvClone = clone
        return clone
    }

    operator fun set(x: Float, y: Float, hsv: FloatArray) {
        this.x = x
        this.y = y
        this.hsv[0] = hsv[0]
        this.hsv[1] = hsv[1]
        this.hsv[2] = hsv[2]
        this.color = Color.HSVToColor(this.hsv)
    }
}
