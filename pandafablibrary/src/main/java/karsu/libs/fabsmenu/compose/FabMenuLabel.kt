package karsu.libs.fabsmenu.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import karsu.libs.fabsmenu.compose.state.LabelConfig

/**
 * A label composable for FAB menu items.
 *
 * @param text The text to display in the label.
 * @param config Configuration for the label appearance.
 * @param onClick Optional click handler for the label.
 * @param modifier Modifier for the label.
 */
@Composable
fun FabMenuLabel(
    text: String,
    config: LabelConfig = LabelConfig(),
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(config.cornerRadius)

    Surface(
        modifier = modifier
            .then(
                if (config.showShadow) {
                    Modifier.shadow(
                        elevation = 4.dp,
                        shape = shape,
                        clip = false
                    )
                } else {
                    Modifier
                }
            )
            .then(
                if (config.clickable && onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        shape = shape,
        color = config.backgroundColor
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = config.textPadding.horizontal,
                    vertical = config.textPadding.vertical
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = config.textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun FabMenuLabelDefaultPreview() {
    FabMenuLabel(
        text = "Download",
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun FabMenuLabelCustomPreview() {
    FabMenuLabel(
        text = "Custom Label",
        config = LabelConfig(
            backgroundColor = Color(0xFFE91E63),
            textColor = Color.White
        ),
        onClick = {}
    )
}
