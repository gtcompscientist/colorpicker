package co.csadev.colorpicker.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.sample.screens.ComparisonScreen
import co.csadev.colorpicker.sample.screens.DialogsScreen
import co.csadev.colorpicker.sample.screens.FullFeaturedScreen
import co.csadev.colorpicker.sample.screens.PreferencesScreen

/**
 * Main navigation container for the sample app.
 */
@Composable
fun ColorPickerSampleApp(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = modifier.fillMaxSize()) {
        // Content
        when (selectedTab) {
            0 -> FullFeaturedScreen()
            1 -> DialogsScreen()
            2 -> ComparisonScreen()
            3 -> PreferencesScreen()
        }

        // Bottom Navigation
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Palette, contentDescription = null) },
                label = { Text("Full Featured") },
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
                label = { Text("Dialogs") },
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Colorize, contentDescription = null) },
                label = { Text("Comparison") },
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                label = { Text("Settings") },
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 }
            )
        }
    }
}