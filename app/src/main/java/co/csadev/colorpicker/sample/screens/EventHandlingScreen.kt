package co.csadev.colorpicker.sample.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.compose.ColorPicker
import co.csadev.colorpicker.compose.hexStringWithAlpha
import co.csadev.colorpicker.sample.ViewCodeButton
import co.csadev.colorpicker.sample.WheelTypeSelector
import co.csadev.colorpicker.state.ColorPickerState

private const val SOURCE_CODE = """
@Composable
fun EventHandlingExample() {
    var currentColor by remember { mutableStateOf(Color.Yellow) }
    var previewColor by remember { mutableStateOf(Color.Yellow) }

    Column {
        // Live preview while dragging
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(previewColor)
        )

        // Confirmed color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(currentColor)
        )

        ColorPicker(
            initialColor = currentColor,
            onColorChanged = { color ->
                // Update preview while dragging
                previewColor = color
            },
            onColorSelected = { color ->
                // Update final color on release
                currentColor = color
                previewColor = color
            }
        )
    }
}
"""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventHandlingScreen() {
    var currentColor by remember { mutableStateOf(Color.Yellow) }
    var previewColor by remember { mutableStateOf(Color.Yellow) }
    var wheelType by remember { mutableStateOf(ColorPickerState.WheelType.FLOWER) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Handling") },
                actions = {
                    ViewCodeButton(
                        code = SOURCE_CODE,
                        docsAnchor = "event-handling"
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
                text = "Real-time Color Events",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Demonstrates onColorChanged (while dragging) vs onColorSelected (on release).",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            WheelTypeSelector(
                wheelType = wheelType,
                onWheelTypeChange = { wheelType = it }
            )

            // Live preview while dragging
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Live Preview",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(previewColor)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = previewColor.hexStringWithAlpha,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            color = if (previewColor.luminance() > 0.5f) Color.Black else Color.White
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Confirmed",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(currentColor)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentColor.hexStringWithAlpha,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            color = if (currentColor.luminance() > 0.5f) Color.Black else Color.White
                        )
                    }
                }
            }

            ColorPicker(
                initialColor = currentColor,
                wheelType = wheelType,
                onColorChanged = { color ->
                    // Update preview while dragging
                    previewColor = color
                },
                onColorSelected = { color ->
                    // Update final color on release
                    currentColor = color
                    previewColor = color
                }
            )
        }
    }
}
