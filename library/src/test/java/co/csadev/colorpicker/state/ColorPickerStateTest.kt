package co.csadev.colorpicker.state

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class ColorPickerStateTest {

    @Test
    fun `ColorPickerState validates alpha range`() {
        assertThrows(IllegalArgumentException::class.java) {
            ColorPickerState(selectedColor = androidx.compose.ui.graphics.Color.Red, alpha = -0.1f)
        }

        assertThrows(IllegalArgumentException::class.java) {
            ColorPickerState(selectedColor = androidx.compose.ui.graphics.Color.Red, alpha = 1.1f)
        }
    }

    @Test
    fun `ColorPickerState validates lightness range`() {
        assertThrows(IllegalArgumentException::class.java) {
            ColorPickerState(selectedColor = androidx.compose.ui.graphics.Color.Red, lightness = -0.1f)
        }

        assertThrows(IllegalArgumentException::class.java) {
            ColorPickerState(selectedColor = androidx.compose.ui.graphics.Color.Red, lightness = 1.1f)
        }
    }

    @Test
    fun `ColorPickerState validates density minimum`() {
        assertThrows(IllegalArgumentException::class.java) {
            ColorPickerState(selectedColor = androidx.compose.ui.graphics.Color.Red, density = 1)
        }

        // Should not throw
        ColorPickerState(selectedColor = androidx.compose.ui.graphics.Color.Red, density = 2)
    }

    @Test
    fun `WheelType enum has correct values`() {
        assertEquals(2, ColorPickerState.WheelType.values().size)
        assertEquals(ColorPickerState.WheelType.FLOWER, ColorPickerState.WheelType.valueOf("FLOWER"))
        assertEquals(ColorPickerState.WheelType.CIRCLE, ColorPickerState.WheelType.valueOf("CIRCLE"))
    }
}