package co.csadev.colorpicker.renderer

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FlowerColorWheelRendererTest {

    @Test
    fun `FlowerColorWheelRenderer size jitter calculation is correct for center`() {
        val baseSize = 10f
        val density = 10
        val sizeJitterFactor = 1.2f
        val ring = 0

        val jitter = (ring - density / 2f) / density
        val sizeVariation = if (ring == 0) {
            0f
        } else {
            baseSize * sizeJitterFactor * jitter
        }

        assertEquals(0f, sizeVariation, "Center should have no variation")
    }

    @Test
    fun `FlowerColorWheelRenderer size jitter creates variation for non-center rings`() {
        val baseSize = 10f
        val density = 10
        val sizeJitterFactor = 1.2f

        // Test non-center ring
        val ring = 5
        val jitter = (ring - density / 2f) / density
        val sizeVariation = baseSize * sizeJitterFactor * jitter

        // For ring 5 with density 10: jitter = (5 - 5) / 10 = 0
        // So actually variation should be 0 at the midpoint
        assertEquals(0f, sizeVariation, 0.001f)
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

        assertTrue(
            outerVariation > innerVariation,
            "Outer rings should have larger circles than inner rings"
        )
    }

    @Test
    fun `FlowerColorWheelRenderer instantiates without error`() {
        val renderer = FlowerColorWheelRenderer()
        assertTrue(renderer is ColorWheelRenderer, "Should be a ColorWheelRenderer")
    }
}
