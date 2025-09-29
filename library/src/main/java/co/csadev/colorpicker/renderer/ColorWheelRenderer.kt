package co.csadev.colorpicker.renderer

import co.csadev.colorpicker.ColorCircle

/**
 * Interface for rendering color wheels with different visual styles.
 */
interface ColorWheelRenderer {

    /**
     * Configuration options for rendering.
     */
    var renderOption: ColorWheelRenderOption

    /**
     * List of color circles that make up the wheel.
     * Used for hit detection and color selection.
     */
    val colorCircleList: MutableList<ColorCircle>

    /**
     * Draws the color wheel on the target canvas.
     * Should populate colorCircleList for interaction.
     */
    fun draw()

    companion object {
        /**
         * Gap percentage between color circles (0.025 = 2.5%).
         * Prevents circles from overlapping visually.
         */
        const val GAP_PERCENTAGE = 0.025f
    }
}
