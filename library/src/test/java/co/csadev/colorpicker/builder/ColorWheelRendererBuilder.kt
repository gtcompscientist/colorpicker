package co.csadev.colorpicker.builder

import android.graphics.Canvas
import co.csadev.colorpicker.renderer.ColorWheelRenderOption

/**
 * Test builder for creating ColorWheelRenderOption instances.
 * Used only in unit tests.
 */
class ColorWheelRendererBuilder {
    private var targetCanvas: Canvas? = null
    private var density: Int = 10
    private var maxRadius: Float = 100f
    private var cSize: Float = 10f
    private var strokeWidth: Float = 2f
    private var alpha: Float = 1f
    private var lightness: Float = 1f
    private var centerX: Float? = null
    private var centerY: Float? = null

    fun setTargetCanvas(canvas: Canvas) = apply { this.targetCanvas = canvas }
    fun setDensity(density: Int) = apply { this.density = density }
    fun setMaxRadius(maxRadius: Float) = apply { this.maxRadius = maxRadius }
    fun setCSize(cSize: Float) = apply { this.cSize = cSize }
    fun setStrokeWidth(strokeWidth: Float) = apply { this.strokeWidth = strokeWidth }
    fun setAlpha(alpha: Float) = apply { this.alpha = alpha }
    fun setLightness(lightness: Float) = apply { this.lightness = lightness }
    fun setCenterX(centerX: Float) = apply { this.centerX = centerX }
    fun setCenterY(centerY: Float) = apply { this.centerY = centerY }

    fun build(): ColorWheelRenderOption {
        return ColorWheelRenderOption(
            density = density,
            maxRadius = maxRadius,
            cSize = cSize,
            strokeWidth = strokeWidth,
            alpha = alpha,
            lightness = lightness,
            targetCanvas = targetCanvas,
            centerX = centerX,
            centerY = centerY
        )
    }
}
