package com.flask.colorpicker.slider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DimenRes
import com.flask.colorpicker.R

@Suppress("ConvertSecondaryConstructorToPrimary")
abstract class AbsCustomSlider : View {
    protected var bitmap: Bitmap? = null
    protected var bitmapCanvas: Canvas? = null
    protected var bar: Bitmap? = null
    protected var barCanvas: Canvas? = null
    protected var onValueChangedListener: OnValueChangedListener? = null
    protected var barOffsetX: Int = 0
    protected var handleRadius = 20
    protected var barHeight = 5
    protected var value = 1f
    protected var showBorder = false

    private var inVerticalOrientation = false

    @JvmOverloads
    constructor(context: Context? = null, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        val styledAttrs = context?.theme?.obtainStyledAttributes(
                attrs, R.styleable.AbsCustomSlider, 0, 0)
        try {
            inVerticalOrientation = styledAttrs?.getBoolean(
                    R.styleable.AbsCustomSlider_inVerticalOrientation, inVerticalOrientation)
                    ?: false
        } finally {
            styledAttrs?.recycle()
        }
    }

    protected fun updateBar() {
        handleRadius = getDimension(R.dimen.default_slider_handler_radius)
        barHeight = getDimension(R.dimen.default_slider_bar_height)
        barOffsetX = handleRadius

        if (bar == null) {
            createBitmaps()
        }
        barCanvas?.let { drawBar(it) }
        invalidate()
    }

    protected open fun createBitmaps() {
        val width: Int
        val height: Int
        if (inVerticalOrientation) {
            width = getHeight()
            height = getWidth()
        } else {
            width = getWidth()
            height = getHeight()
        }

        bar = Bitmap.createBitmap(width - barOffsetX * 2, barHeight, Bitmap.Config.ARGB_8888)
        barCanvas = bar?.run { Canvas(this) }

        if (bitmap?.width != width || bitmap?.height != height) {
            bitmap?.recycle()
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmapCanvas = bitmap?.run { Canvas(this) }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width: Int
        val height: Int
        if (inVerticalOrientation) {
            width = getHeight()
            height = getWidth()

            canvas.rotate(-90f)
            canvas.translate((-width).toFloat(), 0f)
        } else {
            width = getWidth()
            height = getHeight()
        }

        bar?.let {
            bitmapCanvas?.drawColor(0, PorterDuff.Mode.CLEAR)
            bitmapCanvas?.drawBitmap(it, barOffsetX.toFloat(), ((height - it.height) / 2).toFloat(), null)
        }
        val x = handleRadius + value * (width - handleRadius * 2)
        val y = height / 2f
        bitmapCanvas?.let { drawHandle(it, x, y) }
        bitmap?.let { canvas.drawBitmap(it, 0f, 0f, null) }
    }

    protected abstract fun drawBar(barCanvas: Canvas)

    protected abstract fun onValueChanged(value: Float)

    protected abstract fun drawHandle(canvas: Canvas, x: Float, y: Float)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateBar()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = 0
        when (widthMode) {
            MeasureSpec.UNSPECIFIED -> width = widthMeasureSpec
            MeasureSpec.AT_MOST -> width = MeasureSpec.getSize(widthMeasureSpec)
            MeasureSpec.EXACTLY -> width = MeasureSpec.getSize(widthMeasureSpec)
        }

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = 0
        when (heightMode) {
            MeasureSpec.UNSPECIFIED -> height = heightMeasureSpec
            MeasureSpec.AT_MOST -> height = MeasureSpec.getSize(heightMeasureSpec)
            MeasureSpec.EXACTLY -> height = MeasureSpec.getSize(heightMeasureSpec)
        }

        setMeasuredDimension(width, height)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (bar != null) {
                    value = if (inVerticalOrientation) {
                        1 - (event.y - barOffsetX) / (bar?.width ?: 1)
                    } else {
                        (event.x - barOffsetX) / (bar?.width ?: 1)
                    }
                    value = 0f.coerceAtLeast(value.coerceAtMost(1f))
                    onValueChanged(value)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                onValueChanged(value)
                onValueChangedListener?.onValueChanged(value)
                invalidate()
            }
        }
        return true
    }

    protected fun getDimension(@DimenRes id: Int): Int = resources.getDimensionPixelSize(id)
}