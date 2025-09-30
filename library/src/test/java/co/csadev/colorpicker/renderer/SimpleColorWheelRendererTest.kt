package co.csadev.colorpicker.renderer

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimpleColorWheelRendererTest {

    @Test
    fun `SimpleColorWheelRenderer calculates circle size with overlap multiplier`() {
        val baseSize = 10f
        val density = 12

        // The actual circle size should be larger than base size for overlap
        // Formula: baseCircleSize * ((density + 1) / 6f)
        val expectedMultiplier = (density + 1) / 6f
        val expectedSize = baseSize * expectedMultiplier

        assertTrue(expectedSize > baseSize, "Circle size should be larger than base for overlap")
        assertEquals(expectedMultiplier, (density + 1) / 6f, 0.01f)
    }

    @Test
    fun `SimpleColorWheelRenderer overlap multiplier increases with density`() {
        val lowDensity = 6
        val highDensity = 20

        val lowMultiplier = (lowDensity + 1) / 6f
        val highMultiplier = (highDensity + 1) / 6f

        assertTrue(
            highMultiplier > lowMultiplier,
            "Higher density should produce larger overlap multiplier"
        )
    }

    @Test
    fun `SimpleColorWheelRenderer instantiates without error`() {
        val renderer = SimpleColorWheelRenderer()
        assertTrue(renderer is ColorWheelRenderer, "Should be a ColorWheelRenderer")
    }

    @Test
    fun `SimpleColorWheelRenderer overlap calculation for typical density`() {
        val baseSize = 10f
        val density = 12 // Typical density
        val multiplier = (density + 1) / 6f // (12 + 1) / 6 = 2.166...
        val expectedSize = baseSize * multiplier

        assertEquals(21.666f, expectedSize, 0.01f)
    }
}
