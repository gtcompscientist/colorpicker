package co.csadev.colorpicker.sample.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.compose.ColorPicker
import co.csadev.colorpicker.compose.hexStringWithAlpha
import co.csadev.colorpicker.sample.ViewCodeButton
import co.csadev.colorpicker.state.ColorPickerState

private const val SOURCE_CODE = """
@Composable
fun FullFeaturedColorPicker() {
    var selectedColor by remember { mutableStateOf(Color(0xFF2196F3)) }

    ColorPicker(
        initialColor = selectedColor,
        wheelType = ColorPickerState.WheelType.FLOWER,
        density = 10,
        showColorWheel = true,
        showAlphaSlider = true,
        showLightnessSlider = true,
        showColorEdit = true,
        onColorChanged = { color ->
            println("Color changing: ${'$'}{color.hexStringWithAlpha}")
        },
        onColorSelected = { color ->
            selectedColor = color
        }
    )
}
"""

/**
 * Demonstrates the full-featured color picker with all options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullFeaturedScreen() {
    var selectedColor by remember { mutableStateOf(Color(0xFF2196F3)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Full Featured") },
                actions = {
                    ViewCodeButton(
                        code = SOURCE_CODE,
                        docsAnchor = "simple-color-picker"
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
            // Header
        Text(
            text = "Full Featured Color Picker",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Interactive color wheel with all controls enabled",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Color Preview Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = selectedColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selected Color",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (selectedColor.luminance() > 0.5f) Color.Black else Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = selectedColor.hexStringWithAlpha,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedColor.luminance() > 0.5f) Color.Black else Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ColorInfo("R", (selectedColor.red * 255).toInt())
                    ColorInfo("G", (selectedColor.green * 255).toInt())
                    ColorInfo("B", (selectedColor.blue * 255).toInt())
                    ColorInfo("A", (selectedColor.alpha * 255).toInt())
                }
            }
        }

        // Color Picker
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            ColorPicker(
                modifier = Modifier.padding(16.dp),
                initialColor = selectedColor,
                wheelType = ColorPickerState.WheelType.FLOWER,
                density = 10,
                showColorWheel = true,
                showAlphaSlider = true,
                showLightnessSlider = true,
                showColorEdit = true,
                onColorSelected = { color ->
                    selectedColor = color
                }
            )
        }
    }
    }
}

@Composable
private fun ColorInfo(label: String, value: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (value > 127) Color.Black else Color.White
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (value > 127) Color.Black else Color.White
        )
    }
}