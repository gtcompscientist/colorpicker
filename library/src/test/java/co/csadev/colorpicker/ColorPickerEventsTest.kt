package co.csadev.colorpicker

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ColorPickerEventsTest {

    @Test
    fun `ColorPickerEventHandler emits ColorChanged event`() = runTest {
        val handler = ColorPickerEventHandler()
        val testColor = Color.Red
        val testAlpha = 0.8f
        val testLightness = 0.6f

        // Start collecting in background
        val job = launch {
            val event = handler.events.first()
            assertTrue(event is ColorPickerEvent.ColorChanged)
            val colorChangedEvent = event as ColorPickerEvent.ColorChanged
            assertEquals(testColor, colorChangedEvent.color)
            assertEquals(testAlpha, colorChangedEvent.alpha, 0.001f)
            assertEquals(testLightness, colorChangedEvent.lightness, 0.001f)
        }

        // Emit event
        handler.emitColorChanged(testColor, testAlpha, testLightness)

        job.join()
    }

    @Test
    fun `ColorPickerEventHandler emits ColorSelected event`() = runTest {
        val handler = ColorPickerEventHandler()
        val testColor = Color.Blue

        // Start collecting in background
        val job = launch {
            val event = handler.events.first()
            assertTrue(event is ColorPickerEvent.ColorSelected)
            val colorSelectedEvent = event as ColorPickerEvent.ColorSelected
            assertEquals(testColor, colorSelectedEvent.finalColor)
        }

        // Emit event
        handler.emitColorSelected(testColor)

        job.join()
    }

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
