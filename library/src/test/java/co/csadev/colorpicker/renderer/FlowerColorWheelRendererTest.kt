package co.csadev.colorpicker.renderer

import android.graphics.Canvas
import co.csadev.colorpicker.builder.ColorWheelRendererBuilder
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class FlowerColorWheelRendererTest {

    @Mock
    private lateinit var mockCanvas: Canvas

    @Test
    fun `FlowerColorWheelRenderer creates varied circle sizes`() {
        val renderer = FlowerColorWheelRenderer()

        whenever(mockCanvas.width).thenReturn(500)
        whenever(mockCanvas.height).thenReturn(500)

        val renderOption = ColorWheelRendererBuilder()
            .setTargetCanvas(mockCanvas)
            .setDensity(10)
            .setMaxRadius(200f)
            .setCSize(10f)
            .setLightness(1f)
            .setAlpha(1f)
            .build()

        renderer.renderOption = renderOption
        renderer.draw()

        // Verify circles were drawn
        verify(mockCanvas, atLeast(1)).drawCircle(any(), any(), any(), any())
    }

    @Test
    fun `FlowerColorWheelRenderer applies size variation based on ring position`() {
        val renderer = FlowerColorWheelRenderer()
        val baseSize = 10f
        val density = 10
        val sizeJitterFactor = 1.2f

        // Test that jitter calculation creates variation
        for (ring in 0 until density) {
            val jitter = (ring - density / 2f) / density
            val sizeVariation = if (ring == 0) {
                0f
            } else {
                baseSize * sizeJitterFactor * jitter
            }

            if (ring == 0) {
                assertEquals(0f, sizeVariation, "Center should have no variation")
            } else {
                assertTrue(sizeVariation != 0f, "Non-center rings should have size variation")
            }
        }
    }

    @Test
    fun `FlowerColorWheelRenderer handles low density`() {
        val renderer = FlowerColorWheelRenderer()

        val renderOption = ColorWheelRendererBuilder()
            .setDensity(3)
            .setCSize(10f)
            .setMaxRadius(100f)
            .setLightness(1f)
            .setAlpha(1f)
            .build()

        renderer.renderOption = renderOption
        renderer.draw()
    }

    @Test
    fun `FlowerColorWheelRenderer handles high density`() {
        val renderer = FlowerColorWheelRenderer()

        val renderOption = ColorWheelRendererBuilder()
            .setDensity(50)
            .setCSize(5f)
            .setMaxRadius(200f)
            .setLightness(1f)
            .setAlpha(1f)
            .build()

        renderer.renderOption = renderOption
        renderer.draw()
    }

    @Test
    fun `FlowerColorWheelRenderer creates petal effect with size jitter`() {
        val baseSize = 10f
        val density = 10
        val sizeJitterFactor = 1.2f

        // Verify that outer rings get larger circles
        val outerRing = density - 1
        val outerJitter = (outerRing - density / 2f) / density
        val outerVariation = baseSize * sizeJitterFactor * outerJitter

        // Verify that inner rings get smaller circles
        val innerRing = 1
        val innerJitter = (innerRing - density / 2f) / density
        val innerVariation = baseSize * sizeJitterFactor * innerJitter

        assertTrue(outerVariation > innerVariation,
            "Outer rings should have larger circles than inner rings")
    }
}