package co.csadev.colorpicker.sample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.compose.AlphaSlider
import co.csadev.colorpicker.compose.ColorWheel
import co.csadev.colorpicker.compose.LightnessSlider
import co.csadev.colorpicker.compose.applyLightness
import co.csadev.colorpicker.compose.hexStringWithAlpha
import co.csadev.colorpicker.sample.ViewCodeButton
import co.csadev.colorpicker.sample.WheelTypeSelector
import co.csadev.colorpicker.state.ColorPickerState

private const val SOURCE_CODE = """
@Composable
fun CustomizableExample() {
    var color by remember { mutableStateOf(Color.Red) }
    var wheelType by remember { mutableStateOf(ColorPickerState.WheelType.CIRCLE) }
    var density by remember { mutableIntStateOf(12) }
    var lightness by remember { mutableFloatStateOf(1f) }
    var alpha by remember { mutableFloatStateOf(1f) }

    Column {
        ColorWheel(
            wheelType = wheelType,
            density = density,
            lightness = lightness,
            alpha = alpha,
            onColorSelected = { newColor ->
                color = newColor
            }
        )

        // Controls
        WheelTypeSelector(
            wheelType = wheelType,
            onWheelTypeChange = { wheelType = it }
        )

        Slider(
            value = density.toFloat(),
            onValueChange = { density = it.toInt() },
            valueRange = 2f..100f
        )

        LightnessSlider(
            color = color,
            lightness = lightness,
            onLightnessChange = { lightness = it }
        )

        AlphaSlider(
            color = color,
            alpha = alpha,
            onAlphaChange = { alpha = it }
        )
    }
}
"""

/**
 * Customizable color wheel with live controls.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizableScreen() {
    var baseColor by remember { mutableStateOf(Color.Red) }
    var wheelType by remember { mutableStateOf(ColorPickerState.WheelType.CIRCLE) }
    var density by remember { mutableIntStateOf(12) }
    var lightness by remember { mutableFloatStateOf(1f) }
    var alpha by remember { mutableFloatStateOf(1f) }
    var hexInput by remember { mutableStateOf("FFFF0000") }

    // Compute final color
    val finalColor = baseColor.applyLightness(lightness).copy(alpha = alpha)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Customizable Wheel") },
                actions = {
                    ViewCodeButton(
                        code = SOURCE_CODE,
                        docsAnchor = "customizable"
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
                text = "Live Customizable Color Wheel",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // Color Wheel
            ColorWheel(
                wheelType = wheelType,
                density = density,
                lightness = lightness,
                alpha = alpha,
                onColorSelected = { color ->
                    baseColor = color
                    hexInput = color.applyLightness(lightness).copy(alpha = alpha).hexStringWithAlpha
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Wheel Type Selector
            Text(
                text = "Wheel Type",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            WheelTypeSelector(
                wheelType = wheelType,
                onWheelTypeChange = { wheelType = it }
            )

            // Density Slider
            Text(
                text = "Density: $density",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("2", style = MaterialTheme.typography.bodySmall)
                Slider(
                    value = density.toFloat(),
                    onValueChange = { density = it.toInt() },
                    valueRange = 2f..100f,
                    modifier = Modifier.weight(1f)
                )
                Text("100", style = MaterialTheme.typography.bodySmall)
            }

            // Lightness Slider
            Text(
                text = "Lightness: ${(lightness * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            LightnessSlider(
                color = baseColor,
                lightness = lightness,
                onLightnessChange = { newLightness ->
                    lightness = newLightness
                    hexInput = baseColor.applyLightness(newLightness).copy(alpha = alpha).hexStringWithAlpha
                }
            )

            // Alpha Slider
            Text(
                text = "Alpha: ${(alpha * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            AlphaSlider(
                color = baseColor.applyLightness(lightness),
                alpha = alpha,
                onAlphaChange = { newAlpha ->
                    alpha = newAlpha
                    hexInput = baseColor.applyLightness(lightness).copy(alpha = newAlpha).hexStringWithAlpha
                }
            )

            // Hex Input
            Text(
                text = "Hex Color (AARRGGBB or RRGGBB)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = hexInput,
                onValueChange = { input ->
                    hexInput = input.uppercase()
                    // Try to parse the hex color
                    val hex = input.removePrefix("#").uppercase()
                    try {
                        when (hex.length) {
                            6 -> {
                                // RRGGBB format
                                val color = Color(hex.toLong(16) or 0xFF000000)
                                baseColor = color
                                alpha = 1f
                                lightness = 1f
                            }
                            8 -> {
                                // AARRGGBB format
                                val color = Color(hex.toLong(16))
                                baseColor = color.copy(alpha = 1f)
                                alpha = color.alpha
                                lightness = 1f
                            }
                        }
                    } catch (_: Exception) {
                        // Invalid hex, ignore
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                prefix = { Text("#") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters
                ),
                placeholder = { Text("FFFF0000") }
            )

            // Color Preview
            Text(
                text = "Final Color",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .drawBehind {
                        drawRect(color = finalColor)
                    }
            )
        }
    }
}