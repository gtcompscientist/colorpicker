package co.csadev.colorpicker

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorPickerEventsTest {

    @Test
    fun `ColorPickerEvent ColorChanged contains correct data`() {
        val event = ColorPickerEvent.ColorChanged(
            color = Color.Magenta,
            alpha = 0.5f,
            lightness = 0.7f
        )

        assertEquals(Color.Magenta, event.color)
        assertEquals(0.5f, event.alpha, 0.001f)
        assertEquals(0.7f, event.lightness, 0.001f)
    }

    @Test
    fun `ColorPickerEvent ColorSelected contains correct data`() {
        val event = ColorPickerEvent.ColorSelected(finalColor = Color.Cyan)

        assertEquals(Color.Cyan, event.finalColor)
    }
}
