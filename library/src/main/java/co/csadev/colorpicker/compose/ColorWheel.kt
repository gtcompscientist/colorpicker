package co.csadev.colorpicker.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.renderer.ColorWheelRenderOption
import co.csadev.colorpicker.renderer.FlowerColorWheelRenderer
import co.csadev.colorpicker.renderer.SimpleColorWheelRenderer
import co.csadev.colorpicker.state.ColorPickerState
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.hypot

/**
 * A color wheel for selecting hue and saturation.
 *
 * @param modifier The modifier to apply to this composable
 * @param wheelType The type of wheel to render (FLOWER or CIRCLE)
 * @param density Number of concentric rings in the wheel (2-20 recommended)
 * @param lightness The lightness value (0f to 1f)
 * @param alpha The alpha value (0f to 1f)
 * @param onColorChange Callback when color changes during drag
 * @param onColorSelected Callback when touch is released
 */
@Composable
fun ColorWheel(
    modifier: Modifier = Modifier,
    wheelType: ColorPickerState.WheelType = ColorPickerState.WheelType.FLOWER,
    density: Int = 10,
    lightness: Float = 1f,
    alpha: Float = 1f,
    onColorChange: ((Color) -> Unit)? = null,
    onColorSelected: ((Color) -> Unit)? = null
) {
    var selectedColor by remember { mutableStateOf(Color.Red) }
    val strokeWidthPx = with(LocalDensity.current) { 2.dp.toPx() }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val color = calculateColorFromPosition(
                        offset = offset,
                        size = androidx.compose.ui.geometry.Size(
                            size.width.toFloat(),
                            size.height.toFloat()
                        ),
                        lightness = lightness,
                        alpha = alpha
                    )
                    selectedColor = color
                    onColorChange?.invoke(color)
                    onColorSelected?.invoke(color)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, _ ->
                        change.consume()
                        val color = calculateColorFromPosition(
                            offset = change.position,
                            size = androidx.compose.ui.geometry.Size(
                                size.width.toFloat(),
                                size.height.toFloat()
                            ),
                            lightness = lightness,
                            alpha = alpha
                        )
                        selectedColor = color
                        onColorChange?.invoke(color)
                    },
                    onDragEnd = {
                        onColorSelected?.invoke(selectedColor)
                    }
                )
            }
    ) {
        val canvas = drawContext.canvas.nativeCanvas
        val wheelSize = size.width.coerceAtMost(size.height)
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val maxRadius = wheelSize / 2f - strokeWidthPx * 2

        // Create and configure renderer
        val renderer = when (wheelType) {
            ColorPickerState.WheelType.FLOWER -> FlowerColorWheelRenderer()
            ColorPickerState.WheelType.CIRCLE -> SimpleColorWheelRenderer()
        }

        val renderOption = ColorWheelRenderOption(
            density = density,
            maxRadius = maxRadius,
            cSize = maxRadius / density.toFloat() / 2f,
            strokeWidth = strokeWidthPx,
            alpha = alpha,
            lightness = lightness,
            targetCanvas = canvas,
            centerX = centerX,
            centerY = centerY
        )

        renderer.renderOption = renderOption
        renderer.draw()
    }
}

/**
 * Calculates a color from a touch position on the color wheel.
 *
 * @param offset The touch position
 * @param size The size of the canvas
 * @param lightness The lightness value
 * @param alpha The alpha value
 * @return The calculated color
 */
private fun calculateColorFromPosition(
    offset: Offset,
    size: androidx.compose.ui.geometry.Size,
    lightness: Float,
    alpha: Float
): Color {
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val maxRadius = size.width.coerceAtMost(size.height) / 2f

    // Calculate distance from center
    val dx = offset.x - centerX
    val dy = offset.y - centerY
    val distance = hypot(dx.toDouble(), dy.toDouble()).toFloat()

    // Calculate saturation (0 at center, 1 at edge)
    val saturation = (distance / maxRadius).coerceIn(0f, 1f)

    // Calculate hue from angle
    val angle = atan2(dy.toDouble(), dx.toDouble())
    val hue = ((angle * 180.0 / PI + 360.0) % 360.0).toFloat()

    // Create HSV and convert to Color
    return HSV(hue, saturation, lightness).toColor(alpha)
}

// Preview functions

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ColorWheelFlowerPreview() {
    ColorWheel(
        wheelType = ColorPickerState.WheelType.FLOWER,
        density = 10,
        lightness = 1f,
        alpha = 1f
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ColorWheelCirclePreview() {
    ColorWheel(
        wheelType = ColorPickerState.WheelType.CIRCLE,
        density = 10,
        lightness = 1f,
        alpha = 1f
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "Low Density")
@Composable
private fun ColorWheelLowDensityPreview() {
    ColorWheel(
        wheelType = ColorPickerState.WheelType.FLOWER,
        density = 6,
        lightness = 1f,
        alpha = 1f
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "High Density")
@Composable
private fun ColorWheelHighDensityPreview() {
    ColorWheel(
        wheelType = ColorPickerState.WheelType.CIRCLE,
        density = 15,
        lightness = 1f,
        alpha = 1f
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "Dark Lightness")
@Composable
private fun ColorWheelDarkPreview() {
    ColorWheel(
        wheelType = ColorPickerState.WheelType.FLOWER,
        density = 10,
        lightness = 0.3f,
        alpha = 1f
    )
}
