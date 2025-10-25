package co.csadev.colorpicker.snapshots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import co.csadev.colorpicker.compose.ColorPicker
import co.csadev.colorpicker.compose.ColorPickerDialog
import co.csadev.colorpicker.compose.ColorPickerPreferenceItem
import co.csadev.colorpicker.state.ColorPickerState
import org.junit.Rule
import org.junit.Test

/**
 * Snapshot tests across various Material3 themes.
 *
 * These tests verify that the ColorPicker renders correctly with:
 * - Light theme
 * - Dark theme
 * - Custom color schemes
 * - Different Material3 surface colors
 */
class ThemeSnapshotTests {

    // ========== Light Theme Tests ==========

    @get:Rule
    val paparazziLightTheme = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun lightTheme_colorPicker_fullFeatured() {
        paparazziLightTheme.snapshot {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Blue,
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
    fun lightTheme_dialog_fullFeatured() {
        paparazziLightTheme.snapshot {
            MaterialTheme(colorScheme = lightColorScheme()) {
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
    fun lightTheme_preferenceItem() {
        paparazziLightTheme.snapshot {
            MaterialTheme(colorScheme = lightColorScheme()) {
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
    }

    // ========== Dark Theme Tests ==========

    @get:Rule
    val paparazziDarkTheme = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Dark.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun darkTheme_colorPicker_fullFeatured() {
        paparazziDarkTheme.snapshot {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Blue,
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
    fun darkTheme_dialog_fullFeatured() {
        paparazziDarkTheme.snapshot {
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
    }

    @Test
    fun darkTheme_preferenceItem() {
        paparazziDarkTheme.snapshot {
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
    }

    @Test
    fun darkTheme_colorPicker_circleWheel() {
        paparazziDarkTheme.snapshot {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Green,
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

    // ========== Custom Blue Theme ==========

    @get:Rule
    val paparazziBlueTheme = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun customBlueTheme_colorPicker() {
        paparazziBlueTheme.snapshot {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF1976D2),
                    secondary = Color(0xFF42A5F5),
                    tertiary = Color(0xFF90CAF9),
                    background = Color(0xFFE3F2FD),
                    surface = Color(0xFFBBDEFB)
                )
            ) {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFF1976D2),
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
    fun customBlueTheme_dialog() {
        paparazziBlueTheme.snapshot {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF1976D2),
                    secondary = Color(0xFF42A5F5),
                    tertiary = Color(0xFF90CAF9),
                    background = Color(0xFFE3F2FD),
                    surface = Color(0xFFBBDEFB)
                )
            ) {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color(0xFF1976D2),
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

    // ========== Custom Purple Theme ==========

    @get:Rule
    val paparazziPurpleTheme = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun customPurpleTheme_colorPicker() {
        paparazziPurpleTheme.snapshot {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF6200EA),
                    secondary = Color(0xFF9C27B0),
                    tertiary = Color(0xFFBA68C8),
                    background = Color(0xFFF3E5F5),
                    surface = Color(0xFFE1BEE7)
                )
            ) {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFF6200EA),
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

    // ========== Custom Green Theme ==========

    @get:Rule
    val paparazziGreenTheme = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun customGreenTheme_colorPicker() {
        paparazziGreenTheme.snapshot {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF2E7D32),
                    secondary = Color(0xFF4CAF50),
                    tertiary = Color(0xFF81C784),
                    background = Color(0xFFE8F5E9),
                    surface = Color(0xFFC8E6C9)
                )
            ) {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFF2E7D32),
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

    // ========== Custom Dark Blue Theme ==========

    @get:Rule
    val paparazziDarkBlueTheme = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Dark.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun customDarkBlueTheme_colorPicker() {
        paparazziDarkBlueTheme.snapshot {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF90CAF9),
                    secondary = Color(0xFF42A5F5),
                    tertiary = Color(0xFF1976D2),
                    background = Color(0xFF0D47A1),
                    surface = Color(0xFF1565C0)
                )
            ) {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFF90CAF9),
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
    fun customDarkBlueTheme_dialog() {
        paparazziDarkBlueTheme.snapshot {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF90CAF9),
                    secondary = Color(0xFF42A5F5),
                    tertiary = Color(0xFF1976D2),
                    background = Color(0xFF0D47A1),
                    surface = Color(0xFF1565C0)
                )
            ) {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color(0xFF90CAF9),
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

    // ========== High Contrast Theme ==========

    @get:Rule
    val paparazziHighContrast = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Dark.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun highContrastTheme_colorPicker() {
        paparazziHighContrast.snapshot {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color.White,
                    secondary = Color(0xFFFFFFFF),
                    tertiary = Color(0xFFCCCCCC),
                    background = Color.Black,
                    surface = Color(0xFF000000),
                    onPrimary = Color.Black,
                    onSecondary = Color.Black,
                    onBackground = Color.White,
                    onSurface = Color.White
                )
            ) {
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
}
