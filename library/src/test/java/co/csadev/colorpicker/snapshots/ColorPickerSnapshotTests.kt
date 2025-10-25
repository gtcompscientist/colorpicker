package co.csadev.colorpicker.snapshots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import co.csadev.colorpicker.compose.ColorPicker
import co.csadev.colorpicker.state.ColorPickerState
import org.junit.Rule
import org.junit.Test

/**
 * Snapshot tests for the full ColorPicker component.
 *
 * These tests capture the complete ColorPicker with various configurations:
 * - Different initial colors
 * - Various wheel types
 * - Different combinations of enabled/disabled features
 * - Different density settings
 */
class ColorPickerSnapshotTests {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun colorPicker_fullFeatured_flowerWheel() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Red,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
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
    fun colorPicker_fullFeatured_circleWheel() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Blue,
                            wheelType = ColorPickerState.WheelType.CIRCLE,
                            density = 10,
                            showColorWheel = true,
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
    fun colorPicker_withoutColorEdit() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Green,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
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
    fun colorPicker_withoutAlphaSlider() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Magenta,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
                            showAlphaSlider = false,
                            showLightnessSlider = true,
                            showColorEdit = false
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorPicker_withoutLightnessSlider() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Cyan,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
                            showAlphaSlider = true,
                            showLightnessSlider = false,
                            showColorEdit = false
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorPicker_withoutSliders() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Yellow,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
                            showAlphaSlider = false,
                            showLightnessSlider = false,
                            showColorEdit = false
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorPicker_wheelOnly() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFFFF9800),
                            wheelType = ColorPickerState.WheelType.CIRCLE,
                            density = 10,
                            showColorWheel = true,
                            showAlphaSlider = false,
                            showLightnessSlider = false,
                            showColorEdit = false
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorPicker_withoutWheel() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFF9C27B0),
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = false,
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
    fun colorPicker_lowDensity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Red,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 6,
                            showColorWheel = true,
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
    fun colorPicker_highDensity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Blue,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 15,
                            showColorWheel = true,
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
    fun colorPicker_customColor_orange() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFFFF9800),
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
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
    fun colorPicker_customColor_purple() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFF9C27B0),
                            wheelType = ColorPickerState.WheelType.CIRCLE,
                            density = 10,
                            showColorWheel = true,
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
    fun colorPicker_customColor_teal() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFF009688),
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
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
    fun colorPicker_white() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.White,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
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
    fun colorPicker_black() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Black,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
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
    fun colorPicker_transparent() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Transparent,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            showColorWheel = true,
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
