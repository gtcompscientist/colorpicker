package co.csadev.colorpicker.snapshots

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import co.csadev.colorpicker.compose.ColorPickerDialog
import co.csadev.colorpicker.compose.ColorPickerPreferenceItem
import co.csadev.colorpicker.state.ColorPickerState
import org.junit.Rule
import org.junit.Test

/**
 * Snapshot tests for ColorPicker dialog and preference components.
 *
 * These tests capture:
 * - ColorPickerDialog with various configurations
 * - ColorPickerPreferenceItem in different states
 */
class DialogSnapshotTests {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    // ========== Dialog Tests ==========

    @Test
    fun dialog_fullFeatured_flowerWheel() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Red,
                        title = "Choose Color",
                        confirmText = "OK",
                        dismissText = "Cancel",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    @Test
    fun dialog_fullFeatured_circleWheel() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Blue,
                        title = "Select Color",
                        confirmText = "OK",
                        dismissText = "Cancel",
                        wheelType = ColorPickerState.WheelType.CIRCLE,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    @Test
    fun dialog_withoutColorEdit() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Green,
                        title = "Pick a Color",
                        confirmText = "Choose",
                        dismissText = "Cancel",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = false
                    )
                }
            }
        }
    }

    @Test
    fun dialog_withoutAlphaSlider() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Magenta,
                        title = "Choose Color",
                        confirmText = "OK",
                        dismissText = "Cancel",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = false,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    @Test
    fun dialog_minimal() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Cyan,
                        title = "Choose Color",
                        confirmText = "OK",
                        dismissText = "Cancel",
                        wheelType = ColorPickerState.WheelType.CIRCLE,
                        showAlphaSlider = false,
                        showLightnessSlider = false,
                        showColorEdit = false
                    )
                }
            }
        }
    }

    @Test
    fun dialog_customButtonText() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Yellow,
                        title = "Select Your Color",
                        confirmText = "Apply",
                        dismissText = "Discard",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = false
                    )
                }
            }
        }
    }

    @Test
    fun dialog_customColor_orange() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color(0xFFFF9800),
                        title = "Choose Color",
                        confirmText = "OK",
                        dismissText = "Cancel",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    @Test
    fun dialog_customColor_purple() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color(0xFF9C27B0),
                        title = "Choose Color",
                        confirmText = "OK",
                        dismissText = "Cancel",
                        wheelType = ColorPickerState.WheelType.CIRCLE,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Preference Item Tests ==========

    @Test
    fun preferenceItem_enabled_withSummary() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPickerPreferenceItem(
                            title = "Primary Color",
                            summary = "Choose your primary theme color",
                            color = Color.Blue,
                            onColorChange = {},
                            enabled = true,
                            showAlphaSlider = true,
                            showLightnessSlider = true,
                            showColorEdit = true
                        )
                    }
                }
            }
        }
    }

    @Test
    fun preferenceItem_enabled_withoutSummary() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPickerPreferenceItem(
                            title = "Accent Color",
                            color = Color.Green,
                            onColorChange = {},
                            enabled = true,
                            showAlphaSlider = true,
                            showLightnessSlider = true,
                            showColorEdit = false
                        )
                    }
                }
            }
        }
    }

    @Test
    fun preferenceItem_disabled_withSummary() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPickerPreferenceItem(
                            title = "Background Color",
                            summary = "This setting is currently disabled",
                            color = Color.Gray,
                            onColorChange = {},
                            enabled = false,
                            showAlphaSlider = true,
                            showLightnessSlider = true,
                            showColorEdit = true
                        )
                    }
                }
            }
        }
    }

    @Test
    fun preferenceItem_multipleItems() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPickerPreferenceItem(
                            title = "Primary Color",
                            summary = "Main theme color",
                            color = Color.Blue,
                            onColorChange = {},
                            enabled = true
                        )
                        ColorPickerPreferenceItem(
                            title = "Accent Color",
                            summary = "Highlight color",
                            color = Color(0xFFFF9800),
                            onColorChange = {},
                            enabled = true
                        )
                        ColorPickerPreferenceItem(
                            title = "Background",
                            summary = "Background color",
                            color = Color.White,
                            onColorChange = {},
                            enabled = true
                        )
                    }
                }
            }
        }
    }

    @Test
    fun preferenceItem_red() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPickerPreferenceItem(
                            title = "Error Color",
                            summary = "Used for error states",
                            color = Color.Red,
                            onColorChange = {},
                            enabled = true,
                            showAlphaSlider = false,
                            showLightnessSlider = true,
                            showColorEdit = true
                        )
                    }
                }
            }
        }
    }

    @Test
    fun preferenceItem_customColor_purple() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPickerPreferenceItem(
                            title = "Brand Color",
                            summary = "Company brand color",
                            color = Color(0xFF9C27B0),
                            onColorChange = {},
                            enabled = true,
                            showAlphaSlider = true,
                            showLightnessSlider = true,
                            showColorEdit = true
                        )
                    }
                }
            }
        }
    }

    @Test
    fun preferenceItem_customColor_teal() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPickerPreferenceItem(
                            title = "Link Color",
                            summary = "Hyperlink text color",
                            color = Color(0xFF009688),
                            onColorChange = {},
                            enabled = true,
                            showAlphaSlider = true,
                            showLightnessSlider = true,
                            showColorEdit = true
                        )
                    }
                }
            }
        }
    }
}
