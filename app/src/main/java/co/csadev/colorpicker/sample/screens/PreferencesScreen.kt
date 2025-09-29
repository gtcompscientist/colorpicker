package co.csadev.colorpicker.sample.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.preferencesDataStore
import co.csadev.colorpicker.compose.ColorPickerPreferenceItem
import co.csadev.colorpicker.compose.getColorFlow
import co.csadev.colorpicker.compose.saveColor
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "color_preferences")

/**
 * Demonstrates DataStore preference integration.
 */
@Composable
fun PreferencesScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val primaryColor by context.dataStore.getColorFlow(
        key = "primary_color",
        defaultColor = Color(0xFF6200EE)
    ).collectAsState(initial = Color(0xFF6200EE))

    val secondaryColor by context.dataStore.getColorFlow(
        key = "secondary_color",
        defaultColor = Color(0xFF03DAC6)
    ).collectAsState(initial = Color(0xFF03DAC6))

    val accentColor by context.dataStore.getColorFlow(
        key = "accent_color",
        defaultColor = Color(0xFFFF0266)
    ).collectAsState(initial = Color(0xFFFF0266))

    val backgroundColor by context.dataStore.getColorFlow(
        key = "background_color",
        defaultColor = Color(0xFFFAFAFA)
    ).collectAsState(initial = Color(0xFFFAFAFA))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Theme Preferences",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Customize your app theme colors with DataStore persistence",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Preview Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Theme Preview",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(backgroundColor)
                        .padding(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(primaryColor)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(secondaryColor)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(accentColor)
                        )
                    }
                }
            }
        }

        // Preferences
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                ColorPickerPreferenceItem(
                    title = "Primary Color",
                    color = primaryColor,
                    summary = "Main theme color",
                    onColorChange = { color ->
                        scope.launch {
                            context.dataStore.saveColor("primary_color", color)
                        }
                    },
                    showAlphaSlider = false,
                    showLightnessSlider = true
                )

                HorizontalDivider()

                ColorPickerPreferenceItem(
                    title = "Secondary Color",
                    color = secondaryColor,
                    summary = "Complementary theme color",
                    onColorChange = { color ->
                        scope.launch {
                            context.dataStore.saveColor("secondary_color", color)
                        }
                    },
                    showAlphaSlider = false,
                    showLightnessSlider = true
                )

                HorizontalDivider()

                ColorPickerPreferenceItem(
                    title = "Accent Color",
                    color = accentColor,
                    summary = "Highlight and emphasis color",
                    onColorChange = { color ->
                        scope.launch {
                            context.dataStore.saveColor("accent_color", color)
                        }
                    },
                    showAlphaSlider = false,
                    showLightnessSlider = true
                )

                HorizontalDivider()

                ColorPickerPreferenceItem(
                    title = "Background Color",
                    color = backgroundColor,
                    summary = "Main background color",
                    onColorChange = { color ->
                        scope.launch {
                            context.dataStore.saveColor("background_color", color)
                        }
                    },
                    showAlphaSlider = true,
                    showLightnessSlider = true
                )
            }
        }

        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "✨ DataStore Integration",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Colors are automatically persisted using Jetpack DataStore and will be restored when you reopen the app.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}