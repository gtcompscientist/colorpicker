package co.csadev.colorpicker.sample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.R
import co.csadev.colorpicker.compose.ColorPicker
import co.csadev.colorpicker.sample.ViewCodeButton
import co.csadev.colorpicker.sample.WheelTypeSelector
import co.csadev.colorpicker.state.ColorPickerState

private const val SOURCE_CODE = """
@Composable
fun MyScreen() {
    var selectedColor by remember { mutableStateOf(Color.Blue) }

    ColorPicker(
        initialColor = selectedColor,
        onColorSelected = { color ->
            selectedColor = color
        }
    )
}
"""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimplestExampleScreen() {
    var selectedColor by remember { mutableStateOf(Color.Blue) }
    var wheelType by remember { mutableStateOf(ColorPickerState.WheelType.FLOWER) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simplest Example") },
                actions = {
                    ViewCodeButton(
                        code = SOURCE_CODE,
                        docsAnchor = "simplest-example"
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
                text = "Quickest way to get started",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.this_is_the_simplest_possible),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            WheelTypeSelector(
                wheelType = wheelType,
                onWheelTypeChange = { wheelType = it }
            )

            ColorPicker(
                initialColor = selectedColor,
                wheelType = wheelType,
                onColorSelected = { color ->
                    selectedColor = color
                }
            )
        }
    }
}
