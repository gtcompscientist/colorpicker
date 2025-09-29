package co.csadev.colorpicker.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * A slider for adjusting the lightness/brightness of a color.
 *
 * @param modifier The modifier to apply to this slider
 * @param color The base color to adjust lightness for
 * @param lightness The current lightness value (0f to 1f)
 * @param onLightnessChange Callback when lightness changes
 * @param enabled Whether the slider is enabled
 */
@Composable
fun LightnessSlider(
    modifier: Modifier = Modifier,
    color: Color,
    lightness: Float,
    onLightnessChange: (Float) -> Unit,
    enabled: Boolean = true
) {
    val hsv = color.toHSV()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        val trackHeight = 8.dp.toPx()
        val cornerRadius = 4.dp.toPx()
        val trackTop = (size.height - trackHeight) / 2

        // Draw gradient background
        val gradient = Brush.horizontalGradient(
            colors = buildList {
                for (i in 0..100) {
                    val l = i / 100f
                    add(hsv.copy(value = l).toColor(color.alpha))
                }
            }
        )

        drawRoundRect(
            brush = gradient,
            cornerRadius = CornerRadius(cornerRadius),
            topLeft = Offset(0f, trackTop),
            size = Size(size.width, trackHeight)
        )

        // Draw current position indicator
        val indicatorX = size.width * lightness
        val indicatorY = size.height / 2

        // Draw outer circle (white border)
        drawCircle(
            color = Color.White,
            radius = 10.dp.toPx(),
            center = Offset(indicatorX, indicatorY),
            style = Stroke(width = 2.dp.toPx())
        )

        // Draw inner circle with current lightness color
        drawCircle(
            color = hsv.copy(value = lightness).toColor(color.alpha),
            radius = 8.dp.toPx(),
            center = Offset(indicatorX, indicatorY)
        )
    }

    Slider(
        value = lightness,
        onValueChange = onLightnessChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        enabled = enabled,
        colors = SliderDefaults.colors(
            thumbColor = Color.Transparent,
            activeTrackColor = Color.Transparent,
            inactiveTrackColor = Color.Transparent,
            disabledThumbColor = Color.Transparent,
            disabledActiveTrackColor = Color.Transparent,
            disabledInactiveTrackColor = Color.Transparent
        )
    )
}

/**
 * A slider for adjusting the alpha/transparency of a color.
 *
 * @param modifier The modifier to apply to this slider
 * @param color The color to adjust alpha for
 * @param alpha The current alpha value (0f to 1f)
 * @param onAlphaChange Callback when alpha changes
 * @param enabled Whether the slider is enabled
 */
@Composable
fun AlphaSlider(
    modifier: Modifier = Modifier,
    color: Color,
    alpha: Float,
    onAlphaChange: (Float) -> Unit,
    enabled: Boolean = true
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        val trackHeight = 8.dp.toPx()
        val cornerRadius = 4.dp.toPx()
        val trackTop = (size.height - trackHeight) / 2

        // Draw checkerboard pattern for transparency visualization
        drawCheckerboard(
            checkerSize = 4.dp.toPx(),
            cornerRadius = cornerRadius,
            topLeft = Offset(0f, trackTop),
            size = Size(size.width, trackHeight)
        )

        // Draw gradient background with alpha
        val gradient = Brush.horizontalGradient(
            colors = buildList {
                for (i in 0..100) {
                    add(color.copy(alpha = i / 100f))
                }
            }
        )

        drawRoundRect(
            brush = gradient,
            cornerRadius = CornerRadius(cornerRadius),
            topLeft = Offset(0f, trackTop),
            size = Size(size.width, trackHeight)
        )

        // Draw current position indicator
        val indicatorX = size.width * alpha
        val indicatorY = size.height / 2

        // Draw outer circle (white border)
        drawCircle(
            color = Color.White,
            radius = 10.dp.toPx(),
            center = Offset(indicatorX, indicatorY),
            style = Stroke(width = 2.dp.toPx())
        )

        // Draw inner circle with current alpha color
        drawCircle(
            color = color.copy(alpha = alpha),
            radius = 8.dp.toPx(),
            center = Offset(indicatorX, indicatorY)
        )
    }

    Slider(
        value = alpha,
        onValueChange = onAlphaChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        enabled = enabled,
        colors = SliderDefaults.colors(
            thumbColor = Color.Transparent,
            activeTrackColor = Color.Transparent,
            inactiveTrackColor = Color.Transparent,
            disabledThumbColor = Color.Transparent,
            disabledActiveTrackColor = Color.Transparent,
            disabledInactiveTrackColor = Color.Transparent
        )
    )
}

/**
 * Draws a checkerboard pattern for transparency visualization.
 */
private fun DrawScope.drawCheckerboard(
    checkerSize: Float,
    cornerRadius: Float = 0f,
    topLeft: Offset = Offset.Zero,
    size: Size = this.size
) {
    val cols = (size.width / checkerSize).roundToInt() + 1
    val rows = (size.height / checkerSize).roundToInt() + 1

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            if ((row + col) % 2 == 0) {
                val x = topLeft.x + col * checkerSize
                val y = topLeft.y + row * checkerSize

                drawRect(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    topLeft = Offset(x, y),
                    size = Size(checkerSize, checkerSize)
                )
            }
        }
    }
}

// Previews

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun LightnessSliderPreview() {
    LightnessSlider(
        color = Color.Red,
        lightness = 0.7f,
        onLightnessChange = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AlphaSliderPreview() {
    AlphaSlider(
        color = Color.Blue,
        alpha = 0.5f,
        onAlphaChange = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "Disabled Slider")
@Composable
private fun DisabledSliderPreview() {
    LightnessSlider(
        color = Color.Green,
        lightness = 0.5f,
        onLightnessChange = {},
        enabled = false
    )
}