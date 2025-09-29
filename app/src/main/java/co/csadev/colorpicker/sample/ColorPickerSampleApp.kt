package co.csadev.colorpicker.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import co.csadev.colorpicker.sample.screens.ComparisonScreen
import co.csadev.colorpicker.sample.screens.DialogsScreen
import co.csadev.colorpicker.sample.screens.EventHandlingScreen
import co.csadev.colorpicker.sample.screens.FullFeaturedScreen
import co.csadev.colorpicker.sample.screens.PreferencesScreen
import co.csadev.colorpicker.sample.screens.SimplestExampleScreen
import co.csadev.colorpicker.sample.screens.SlidersExampleScreen

/**
 * Navigation tab definition.
 */
private data class NavigationTab(
    val title: String,
    val icon: ImageVector
)

/**
 * Main navigation container for the sample app.
 */
@Composable
fun ColorPickerSampleApp(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        NavigationTab("Quick Start", Icons.Default.RocketLaunch),
        NavigationTab("Full Featured", Icons.Default.Palette),
        NavigationTab("Dialogs", Icons.Default.Dashboard),
        NavigationTab("Sliders", Icons.Default.Tune),
        NavigationTab("Events", Icons.Default.Event),
        NavigationTab("Comparison", Icons.Default.Colorize),
        NavigationTab("Preferences", Icons.Default.Settings)
    )

    Column(modifier = modifier.fillMaxSize()) {
        // Top Tab Navigation
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 0.dp
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(tab.title) },
                    icon = { Icon(tab.icon, contentDescription = null) }
                )
            }
        }

        // Content
        when (selectedTab) {
            0 -> SimplestExampleScreen()
            1 -> FullFeaturedScreen()
            2 -> DialogsScreen()
            3 -> SlidersExampleScreen()
            4 -> EventHandlingScreen()
            5 -> ComparisonScreen()
            6 -> PreferencesScreen()
        }
    }
}