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
import co.csadev.colorpicker.compose.ColorPickerDialog
import co.csadev.colorpicker.state.ColorPickerState
import org.junit.Rule
import org.junit.Test

/**
 * Snapshot tests across various device configurations.
 *
 * These tests capture how the ColorPicker renders on:
 * - Different phone sizes (small, medium, large)
 * - Tablets
 * - Different screen densities (ldpi, mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
 * - Different orientations (portrait, landscape)
 */
class DeviceConfigurationSnapshotTests {

    // ========== Phone - Pixel 5 (Default) ==========

    @get:Rule
    val paparazziPixel5 = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun pixel5_colorPicker_fullFeatured() {
        paparazziPixel5.snapshot {
            MaterialTheme {
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
    fun pixel5_dialog_fullFeatured() {
        paparazziPixel5.snapshot {
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

    // ========== Small Phone - Nexus 4 ==========

    @get:Rule
    val paparazziNexus4 = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_4,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun nexus4_colorPicker_fullFeatured() {
        paparazziNexus4.snapshot {
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
                            showColorEdit = true
                        )
                    }
                }
            }
        }
    }

    @Test
    fun nexus4_dialog_minimal() {
        paparazziNexus4.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Magenta,
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

    // ========== Large Phone - Pixel 6 Pro ==========

    @get:Rule
    val paparazziPixel6Pro = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6_PRO,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun pixel6Pro_colorPicker_fullFeatured() {
        paparazziPixel6Pro.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Cyan,
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
    fun pixel6Pro_dialog_fullFeatured() {
        paparazziPixel6Pro.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color(0xFFFF9800),
                        title = "Select Color",
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

    // ========== Tablet - Nexus 7 ==========

    @get:Rule
    val paparazziNexus7 = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_7,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun nexus7_colorPicker_fullFeatured() {
        paparazziNexus7.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Yellow,
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 12,
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
    fun nexus7_dialog_fullFeatured() {
        paparazziNexus7.snapshot {
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

    // ========== Large Tablet - Nexus 10 ==========

    @get:Rule
    val paparazziNexus10 = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_10,
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun nexus10_colorPicker_fullFeatured() {
        paparazziNexus10.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                        ColorPicker(
                            initialColor = Color(0xFF009688),
                            wheelType = ColorPickerState.WheelType.FLOWER,
                            density = 15,
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
    fun nexus10_dialog_fullFeatured() {
        paparazziNexus10.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Red,
                        title = "Pick Your Color",
                        confirmText = "Apply",
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

    // ========== Landscape Orientation - Pixel 5 ==========

    @get:Rule
    val paparazziPixel5Landscape = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(orientation = DeviceConfig.Orientation.landscape),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun pixel5Landscape_colorPicker_fullFeatured() {
        paparazziPixel5Landscape.snapshot {
            MaterialTheme {
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
    fun pixel5Landscape_dialog_fullFeatured() {
        paparazziPixel5Landscape.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Green,
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

    // ========== Different DPI - HDPI ==========

    @get:Rule
    val paparazziHdpi = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_4.copy(
            screenHeight = 1280,
            screenWidth = 720,
            density = DeviceConfig.Density.HDPI
        ),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun hdpi_colorPicker_fullFeatured() {
        paparazziHdpi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Magenta,
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

    // ========== Different DPI - XXHDPI ==========

    @get:Rule
    val paparazziXxhdpi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(density = DeviceConfig.Density.XXHDPI),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun xxhdpi_colorPicker_fullFeatured() {
        paparazziXxhdpi.snapshot {
            MaterialTheme {
                Surface {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        ColorPicker(
                            initialColor = Color.Cyan,
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

    // ========== Different DPI - XXXHDPI ==========

    @get:Rule
    val paparazziXxxhdpi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6_PRO.copy(density = DeviceConfig.Density.XXXHDPI),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun xxxhdpi_colorPicker_fullFeatured() {
        paparazziXxxhdpi.snapshot {
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
}
