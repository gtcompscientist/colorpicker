package co.csadev.colorpicker.snapshots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.compose.AlphaSlider
import co.csadev.colorpicker.compose.ColorPreviewBox
import co.csadev.colorpicker.compose.ColorWheel
import co.csadev.colorpicker.compose.LightnessSlider
import co.csadev.colorpicker.state.ColorPickerState
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Snapshot tests for individual ColorPicker UI components using Roborazzi.
 *
 * These tests capture visual regression snapshots of:
 * - ColorWheel with different wheel types (FLOWER, CIRCLE)
 * - ColorWheel with varying densities
 * - ColorWheel with different lightness values
 * - LightnessSlider in various states
 * - AlphaSlider in various states
 * - ColorPreviewBox with different colors
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = "w411dp-h891dp-normal-long-notround-any-420dpi-keyshidden-nonav")
class ComponentSnapshotTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ========== ColorWheel Tests ==========

    @Test
    fun colorWheel_flowerType_default() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_circleType_default() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_flowerType_lowDensity() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_flowerType_highDensity() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_circleType_lowDensity() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_circleType_highDensity() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_flowerType_darkLightness() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_circleType_darkLightness() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_flowerType_mediumLightness() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorWheel_flowerType_withTransparency() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    // ========== Slider Tests ==========

    @Test
    fun lightnessSlider_fullBrightness() {
        composeTestRule.setContent {
            TestTheme {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    LightnessSlider(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        color = Color.Red,
                        lightness = 1f,
                        onLightnessChange = {}
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightnessSlider_mediumBrightness() {
        composeTestRule.setContent {
            TestTheme {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    LightnessSlider(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        color = Color.Blue,
                        lightness = 0.5f,
                        onLightnessChange = {}
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightnessSlider_lowBrightness() {
        composeTestRule.setContent {
            TestTheme {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    LightnessSlider(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        color = Color.Green,
                        lightness = 0.2f,
                        onLightnessChange = {}
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightnessSlider_differentColors() {
        composeTestRule.setContent {
            TestTheme {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    LightnessSlider(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        color = Color(0xFFFF9800),
                        lightness = 0.7f,
                        onLightnessChange = {}
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun alphaSlider_fullOpacity() {
        composeTestRule.setContent {
            TestTheme {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    AlphaSlider(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        color = Color.Red,
                        alpha = 1f,
                        onAlphaChange = {}
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun alphaSlider_halfOpacity() {
        composeTestRule.setContent {
            TestTheme {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    AlphaSlider(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        color = Color.Blue,
                        alpha = 0.5f,
                        onAlphaChange = {}
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun alphaSlider_lowOpacity() {
        composeTestRule.setContent {
            TestTheme {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    AlphaSlider(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        color = Color.Green,
                        alpha = 0.2f,
                        onAlphaChange = {}
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun alphaSlider_differentColors() {
        composeTestRule.setContent {
            TestTheme {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    AlphaSlider(
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        color = Color.Magenta,
                        alpha = 0.8f,
                        onAlphaChange = {}
                    )
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // ========== ColorPreviewBox Tests ==========

    @Test
    fun colorPreviewBox_red() {
        composeTestRule.setContent {
            TestTheme {
                Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                    ColorPreviewBox(color = Color.Red)
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPreviewBox_blue() {
        composeTestRule.setContent {
            TestTheme {
                Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                    ColorPreviewBox(color = Color.Blue)
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPreviewBox_green() {
        composeTestRule.setContent {
            TestTheme {
                Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                    ColorPreviewBox(color = Color.Green)
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPreviewBox_customColor() {
        composeTestRule.setContent {
            TestTheme {
                Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                    ColorPreviewBox(color = Color(0xFF9C27B0))
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPreviewBox_transparent() {
        composeTestRule.setContent {
            TestTheme {
                Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                    ColorPreviewBox(color = Color(0x80FF0000))
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPreviewBox_black() {
        composeTestRule.setContent {
            TestTheme {
                Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                    ColorPreviewBox(color = Color.Black)
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPreviewBox_white() {
        composeTestRule.setContent {
            TestTheme {
                Box(modifier = Modifier.size(100.dp).padding(16.dp)) {
                    ColorPreviewBox(color = Color.White)
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}

@Composable
private fun TestTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface {
            content()
        }
    }
}
