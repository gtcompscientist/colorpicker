package co.csadev.colorpicker

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import co.csadev.colorpicker.builder.PaintBuilder
import co.csadev.colorpicker.builder.getRenderer
import co.csadev.colorpicker.renderer.ColorWheelRenderOption
import co.csadev.colorpicker.renderer.ColorWheelRenderer
import co.csadev.colorpicker.slider.AlphaSlider
import co.csadev.colorpicker.slider.LightnessSlider
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

private const val STROKE_RATIO = 1.5f

/**
 * The main view that drives the Color Picking
 */
class ColorPickerView : View {

    private var colorWheel: Bitmap? = null
    private var colorWheelCanvas: Canvas? = null
    private var currentColor: Bitmap? = null
    private var currentColorCanvas: Canvas? = null
    private var showBorder: Boolean = false
    private var density = 8

    private var lightness = 1f
    private var alphaValue = 1f
    private val backgroundColor = 0x00000000

    var allColors = arrayOf<Int?>(null, null, null, null, null)
        private set
    private var colorSelection = 0
    private var initialColor: Int = 0
    private var pickerColorEditTextColor: Int? = null
    private val colorWheelFill = PaintBuilder.newPaint().color(0).build()
    private var selectorStroke = PaintBuilder.newPaint().color(0).build()
    private val alphaPatternPaint = PaintBuilder.newPaint().build()
    private var currentColorCircle: ColorCircle? = null

    private val colorChangedListeners = ArrayList<OnColorChangedListener>()
    private val listeners = ArrayList<OnColorSelectedListener>()

    private var lightnessSlider: LightnessSlider? = null
    private var alphaSlider: AlphaSlider? = null
    private var colorEdit: EditText? = null
    private val colorTextChange = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            try {
                val color = Color.parseColor(s.toString())

                // set the color without changing the edit text preventing stack overflow
                setColor(color, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        override fun afterTextChanged(s: Editable) = Unit
    }
    private var colorPreview: LinearLayout? = null

    private var renderer: ColorWheelRenderer? = null

    private var alphaSliderViewId: Int = 0
    private var lightnessSliderViewId: Int = 0

    var selectedColor: Int
        get() = (currentColorCircle?.color ?: 0)
                .applyLightness(lightness)
                .adjustAlpha(alphaValue)
        set(value) {
            if (allColors.any { it == null })
                return
            colorSelection = value
            setHighlightedColor(value)
            val color = allColors[value] ?: return
            setColor(color, true)
        }

    constructor(context: Context) : super(context) {
        initWith(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initWith(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initWith(context, attrs)
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initWith(context, attrs)
    }

    private fun initWith(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerView)

        density = typedArray.getInt(R.styleable.ColorPickerView_density, 10)
        initialColor = typedArray.getInt(R.styleable.ColorPickerView_initialColor, -0x1)

        pickerColorEditTextColor = typedArray.getInt(R.styleable.ColorPickerView_pickerColorEditTextColor, -0x1)

        val wheelType = WheelType.indexOf(typedArray.getInt(R.styleable.ColorPickerView_wheelType, 0))
        val renderer = wheelType.getRenderer()

        alphaSliderViewId = typedArray.getResourceId(R.styleable.ColorPickerView_alphaSliderView, 0)
        lightnessSliderViewId = typedArray.getResourceId(R.styleable.ColorPickerView_lightnessSliderView, 0)

        setRenderer(renderer)
        setDensity(density)
        setInitialColor(initialColor, true)

        typedArray.recycle()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        updateColorWheel()
        currentColorCircle = findNearestByColor(initialColor)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (alphaSliderViewId != 0)
            setAlphaSlider(rootView.findViewById<View>(alphaSliderViewId) as AlphaSlider)
        if (lightnessSliderViewId != 0)
            setLightnessSlider(rootView.findViewById<View>(lightnessSliderViewId) as LightnessSlider)

        updateColorWheel()
        currentColorCircle = findNearestByColor(initialColor)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateColorWheel()
    }

    private fun updateColorWheel() {
        var width = measuredWidth
        val height = measuredHeight

        if (height < width)
            width = height
        if (width <= 0)
            return
        colorWheel?.let {
            colorWheel = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            colorWheelCanvas = Canvas(it)
            alphaPatternPaint.shader = PaintBuilder.createAlphaPatternShader(26)
        }
        currentColor?.let {
            currentColor = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            currentColorCanvas = Canvas(it)
        }
        drawColorWheel()
        invalidate()
    }

    private fun drawColorWheel() {
        colorWheelCanvas?.drawColor(0, PorterDuff.Mode.CLEAR)
        currentColorCanvas?.drawColor(0, PorterDuff.Mode.CLEAR)

        if (renderer == null) return

        val half = (colorWheelCanvas?.width ?: 0) / 2f
        val strokeWidth = STROKE_RATIO * (1f + ColorWheelRenderer.GAP_PERCENTAGE)
        val maxRadius = half - strokeWidth - half / density
        val cSize = maxRadius / (density - 1).toFloat() / 2f

        val colorWheelRenderOption = renderer?.renderOption ?: ColorWheelRenderOption()
        colorWheelRenderOption.density = density
        colorWheelRenderOption.maxRadius = maxRadius
        colorWheelRenderOption.cSize = cSize
        colorWheelRenderOption.strokeWidth = strokeWidth
        colorWheelRenderOption.alpha = alphaValue
        colorWheelRenderOption.lightness = lightness
        colorWheelRenderOption.targetCanvas = colorWheelCanvas

        renderer?.renderOption = colorWheelRenderOption
        renderer?.draw()
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
        when {
            heightMode == MeasureSpec.UNSPECIFIED -> height = widthMeasureSpec
            heightMode == MeasureSpec.AT_MOST -> height = MeasureSpec.getSize(heightMeasureSpec)
            widthMode == MeasureSpec.EXACTLY -> height = MeasureSpec.getSize(heightMeasureSpec)
        }
        var squareDimen = width
        if (height < width)
            squareDimen = height
        setMeasuredDimension(squareDimen, squareDimen)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val lastSelectedColor = selectedColor
                currentColorCircle = findNearestByPosition(event.x, event.y)
                val selectedColor = selectedColor

                callOnColorChangedListeners(lastSelectedColor, selectedColor)

                initialColor = selectedColor
                setColorToSliders(selectedColor)
                updateColorWheel()
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                val selectedColor = selectedColor
                listeners.forEach { listener ->
                    try {
                        listener.onColorSelected(selectedColor)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                setColorToSliders(selectedColor)
                setColorText(selectedColor)
                setColorPreviewColor(selectedColor)
                invalidate()
            }
        }
        return true
    }

    protected fun callOnColorChangedListeners(oldColor: Int, newColor: Int) {
        if (oldColor == newColor) {
            return
        }
        colorChangedListeners.forEach { listener ->
            try {
                listener.onColorChanged(newColor)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(backgroundColor)

        val maxRadius = canvas.width / (1f + ColorWheelRenderer.GAP_PERCENTAGE)
        val size = maxRadius / density.toFloat() / 2f
        val wheel = colorWheel ?: return
        val colorCircle = currentColorCircle ?: return
        colorWheelFill.color = Color.HSVToColor(colorCircle.getHsvWithLightness(lightness))
        colorWheelFill.alpha = (alphaValue * 0xff).toInt()

        // a separate canvas is used to erase an issue with the alpha pattern around the edges
        // draw circle slightly larger than it needs to be, then erase edges to proper dimensions
        currentColorCanvas?.drawCircle(colorCircle.x, colorCircle.y, size + 4, alphaPatternPaint)
        currentColorCanvas?.drawCircle(colorCircle.x, colorCircle.y, size + 4, colorWheelFill)

        selectorStroke = PaintBuilder.newPaint().color(-0x1).style(Paint.Style.STROKE).stroke(size * (STROKE_RATIO - 1)).xPerMode(PorterDuff.Mode.CLEAR).build()

        if (showBorder) colorWheelCanvas?.drawCircle(colorCircle.x, colorCircle.y, size + selectorStroke.strokeWidth / 2f, selectorStroke)
        canvas.drawBitmap(wheel, 0f, 0f, null)

        currentColorCanvas?.drawCircle(colorCircle.x, colorCircle.y, size + selectorStroke.strokeWidth / 2f, selectorStroke)
        currentColor?.let { canvas.drawBitmap(it, 0f, 0f, null) }
    }

    private fun findNearestByPosition(x: Float, y: Float): ColorCircle? {
        var near: ColorCircle? = null
        var minDist = java.lang.Double.MAX_VALUE

        renderer?.colorCircleList?.forEach { colorCircle ->
            val dist = colorCircle.sqDist(x, y)
            if (minDist > dist) {
                minDist = dist
                near = colorCircle
            }
        }

        return near
    }

    private fun findNearestByColor(color: Int): ColorCircle? {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        var near: ColorCircle? = null
        var minDiff = java.lang.Double.MAX_VALUE
        val x = hsv[1] * cos(hsv[0] * Math.PI / 180)
        val y = hsv[1] * sin(hsv[0] * Math.PI / 180)

        renderer?.colorCircleList?.forEach { colorCircle ->
            val hsv1 = colorCircle.hsv
            val x1 = hsv1[1] * cos(hsv1[0] * Math.PI / 180)
            val y1 = hsv1[1] * sin(hsv1[0] * Math.PI / 180)
            val dx = x - x1
            val dy = y - y1
            val dist = dx * dx + dy * dy
            if (dist < minDiff) {
                minDiff = dist
                near = colorCircle
            }
        }

        return near
    }

    fun setInitialColors(colors: Array<Int?>, selectedColor: Int) {
        allColors = colors
        colorSelection = selectedColor
        var initialColor: Int? = allColors[colorSelection]
        if (initialColor == null) initialColor = -0x1
        setInitialColor(initialColor, true)
    }

    fun setInitialColor(color: Int, updateText: Boolean) {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)

        alphaValue = color.alpha
        lightness = hsv[2]
        allColors[colorSelection] = color
        initialColor = color
        setColorPreviewColor(color)
        setColorToSliders(color)
        if (colorEdit != null && updateText)
            setColorText(color)
        currentColorCircle = findNearestByColor(color)
    }

    fun setLightness(newLightness: Float) {
        val lastSelectedColor = selectedColor

        lightness = newLightness
        val hsv = currentColorCircle?.getHsvWithLightness(lightness) ?: floatArrayOf(0f, 0f, 0f)
        initialColor = Color.HSVToColor(alphaValue.alpha, hsv)
        colorEdit?.setText(if (alphaSlider == null) initialColor.hexString else initialColor.hexStringAlpha)
        alphaSlider?.color = initialColor

        callOnColorChangedListeners(lastSelectedColor, initialColor)

        updateColorWheel()
        invalidate()
    }

    fun setColor(color: Int, updateText: Boolean) {
        setInitialColor(color, updateText)
        updateColorWheel()
        invalidate()
    }

    fun setAlphaValue(alpha: Float) {
        val lastSelectedColor = selectedColor

        alphaValue = alpha
        val hsv = currentColorCircle?.getHsvWithLightness(lightness) ?: floatArrayOf(0f, 0f, 0f)
        initialColor = Color.HSVToColor(alpha.alpha, hsv)
        colorEdit?.setText(if (alphaSlider == null) initialColor.hexString else initialColor.hexStringAlpha)
        lightnessSlider?.setColor(initialColor)

        callOnColorChangedListeners(lastSelectedColor, initialColor)

        updateColorWheel()
        invalidate()
    }

    fun addOnColorChangedListener(listener: OnColorChangedListener) {
        colorChangedListeners.add(listener)
    }

    fun addOnColorSelectedListener(listener: OnColorSelectedListener) {
        listeners.add(listener)
    }

    fun setLightnessSlider(slider: LightnessSlider?) {
        lightnessSlider = slider
        lightnessSlider?.colorPicker = this
        lightnessSlider?.setColor(selectedColor)
    }

    fun setAlphaSlider(slide: AlphaSlider?) {
        alphaSlider = slide
        alphaSlider?.colorPicker = this
        alphaSlider?.color =selectedColor
    }

    fun setColorEdit(editText: EditText) {
        colorEdit = editText
        colorEdit?.visibility = VISIBLE
        colorEdit?.addTextChangedListener(colorTextChange)
        setColorEditTextColor(pickerColorEditTextColor ?: 0)
    }

    fun setColorEditTextColor(argb: Int) {
        pickerColorEditTextColor = argb
        colorEdit?.setTextColor(argb)
    }

    fun setDensity(newDensity: Int) {
        density = 2.coerceAtLeast(newDensity)
        invalidate()
    }

    fun setRenderer(newRenderer: ColorWheelRenderer) {
        renderer = newRenderer
        invalidate()
    }

    fun setColorPreview(preview: LinearLayout?, color: Int?) {
        var selectedColor = color
        colorPreview = preview ?: return
        if (selectedColor == null)
            selectedColor = 0
        val children = colorPreview?.childCount ?: 0
        if (children == 0 || colorPreview?.visibility != VISIBLE)
            return

        (0 until children).forEach { i ->
            val childView = colorPreview?.getChildAt(i)
            if (childView !is LinearLayout)
                return@forEach
            if (i == selectedColor) {
                childView.setBackgroundColor(Color.WHITE)
            }
            val childImage = childView.findViewById<View>(R.id.image_preview) as ImageView
            childImage.isClickable = true
            childImage.tag = i
            childImage.setOnClickListener(OnClickListener { v ->
                if (v == null)
                    return@OnClickListener
                val tag = v.tag
                if (tag == null || tag !is Int)
                    return@OnClickListener
                selectedColor = tag
            })
        }
    }

    fun setShowBorder(border: Boolean) {
        showBorder = border
    }

    private fun setHighlightedColor(previewNumber: Int) {
        val children = colorPreview?.childCount ?: 0
        if (colorPreview?.visibility != VISIBLE)
            return

        (0 until children).forEach { i ->
            val childView = colorPreview?.getChildAt(i)
            if (childView !is LinearLayout)
                return@forEach
            if (i == previewNumber) {
                childView.setBackgroundColor(Color.WHITE)
            } else {
                childView.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    private fun setColorPreviewColor(newColor: Int) {
        if (colorPreview == null || colorSelection > allColors.size || allColors[colorSelection] == null)
            return

        val children = colorPreview?.childCount ?: 0
        if (children == 0 || colorPreview?.visibility != VISIBLE)
            return

        val childView = colorPreview?.getChildAt(colorSelection)
        if (childView !is LinearLayout)
            return
        val childImage = childView.findViewById<View>(R.id.image_preview) as ImageView
        childImage.setImageDrawable(ColorCircleDrawable(newColor))
    }

    private fun setColorText(argb: Int) {
        colorEdit?.setText(if (alphaSlider == null) argb.hexString else argb.hexStringAlpha)
    }

    private fun setColorToSliders(selectedColor: Int) {
        lightnessSlider?.setColor(selectedColor)
        alphaSlider?.color = selectedColor
    }

    enum class WheelType {
        FLOWER, CIRCLE;


        companion object {

            fun indexOf(index: Int): WheelType {
                when (index) {
                    0 -> return FLOWER
                    1 -> return CIRCLE
                }
                return FLOWER
            }
        }
    }
}
