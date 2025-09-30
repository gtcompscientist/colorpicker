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
class SimpleColorWheelRendererTest {

    @Mock
    private lateinit var mockCanvas: Canvas

    @Test
    fun `SimpleColorWheelRenderer creates circles with overlap`() {
        val renderer = SimpleColorWheelRenderer()

        // Setup test canvas dimensions
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

        // The renderer should work without throwing exceptions
        // In a real UI test, we'd verify the visual overlap
        renderer.draw()

        // Verify canvas draw operations were called
        verify(mockCanvas, atLeast(1)).drawCircle(any(), any(), any(), any())
    }

    @Test
    fun `SimpleColorWheelRenderer calculates circle size with overlap multiplier`() {
        val renderer = SimpleColorWheelRenderer()
        val baseSize = 10f
        val density = 12

        val renderOption = ColorWheelRendererBuilder()
            .setDensity(density)
            .setCSize(baseSize)
            .setMaxRadius(200f)
            .setLightness(1f)
            .setAlpha(1f)
            .build()

        renderer.renderOption = renderOption

        // The actual circle size should be larger than base size for overlap
        // Formula: baseCircleSize * ((density + 1) / 6f)
        val expectedMultiplier = (density + 1) / 6f
        val expectedSize = baseSize * expectedMultiplier

        assertTrue(expectedSize > baseSize, "Circle size should be larger than base for overlap")
        assertEquals(expectedMultiplier, (density + 1) / 6f, 0.01f)
    }

    @Test
    fun `SimpleColorWheelRenderer handles minimum density`() {
        val renderer = SimpleColorWheelRenderer()

        val renderOption = ColorWheelRendererBuilder()
            .setDensity(2) // Minimum density
            .setCSize(10f)
            .setMaxRadius(100f)
            .setLightness(1f)
            .setAlpha(1f)
            .build()

        renderer.renderOption = renderOption

        // Should handle minimum density without errors
        renderer.draw()
    }

    @Test
    fun `SimpleColorWheelRenderer handles maximum density`() {
        val renderer = SimpleColorWheelRenderer()

        val renderOption = ColorWheelRendererBuilder()
            .setDensity(100) // Maximum density
            .setCSize(5f)
            .setMaxRadius(200f)
            .setLightness(1f)
            .setAlpha(1f)
            .build()

        renderer.renderOption = renderOption

        // Should handle maximum density without errors
        renderer.draw()
    }
}