package co.csadev.colorpicker.compose

import org.junit.Assert.assertThrows
import org.junit.Test

class ColorExtensionsTest {

    @Test
    fun `HSV validates range constraints`() {
        assertThrows(IllegalArgumentException::class.java) {
            HSV(hue = -1f, saturation = 0.5f, value = 0.5f)
        }

        assertThrows(IllegalArgumentException::class.java) {
            HSV(hue = 361f, saturation = 0.5f, value = 0.5f)
        }

        assertThrows(IllegalArgumentException::class.java) {
            HSV(hue = 180f, saturation = -0.1f, value = 0.5f)
        }

        assertThrows(IllegalArgumentException::class.java) {
            HSV(hue = 180f, saturation = 0.5f, value = 1.1f)
        }

        // Should not throw
        HSV(hue = 180f, saturation = 0.5f, value = 0.5f)
    }
}