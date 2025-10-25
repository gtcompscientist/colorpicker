package co.csadev.colorpicker.snapshots

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import co.csadev.colorpicker.compose.ColorPickerDialog
import co.csadev.colorpicker.state.ColorPickerState
import org.junit.Rule
import org.junit.Test
import java.util.Locale

/**
 * Snapshot tests across various locales.
 *
 * These tests verify that the ColorPicker renders correctly in different languages
 * and locales, particularly for dialog button text and any locale-sensitive formatting.
 */
class LocaleSnapshotTests {

    // ========== English (US) ==========

    @get:Rule
    val paparazziEnglishUS = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "en-rUS"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun englishUS_dialog_fullFeatured() {
        paparazziEnglishUS.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Blue,
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

    // ========== Spanish (Spain) ==========

    @get:Rule
    val paparazziSpanish = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "es-rES"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun spanish_dialog_fullFeatured() {
        paparazziSpanish.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Red,
                        title = "Elegir Color",
                        confirmText = "Aceptar",
                        dismissText = "Cancelar",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== French (France) ==========

    @get:Rule
    val paparazziFrench = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "fr-rFR"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun french_dialog_fullFeatured() {
        paparazziFrench.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Green,
                        title = "Choisir une Couleur",
                        confirmText = "OK",
                        dismissText = "Annuler",
                        wheelType = ColorPickerState.WheelType.CIRCLE,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== German (Germany) ==========

    @get:Rule
    val paparazziGerman = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "de-rDE"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun german_dialog_fullFeatured() {
        paparazziGerman.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Magenta,
                        title = "Farbe Wählen",
                        confirmText = "OK",
                        dismissText = "Abbrechen",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Italian (Italy) ==========

    @get:Rule
    val paparazziItalian = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "it-rIT"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun italian_dialog_fullFeatured() {
        paparazziItalian.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Cyan,
                        title = "Scegli Colore",
                        confirmText = "OK",
                        dismissText = "Annulla",
                        wheelType = ColorPickerState.WheelType.CIRCLE,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Portuguese (Brazil) ==========

    @get:Rule
    val paparazziPortuguese = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "pt-rBR"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun portugueseBrazil_dialog_fullFeatured() {
        paparazziPortuguese.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Yellow,
                        title = "Escolher Cor",
                        confirmText = "OK",
                        dismissText = "Cancelar",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Japanese (Japan) ==========

    @get:Rule
    val paparazziJapanese = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "ja-rJP"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun japanese_dialog_fullFeatured() {
        paparazziJapanese.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color(0xFFFF9800),
                        title = "色を選択",
                        confirmText = "OK",
                        dismissText = "キャンセル",
                        wheelType = ColorPickerState.WheelType.CIRCLE,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Korean (South Korea) ==========

    @get:Rule
    val paparazziKorean = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "ko-rKR"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun korean_dialog_fullFeatured() {
        paparazziKorean.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color(0xFF9C27B0),
                        title = "색상 선택",
                        confirmText = "확인",
                        dismissText = "취소",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Chinese (Simplified - China) ==========

    @get:Rule
    val paparazziChineseSimplified = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "zh-rCN"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun chineseSimplified_dialog_fullFeatured() {
        paparazziChineseSimplified.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color(0xFF009688),
                        title = "选择颜色",
                        confirmText = "确定",
                        dismissText = "取消",
                        wheelType = ColorPickerState.WheelType.CIRCLE,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Chinese (Traditional - Taiwan) ==========

    @get:Rule
    val paparazziChineseTraditional = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "zh-rTW"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun chineseTraditional_dialog_fullFeatured() {
        paparazziChineseTraditional.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Red,
                        title = "選擇顏色",
                        confirmText = "確定",
                        dismissText = "取消",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Arabic (Saudi Arabia) - RTL ==========

    @get:Rule
    val paparazziArabic = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "ar-rSA"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun arabic_dialog_fullFeatured() {
        paparazziArabic.snapshot {
            MaterialTheme {
                Surface {
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
        }
    }

    // ========== Hebrew (Israel) - RTL ==========

    @get:Rule
    val paparazziHebrew = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "he-rIL"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun hebrew_dialog_fullFeatured() {
        paparazziHebrew.snapshot {
            MaterialTheme {
                Surface {
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
        }
    }

    // ========== Russian (Russia) ==========

    @get:Rule
    val paparazziRussian = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "ru-rRU"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun russian_dialog_fullFeatured() {
        paparazziRussian.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Magenta,
                        title = "Выберите Цвет",
                        confirmText = "ОК",
                        dismissText = "Отмена",
                        wheelType = ColorPickerState.WheelType.CIRCLE,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }

    // ========== Hindi (India) ==========

    @get:Rule
    val paparazziHindi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(locale = "hi-rIN"),
        theme = "android:Theme.Material3.Light.NoActionBar",
        maxPercentDifference = 0.0
    )

    @Test
    fun hindi_dialog_fullFeatured() {
        paparazziHindi.snapshot {
            MaterialTheme {
                Surface {
                    ColorPickerDialog(
                        onDismissRequest = {},
                        onColorSelected = {},
                        initialColor = Color.Cyan,
                        title = "रंग चुनें",
                        confirmText = "ठीक है",
                        dismissText = "रद्द करें",
                        wheelType = ColorPickerState.WheelType.FLOWER,
                        showAlphaSlider = true,
                        showLightnessSlider = true,
                        showColorEdit = true
                    )
                }
            }
        }
    }
}
