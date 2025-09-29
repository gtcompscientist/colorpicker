package co.csadev.colorpicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Sealed interface representing events that can occur in a color picker.
 */
sealed interface ColorPickerEvent {
    /**
     * Emitted when the color changes during interaction (e.g., dragging).
     *
     * @property color The new color value
     * @property alpha The current alpha value
     * @property lightness The current lightness value
     */
    data class ColorChanged(
        val color: Color,
        val alpha: Float,
        val lightness: Float
    ) : ColorPickerEvent

    /**
     * Emitted when a color is finalized (e.g., touch released).
     *
     * @property finalColor The final selected color
     */
    data class ColorSelected(val finalColor: Color) : ColorPickerEvent
}

/**
 * Handles color picker events using Kotlin Flows for reactive programming.
 */
class ColorPickerEventHandler {
    private val _events = MutableSharedFlow<ColorPickerEvent>(
        replay = 0,
        extraBufferCapacity = 1
    )

    /**
     * Flow of color picker events.
     */
    val events: SharedFlow<ColorPickerEvent> = _events.asSharedFlow()

    /**
     * Emits a color changed event.
     *
     * @param color The new color
     * @param alpha The current alpha value
     * @param lightness The current lightness value
     */
    suspend fun emitColorChanged(color: Color, alpha: Float, lightness: Float) {
        _events.emit(ColorPickerEvent.ColorChanged(color, alpha, lightness))
    }

    /**
     * Emits a color selected event.
     *
     * @param color The final selected color
     */
    suspend fun emitColorSelected(color: Color) {
        _events.emit(ColorPickerEvent.ColorSelected(color))
    }
}

/**
 * Composable that listens to color picker events and invokes callbacks.
 *
 * @param eventHandler The event handler to listen to
 * @param onColorChanged Callback invoked when color changes
 * @param onColorSelected Callback invoked when color is selected
 */
@Composable
fun LaunchedColorPickerEventListener(
    eventHandler: ColorPickerEventHandler,
    onColorChanged: ((Color, Float, Float) -> Unit)? = null,
    onColorSelected: ((Color) -> Unit)? = null
) {
    LaunchedEffect(eventHandler) {
        eventHandler.events.collect { event ->
            when (event) {
                is ColorPickerEvent.ColorChanged -> onColorChanged?.invoke(
                    event.color,
                    event.alpha,
                    event.lightness
                )

                is ColorPickerEvent.ColorSelected -> onColorSelected?.invoke(event.finalColor)
            }
        }
    }
}