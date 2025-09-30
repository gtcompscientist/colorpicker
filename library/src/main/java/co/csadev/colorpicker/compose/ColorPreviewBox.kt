package co.csadev.colorpicker.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A box that displays a color preview.
 *
 * @param color The color to display
 * @param modifier The modifier to apply to this box
 */
@Composable
fun ColorPreviewBox(
    color: Color,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(color)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = shape
            )
    )
}

@Preview
@Composable
private fun ColorPreviewBoxPreview() {
    ColorPreviewBox(color = Color.Red)
}
