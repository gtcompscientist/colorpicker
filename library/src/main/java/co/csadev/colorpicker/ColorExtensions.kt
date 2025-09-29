package co.csadev.colorpicker

import android.graphics.Color
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Charles Anderson on 4/17/15.
 */
val Int.lightness: Float
    get() = FloatArray(3).also { floatArray -> Color.colorToHSV(this, floatArray) }[2]

fun Int.applyLightness(lightness: Float): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(this, hsv)
    hsv[2] = lightness
    return Color.HSVToColor(hsv)
}


val Float.alpha: Int
    get() = (this * 255).roundToInt()

val Int.alpha: Float
    get() = Color.alpha(this) / 255f

fun Int.adjustAlpha(alpha: Float): Int = alpha.alpha shl 24 or (0x00ffffff and this)

val Int.hexString: String
    get() = getHex()

val Int.hexStringAlpha: String
    get() = getHex(true)

private fun Int.getHex(showAlpha: Boolean = false): String {
    val base = if (showAlpha) -0x1 else 0xFFFFFF
    val format = if (showAlpha) "#%08X" else "#%06X"
    return String.format(format, base and this).uppercase(Locale.getDefault())
}
