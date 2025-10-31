package co.csadev.colorpicker.snapshots

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import co.csadev.colorpicker.compose.ColorPickerDialog
import co.csadev.colorpicker.compose.ColorPickerPreferenceItem
import co.csadev.colorpicker.state.ColorPickerState
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Snapshot tests for dialogs, preferences, and various device/theme configurations.
 *
 * These tests cover:
 * - ColorPickerDialog with various configurations
 * - ColorPickerPreferenceItem in different states
 * - Different device configurations
 * - Light and dark themes
 * - RTL layouts
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class DialogAndConfigurationSnapshotTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ========== Dialog Tests ==========

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
    fun dialog_fullFeatured_flowerWheel() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
    fun dialog_fullFeatured_circleWheel() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
    fun dialog_minimal() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    // ========== Preference Item Tests ==========

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
    fun preferenceItem_enabled_withSummary() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
    fun preferenceItem_disabled_withSummary() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
    fun preferenceItem_multipleItems() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    // ========== Theme Tests ==========

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
    fun dialog_darkTheme() {
        composeTestRule.setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi")
    fun preferenceItem_darkTheme() {
        composeTestRule.setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ColorPickerPreferenceItem(
                            title = "Primary Color",
                            summary = "Choose your primary theme color",
                            color = Color.Blue,
                            onColorChange = {},
                            enabled = true
                        )
                    }
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    // ========== Device Configuration Tests ==========

    @Test
    @Config(sdk = [33], qualifiers = "w320dp-h568dp-320dpi-small")
    fun dialog_smallPhone() {
        composeTestRule.setContent {
            TestTheme {
                ColorPickerDialog(
                    onDismissRequest = {},
                    onColorSelected = {},
                    initialColor = Color.Green,
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(sdk = [33], qualifiers = "w600dp-h960dp-320dpi-large")
    fun dialog_tablet() {
        composeTestRule.setContent {
            TestTheme {
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
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(sdk = [33], qualifiers = "w891dp-h411dp-420dpi-land")
    fun dialog_landscape() {
        composeTestRule.setContent {
            TestTheme {
                ColorPickerDialog(
                    onDismissRequest = {},
                    onColorSelected = {},
                    initialColor = Color.Blue,
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
        composeTestRule.onRoot().captureRoboImage()
    }

    // ========== Locale Tests (RTL) ==========

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi-ar-rSA-ldrtl")
    fun dialog_arabic_rtl() {
        composeTestRule.setContent {
            TestTheme {
                ColorPickerDialog(
                    onDismissRequest = {},
                    onColorSelected = {},
                    initialColor = Color.Blue,
                    title = "اختر اللون",
                    confirmText = "موافق",
                    dismissText = "إلغاء",
                    wheelType = ColorPickerState.WheelType.CIRCLE,
                    showAlphaSlider = true,
                    showLightnessSlider = true,
                    showColorEdit = true
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(sdk = [33], qualifiers = "w411dp-h891dp-420dpi-he-rIL-ldrtl")
    fun dialog_hebrew_rtl() {
        composeTestRule.setContent {
            TestTheme {
                ColorPickerDialog(
                    onDismissRequest = {},
                    onColorSelected = {},
                    initialColor = Color.Green,
                    title = "בחר צבע",
                    confirmText = "אישור",
                    dismissText = "ביטול",
                    wheelType = ColorPickerState.WheelType.FLOWER,
                    showAlphaSlider = true,
                    showLightnessSlider = true,
                    showColorEdit = true
                )
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
