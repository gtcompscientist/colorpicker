package co.csadev.colorpicker.sample.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.compose.ColorPickerDialog
import co.csadev.colorpicker.compose.hexStringWithAlpha
import co.csadev.colorpicker.sample.ViewCodeButton
import co.csadev.colorpicker.state.ColorPickerState

private const val SOURCE_CODE = """
@Composable
fun DialogExample() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.Green) }

    Button(onClick = { showDialog = true }) {
        Text("Pick Color")
    }

    if (showDialog) {
        ColorPickerDialog(
            onDismissRequest = { showDialog = false },
            onColorSelected = { color ->
                selectedColor = color
                showDialog = false
            },
            initialColor = selectedColor,
            title = "Choose a Color",
            confirmText = "Select",
            dismissText = "Cancel"
        )
    }
}
"""

/**
 * Demonstrates color picker dialogs with different configurations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen() {
    var color1 by remember { mutableStateOf(Color(0xFFE91E63)) }
    var color2 by remember { mutableStateOf(Color(0xFF9C27B0)) }
    var color3 by remember { mutableStateOf(Color(0xFF00BCD4)) }

    var showDialog1 by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var showDialog3 by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Color Picker Dialogs") },
                actions = {
                    ViewCodeButton(
                        code = SOURCE_CODE,
                        docsAnchor = "color-picker-dialog"
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
            text = "Color Picker Dialogs",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Different dialog configurations for various use cases",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Dialog 1: Full Featured
        DialogDemoCard(
            title = "Full Featured Dialog",
            description = "Flower wheel with all controls",
            color = color1,
            onClick = { showDialog1 = true }
        )

        // Dialog 2: Simple
        DialogDemoCard(
            title = "Simple Circle Wheel",
            description = "Circle wheel with basic controls",
            color = color2,
            onClick = { showDialog2 = true }
        )

        // Dialog 3: Minimal
        DialogDemoCard(
            title = "Minimal Dialog",
            description = "Wheel only, no sliders",
            color = color3,
            onClick = { showDialog3 = true }
        )
    }

    // Dialog 1
    if (showDialog1) {
        ColorPickerDialog(
            onDismissRequest = { showDialog1 = false },
            onColorSelected = { color1 = it },
            initialColor = color1,
            title = "Choose Color",
            wheelType = ColorPickerState.WheelType.FLOWER,
            showAlphaSlider = true,
            showLightnessSlider = true,
            showColorEdit = true
        )
    }

    // Dialog 2
    if (showDialog2) {
        ColorPickerDialog(
            onDismissRequest = { showDialog2 = false },
            onColorSelected = { color2 = it },
            initialColor = color2,
            title = "Pick a Color",
            wheelType = ColorPickerState.WheelType.CIRCLE,
            showAlphaSlider = false,
            showLightnessSlider = true,
            showColorEdit = false
        )
    }

    // Dialog 3
    if (showDialog3) {
        ColorPickerDialog(
            onDismissRequest = { showDialog3 = false },
            onColorSelected = { color3 = it },
            initialColor = color3,
            title = "Select Color",
            wheelType = ColorPickerState.WheelType.FLOWER,
            showAlphaSlider = false,
            showLightnessSlider = false,
            showColorEdit = false
        )
    }
    }
}

@Composable
private fun DialogDemoCard(
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.outlinedCardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = color.hexStringWithAlpha,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = "Open color picker",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}