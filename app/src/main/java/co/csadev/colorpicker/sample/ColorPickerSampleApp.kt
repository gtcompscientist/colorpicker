package co.csadev.colorpicker.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
    var selectedTab by remember { mutableStateOf(ColorPickerTabs.SIMPLEST_EXAMPLE) }

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
        val tabIndex = selectedTab.ordinal
        // Top Tab Navigation
        SecondaryScrollableTabRow(
            tabIndex,
            Modifier,
            tabs = {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { selectedTab = ColorPickerTabs.entries[index] },
                        text = { Text(tab.title) },
                        icon = { Icon(tab.icon, contentDescription = null) }
                    )
                }
            }
        )

        // Content
        when (selectedTab) {
            ColorPickerTabs.SIMPLEST_EXAMPLE -> SimplestExampleScreen()
            ColorPickerTabs.FULL_FEATURED -> FullFeaturedScreen()
            ColorPickerTabs.DIALOGS -> DialogsScreen()
            ColorPickerTabs.SLIDERS -> SlidersExampleScreen()
            ColorPickerTabs.EVENT_HANDLING -> EventHandlingScreen()
            ColorPickerTabs.COMPARISON -> ComparisonScreen()
            ColorPickerTabs.PREFERENCES -> PreferencesScreen()
        }
    }
}

enum class ColorPickerTabs {
    SIMPLEST_EXAMPLE,
    FULL_FEATURED,
    DIALOGS,
    SLIDERS,
    EVENT_HANDLING,
    COMPARISON,
    PREFERENCES
}
