package karsu.libs.fabsmenu.compose.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Default values for the FabsMenu composables.
 */
object FabsMenuDefaults {

    /**
     * Default animation duration in milliseconds.
     */
    const val AnimationDurationMs = 300

    /**
     * Default stagger delay between items in milliseconds.
     */
    const val StaggerDelayMs = 30

    /**
     * Default spacing between FAB items.
     */
    val ItemSpacing: Dp = 16.dp

    /**
     * Default spacing between FAB and its label.
     */
    val LabelSpacing: Dp = 12.dp

    /**
     * Default overlay color when menu is expanded.
     */
    val OverlayColor: Color = Color.Black.copy(alpha = 0.5f)

    /**
     * Default rotation angle for the menu button when expanded.
     */
    const val MenuButtonRotation = 135f

    /**
     * Default expand direction.
     */
    val DefaultExpandDirection = ExpandDirection.UP

    /**
     * Default labels position.
     */
    val DefaultLabelsPosition = LabelsPosition.LEFT

    /**
     * Default menu button configuration.
     */
    fun menuButtonConfig(
        icon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Default.Add,
        backgroundColor: Color = Color(0xFFE91E63), // Pink default color
        iconTint: Color = Color.White,
        size: FabSize = FabSize.Normal,
        rotationAngle: Float = MenuButtonRotation
    ) = MenuButtonConfig(
        icon = icon,
        backgroundColor = backgroundColor,
        iconTint = iconTint,
        size = size,
        rotationAngle = rotationAngle
    )

    /**
     * Default FAB item configuration.
     */
    fun fabItemConfig(
        backgroundColor: Color = Color(0xFFE91E63),
        iconTint: Color = Color.White,
        size: FabSize = FabSize.Mini
    ) = FabItemConfig(
        backgroundColor = backgroundColor,
        iconTint = iconTint,
        size = size
    )

    /**
     * Default label configuration.
     */
    fun labelConfig(
        backgroundColor: Color = Color.White,
        textColor: Color = Color.Black,
        cornerRadius: Dp = 4.dp,
        clickable: Boolean = true
    ) = LabelConfig(
        backgroundColor = backgroundColor,
        textColor = textColor,
        cornerRadius = cornerRadius,
        clickable = clickable
    )
}
