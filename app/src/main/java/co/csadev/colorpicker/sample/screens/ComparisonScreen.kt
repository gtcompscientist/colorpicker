package co.csadev.colorpicker.sample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.compose.ColorWheel
import co.csadev.colorpicker.state.ColorPickerState

/**
 * Compares Flower vs Circle wheel types side by side.
 */
@Composable
fun ComparisonScreen() {
    var flowerColor by remember { mutableStateOf(Color(0xFFFF9800)) }
    var circleColor by remember { mutableStateOf(Color(0xFF4CAF50)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Wheel Type Comparison",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Compare the Flower and Circle wheel styles",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Flower Wheel
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Flower Wheel",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Organic petal-like appearance with varying circle sizes",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ColorWheel(
                    wheelType = ColorPickerState.WheelType.FLOWER,
                    density = 10,
                    lightness = 1f,
                    alpha = 1f,
                    onColorSelected = { color ->
                        flowerColor = color
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Best for: Creative applications, artistic tools",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Circle Wheel
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Circle Wheel",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Clean uniform circles for precise color selection",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ColorWheel(
                    wheelType = ColorPickerState.WheelType.CIRCLE,
                    density = 12,
                    lightness = 1f,
                    alpha = 1f,
                    onColorSelected = { color ->
                        circleColor = color
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Best for: Professional tools, precise control",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Density Comparison
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Low Density (Faster)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                ColorWheel(
                    wheelType = ColorPickerState.WheelType.CIRCLE,
                    density = 6,
                    lightness = 1f,
                    alpha = 1f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "High Density (More Detail)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                ColorWheel(
                    wheelType = ColorPickerState.WheelType.CIRCLE,
                    density = 15,
                    lightness = 1f,
                    alpha = 1f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}