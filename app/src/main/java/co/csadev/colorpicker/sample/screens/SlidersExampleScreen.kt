package co.csadev.colorpicker.sample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.compose.AlphaSlider
import co.csadev.colorpicker.compose.ColorPreviewBox
import co.csadev.colorpicker.compose.LightnessSlider
import co.csadev.colorpicker.compose.applyLightness
import co.csadev.colorpicker.sample.ViewCodeButton

private const val SOURCE_CODE = """
@Composable
fun SlidersExample() {
    var color by remember { mutableStateOf(Color.Blue) }
    var lightness by remember { mutableStateOf(0.5f) }
    var alpha by remember { mutableStateOf(1f) }

    Column {
        // Color preview
        ColorPreviewBox(
            color = color.applyLightness(lightness).copy(alpha = alpha),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

        // Lightness slider
        LightnessSlider(
            color = color,
            lightness = lightness,
            onLightnessChange = { newLightness ->
                lightness = newLightness
            }
        )

        // Alpha slider
        AlphaSlider(
            color = color,
            alpha = alpha,
            onAlphaChange = { newAlpha ->
                alpha = newAlpha
            }
        )
    }
}
"""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlidersExampleScreen() {
    var color by remember { mutableStateOf(Color.Blue) }
    var lightness by remember { mutableFloatStateOf(0.5f) }
    var alpha by remember { mutableFloatStateOf(1f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Color Sliders") },
                actions = {
                    ViewCodeButton(
                        code = SOURCE_CODE,
                        docsAnchor = "color-sliders"
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Individual Slider Components",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Use lightness and alpha sliders separately for granular control.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Color preview
            ColorPreviewBox(
                color = color.applyLightness(lightness).copy(alpha = alpha),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            // Lightness slider
            LightnessSlider(
                color = color,
                lightness = lightness,
                onLightnessChange = { newLightness ->
                    lightness = newLightness
                }
            )

            // Alpha slider
            AlphaSlider(
                color = color,
                alpha = alpha,
                onAlphaChange = { newAlpha ->
                    alpha = newAlpha
                }
            )

            Text(
                text = "Current Values:",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Lightness: ${(lightness * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )

            Text(
                text = "Alpha: ${(alpha * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
        }
    }
}