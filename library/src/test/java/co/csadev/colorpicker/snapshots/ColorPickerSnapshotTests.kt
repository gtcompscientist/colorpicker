package co.csadev.colorpicker.snapshots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.compose.ColorPicker
import co.csadev.colorpicker.state.ColorPickerState
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Snapshot tests for the full ColorPicker component using Roborazzi.
 *
 * These tests capture the complete ColorPicker with various configurations:
 * - Different initial colors
 * - Various wheel types
 * - Different combinations of enabled/disabled features
 * - Different density settings
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = "w411dp-h891dp-normal-long-notround-any-420dpi-keyshidden-nonav")
class ColorPickerSnapshotTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun colorPicker_fullFeatured_flowerWheel() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_fullFeatured_circleWheel() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_withoutColorEdit() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_withoutAlphaSlider() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_withoutLightnessSlider() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_withoutSliders() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_wheelOnly() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_withoutWheel() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_lowDensity() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun colorPicker_highDensity() {
        composeTestRule.setContent {
            TestTheme {
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
