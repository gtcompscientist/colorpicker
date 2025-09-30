package co.csadev.colorpicker.sample

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.FilterVintage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.csadev.colorpicker.state.ColorPickerState

/**
 * Segmented button for selecting between Flower and Circle wheel types.
 */
@Composable
fun WheelTypeSelector(
    wheelType: ColorPickerState.WheelType,
    onWheelTypeChange: (ColorPickerState.WheelType) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        SegmentedButton(
            selected = wheelType == ColorPickerState.WheelType.FLOWER,
            onClick = { onWheelTypeChange(ColorPickerState.WheelType.FLOWER) },
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            icon = {
                SegmentedButtonDefaults.Icon(wheelType == ColorPickerState.WheelType.FLOWER) {
                    Icon(
                        imageVector = Icons.Default.FilterVintage,
                        contentDescription = "Flower wheel",
                        tint = if (wheelType == ColorPickerState.WheelType.FLOWER) {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
        ) {
            Text("Flower")
        }

        SegmentedButton(
            selected = wheelType == ColorPickerState.WheelType.CIRCLE,
            onClick = { onWheelTypeChange(ColorPickerState.WheelType.CIRCLE) },
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            icon = {
                SegmentedButtonDefaults.Icon(wheelType == ColorPickerState.WheelType.CIRCLE) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = "Circle wheel",
                        tint = if (wheelType == ColorPickerState.WheelType.CIRCLE) {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
        ) {
            Text("Circle")
        }
    }
}
