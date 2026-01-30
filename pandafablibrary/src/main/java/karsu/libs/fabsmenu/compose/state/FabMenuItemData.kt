package karsu.libs.fabsmenu.compose.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Expand direction for the FAB menu.
 */
enum class ExpandDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

/**
 * Position of labels relative to FAB items.
 */
enum class LabelsPosition {
    LEFT,
    RIGHT
}

/**
 * Size variants for FAB buttons.
 */
enum class FabSize(val sizeDp: Dp) {
    Mini(40.dp),
    Normal(56.dp),
    Large(72.dp)
}

/**
 * Configuration for the main menu button.
 */
data class MenuButtonConfig(
    val icon: ImageVector? = null,
    val backgroundColor: Color = Color.Unspecified,
    val iconTint: Color = Color.White,
    val size: FabSize = FabSize.Normal,
    val rotationAngle: Float = 135f,
    val contentDescription: String = "Menu"
)

/**
 * Configuration for FAB item labels.
 */
data class LabelConfig(
    val backgroundColor: Color = Color.White,
    val textColor: Color = Color.Black,
    val cornerRadius: Dp = 4.dp,
    val textPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    val clickable: Boolean = true,
    val showShadow: Boolean = true
)

/**
 * Padding values for label configuration.
 */
data class PaddingValues(
    val horizontal: Dp = 8.dp,
    val vertical: Dp = 4.dp
)

/**
 * Configuration for individual FAB items.
 */
data class FabItemConfig(
    val backgroundColor: Color = Color.White,
    val iconTint: Color = Color.Unspecified,
    val size: FabSize = FabSize.Mini,
    val showShadow: Boolean = true
)

/**
 * Data class representing a single FAB menu item.
 */
data class FabMenuItemData(
    val id: String,
    val icon: ImageVector,
    val label: String? = null,
    val contentDescription: String = label ?: id,
    val fabConfig: FabItemConfig = FabItemConfig(),
    val labelConfig: LabelConfig = LabelConfig(),
    val enabled: Boolean = true,
    val onClick: () -> Unit = {}
)
