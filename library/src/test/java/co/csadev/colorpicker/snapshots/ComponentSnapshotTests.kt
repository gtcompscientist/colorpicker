package co.csadev.colorpicker.snapshots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import co.csadev.colorpicker.compose.AlphaSlider
import co.csadev.colorpicker.compose.ColorPreviewBox
import co.csadev.colorpicker.compose.ColorWheel
import co.csadev.colorpicker.compose.LightnessSlider
import co.csadev.colorpicker.state.ColorPickerState
import org.junit.Rule
import org.junit.Test

/**
 * Snapshot tests for individual ColorPicker UI components.
 *
 * These tests capture visual regression snapshots of:
 * - ColorWheel with different wheel types (FLOWER, CIRCLE)
 * - ColorWheel with varying densities
 * - ColorWheel with different lightness values
 * - LightnessSlider in various states
 * - AlphaSlider in various states
 * - ColorPreviewBox with different colors
 */
class ComponentSnapshotTests {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    // ========== ColorWheel Tests ==========

    @Test
    fun colorWheel_flowerType_default() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            lightness = 1f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_circleType_default() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.CIRCLE,
                            density = 10,
                            lightness = 1f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_flowerType_lowDensity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 6,
                            lightness = 1f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_flowerType_highDensity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 15,
                            lightness = 1f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_circleType_lowDensity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.CIRCLE,
                            density = 6,
                            lightness = 1f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_circleType_highDensity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.CIRCLE,
                            density = 15,
                            lightness = 1f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_flowerType_darkLightness() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            lightness = 0.3f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_circleType_darkLightness() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.CIRCLE,
                            density = 10,
                            lightness = 0.3f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_flowerType_mediumLightness() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            lightness = 0.6f,
                            alpha = 1f
                        )
                    }
                }
            }
        }
    }

    @Test
    fun colorWheel_flowerType_withTransparency() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(300.dp).padding(16.dp)) {
                        ColorWheel(
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 10,
                            lightness = 1f,
                            alpha = 0.5f
                        )
                    }
                }
            }
        }
    }

    // ========== Slider Tests ==========

    @Test
    fun lightnessSlider_fullBrightness() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        LightnessSlider(
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            currentColor = Color.Red,
                            lightness = 1f,
                            onLightnessChange = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun lightnessSlider_mediumBrightness() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        LightnessSlider(
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            currentColor = Color.Blue,
                            lightness = 0.5f,
                            onLightnessChange = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun lightnessSlider_lowBrightness() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        LightnessSlider(
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            currentColor = Color.Green,
                            lightness = 0.2f,
                            onLightnessChange = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun lightnessSlider_differentColors() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        LightnessSlider(
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            currentColor = Color(0xFFFF9800),
                            lightness = 0.7f,
                            onLightnessChange = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun alphaSlider_fullOpacity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        AlphaSlider(
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            currentColor = Color.Red,
                            alpha = 1f,
                            onAlphaChange = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun alphaSlider_halfOpacity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        AlphaSlider(
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            currentColor = Color.Blue,
                            alpha = 0.5f,
                            onAlphaChange = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun alphaSlider_lowOpacity() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        AlphaSlider(
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            currentColor = Color.Green,
                            alpha = 0.2f,
                            onAlphaChange = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun alphaSlider_differentColors() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        AlphaSlider(
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            currentColor = Color.Magenta,
                            alpha = 0.8f,
                            onAlphaChange = {}
                        )
                    }
                }
            }
        }
    }

    // ========== ColorPreviewBox Tests ==========

    @Test
    fun colorPreviewBox_red() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                        ColorPreviewBox(color = Color.Red)
                    }
                }
            }
        }
    }

    @Test
    fun colorPreviewBox_blue() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                        ColorPreviewBox(color = Color.Blue)
                    }
                }
            }
        }
    }

    @Test
    fun colorPreviewBox_green() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                        ColorPreviewBox(color = Color.Green)
                    }
                }
            }
        }
    }

    @Test
    fun colorPreviewBox_customColor() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                        ColorPreviewBox(color = Color(0xFF9C27B0))
                    }
                }
            }
        }
    }

    @Test
    fun colorPreviewBox_transparent() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                        ColorPreviewBox(color = Color(0x80FF0000))
                    }
                }
            }
        }
    }

    @Test
    fun colorPreviewBox_black() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                        ColorPreviewBox(color = Color.Black)
                    }
                }
            }
        }
    }

    @Test
    fun colorPreviewBox_white() {
        paparazzi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                        ColorPreviewBox(color = Color.White)
                    }
                }
            }
        }
    }
}
