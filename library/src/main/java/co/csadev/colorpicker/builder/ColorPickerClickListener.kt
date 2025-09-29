package co.csadev.colorpicker.builder

import android.content.DialogInterface

/**
 * Created by Charles Anderson on 4/17/15.
 */
interface ColorPickerClickListener {
    fun onClick(d: DialogInterface, lastSelectedColor: Int, colors: Array<Int?>)
}
