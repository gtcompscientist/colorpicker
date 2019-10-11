package com.flask.colorpicker

/**
 * Allows listening to the color selection events (before they are locked in)
 */
interface OnColorSelectedListener {
    fun onColorSelected(selectedColor: Int)
}
