package com.flask.colorpicker

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.preference.Preference
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

import com.flask.colorpicker.builder.ColorPickerClickListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder

class ColorPickerPreference : Preference {

    protected var alphaSlider: Boolean = false
    protected var lightSlider: Boolean = false
    protected var border: Boolean = false

    protected var selectedColor = 0

    protected var wheelType: ColorPickerView.WheelType = ColorPickerView.WheelType.FLOWER
    protected var density: Int = 0

    private var pickerColorEdit: Boolean = false
    private var pickerTitle: String? = null
    private var pickerButtonCancel: String? = null
    private var pickerButtonOk: String? = null

    protected lateinit var colorIndicator: ImageView

    @JvmOverloads
    constructor(context: Context? = null, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.ColorPickerView)

        try {
            typedArray?.let { attributes ->
                alphaSlider = attributes.getBoolean(R.styleable.ColorPickerView_alphaSlider, false)
                lightSlider = attributes.getBoolean(R.styleable.ColorPickerView_lightnessSlider, false)
                border = attributes.getBoolean(R.styleable.ColorPickerView_border, true)

                density = attributes.getInt(R.styleable.ColorPickerView_density, 8)
                wheelType = ColorPickerView.WheelType.indexOf(attributes.getInt(R.styleable.ColorPickerView_wheelType, 0))

                selectedColor = attributes.getInt(R.styleable.ColorPickerView_initialColor, -0x1)

                pickerColorEdit = attributes.getBoolean(R.styleable.ColorPickerView_pickerColorEdit, true)
                pickerTitle = attributes.getString(R.styleable.ColorPickerView_pickerTitle)
                if (pickerTitle == null)
                    pickerTitle = "Choose color"

                pickerButtonCancel = attributes.getString(R.styleable.ColorPickerView_pickerButtonCancel)
                if (pickerButtonCancel == null)
                    pickerButtonCancel = "cancel"

                pickerButtonOk = attributes.getString(R.styleable.ColorPickerView_pickerButtonOk)
                if (pickerButtonOk == null)
                    pickerButtonOk = "ok"
            }

        } finally {
            typedArray?.recycle()
        }

        widgetLayoutResource = R.layout.color_widget
    }


    override fun onBindView(view: View) {
        super.onBindView(view)

        val tmpColor = if (isEnabled)
            selectedColor
        else
            darken(selectedColor, .5f)

        colorIndicator = view.findViewById<View>(R.id.color_indicator) as ImageView

        var colorChoiceDrawable: ColorCircleDrawable? = null
        val currentDrawable = colorIndicator.drawable
        if (currentDrawable != null && currentDrawable is ColorCircleDrawable)
            colorChoiceDrawable = currentDrawable

        if (colorChoiceDrawable == null)
            colorChoiceDrawable = ColorCircleDrawable(tmpColor)

        colorIndicator.setImageDrawable(colorChoiceDrawable)
    }

    fun setValue(value: Int) {
        if (callChangeListener(value)) {
            selectedColor = value
            persistInt(value)
            notifyChanged()
        }
    }

    override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any) {
        setValue(if (restoreValue) getPersistedInt(0) else defaultValue as Int)
    }

    override fun onClick() {
        val builder = ColorPickerDialogBuilder
                .with(context)
                .setTitle(pickerTitle!!)
                .initialColor(selectedColor)
                .showBorder(border)
                .wheelType(wheelType)
                .density(density)
                .showColorEdit(pickerColorEdit)
                .setPositiveButton(pickerButtonOk!!, object : ColorPickerClickListener {
                    override fun onClick(dialog: DialogInterface, selectedColorFromPicker: Int, allColors: Array<Int?>) {
                        setValue(selectedColorFromPicker)
                    }
                })
                .setNegativeButton(pickerButtonCancel!!, null!!)

        if (!alphaSlider && !lightSlider)
            builder.noSliders()
        else if (!alphaSlider)
            builder.lightnessSliderOnly()
        else if (!lightSlider) builder.alphaSliderOnly()

        builder
                .build()
                .show()
    }

    companion object {

        fun darken(color: Int, factor: Float): Int {
            val a = Color.alpha(color)
            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color)

            return Color.argb(a,
                    Math.max((r * factor).toInt(), 0),
                    Math.max((g * factor).toInt(), 0),
                    Math.max((b * factor).toInt(), 0))
        }
    }
}
