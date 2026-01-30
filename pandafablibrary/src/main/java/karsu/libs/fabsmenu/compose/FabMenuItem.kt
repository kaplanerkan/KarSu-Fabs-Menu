package karsu.libs.fabsmenu.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import karsu.libs.fabsmenu.compose.state.FabItemConfig
import karsu.libs.fabsmenu.compose.state.FabMenuItemData
import karsu.libs.fabsmenu.compose.state.FabSize
import karsu.libs.fabsmenu.compose.state.FabsMenuDefaults
import karsu.libs.fabsmenu.compose.state.LabelConfig
import karsu.libs.fabsmenu.compose.state.LabelsPosition

/**
 * A single FAB menu item with optional label.
 *
 * @param icon The icon to display in the FAB.
 * @param label Optional label text.
 * @param labelsPosition Position of the label relative to the FAB.
 * @param fabConfig Configuration for the FAB appearance.
 * @param labelConfig Configuration for the label appearance.
 * @param animationProgress Animation progress from 0f to 1f.
 * @param onClick Click handler for the FAB item.
 * @param modifier Modifier for the item container.
 * @param enabled Whether the FAB is enabled.
 * @param contentDescription Content description for accessibility.
 */
@Composable
fun FabMenuItem(
    icon: ImageVector,
    label: String? = null,
    labelsPosition: LabelsPosition = LabelsPosition.LEFT,
    fabConfig: FabItemConfig = FabsMenuDefaults.fabItemConfig(),
    labelConfig: LabelConfig = FabsMenuDefaults.labelConfig(),
    animationProgress: Float = 1f,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String = label ?: ""
) {
    // Use animationProgress directly - no double animation
    Row(
        modifier = modifier
            .scale(animationProgress)
            .alpha(animationProgress),
        horizontalArrangement = Arrangement.spacedBy(FabsMenuDefaults.LabelSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label on left side
        if (label != null && labelsPosition == LabelsPosition.LEFT) {
            FabMenuLabel(
                text = label,
                config = labelConfig,
                onClick = if (labelConfig.clickable) onClick else null
            )
        }

        // FAB button
        when (fabConfig.size) {
            FabSize.Mini -> {
                SmallFloatingActionButton(
                    onClick = onClick,
                    modifier = Modifier.size(fabConfig.size.sizeDp),
                    shape = CircleShape,
                    containerColor = fabConfig.backgroundColor,
                    contentColor = fabConfig.iconTint,
                    elevation = if (fabConfig.showShadow) {
                        FloatingActionButtonDefaults.elevation()
                    } else {
                        FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = contentDescription,
                        tint = fabConfig.iconTint
                    )
                }
            }
            else -> {
                FloatingActionButton(
                    onClick = onClick,
                    modifier = Modifier.size(fabConfig.size.sizeDp),
                    shape = CircleShape,
                    containerColor = fabConfig.backgroundColor,
                    contentColor = fabConfig.iconTint,
                    elevation = if (fabConfig.showShadow) {
                        FloatingActionButtonDefaults.elevation()
                    } else {
                        FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = contentDescription,
                        tint = fabConfig.iconTint
                    )
                }
            }
        }

        // Label on right side
        if (label != null && labelsPosition == LabelsPosition.RIGHT) {
            FabMenuLabel(
                text = label,
                config = labelConfig,
                onClick = if (labelConfig.clickable) onClick else null
            )
        }
    }
}

/**
 * Overload that accepts FabMenuItemData directly.
 */
@Composable
fun FabMenuItem(
    data: FabMenuItemData,
    labelsPosition: LabelsPosition = LabelsPosition.LEFT,
    animationProgress: Float = 1f,
    modifier: Modifier = Modifier
) {
    FabMenuItem(
        icon = data.icon,
        label = data.label,
        labelsPosition = labelsPosition,
        fabConfig = data.fabConfig,
        labelConfig = data.labelConfig,
        animationProgress = animationProgress,
        onClick = data.onClick,
        modifier = modifier,
        enabled = data.enabled,
        contentDescription = data.contentDescription
    )
}

// Previews
@Preview(showBackground = true)
@Composable
private fun FabMenuItemWithLabelLeftPreview() {
    FabMenuItem(
        icon = Icons.Default.Download,
        label = "Download",
        labelsPosition = LabelsPosition.LEFT,
        fabConfig = FabsMenuDefaults.fabItemConfig(
            backgroundColor = Color(0xFF2196F3)
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun FabMenuItemWithLabelRightPreview() {
    FabMenuItem(
        icon = Icons.Default.Edit,
        label = "Edit",
        labelsPosition = LabelsPosition.RIGHT,
        fabConfig = FabsMenuDefaults.fabItemConfig(
            backgroundColor = Color(0xFF4CAF50)
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun FabMenuItemMiniPreview() {
    FabMenuItem(
        icon = Icons.Default.Download,
        label = "Download",
        labelsPosition = LabelsPosition.LEFT,
        fabConfig = FabsMenuDefaults.fabItemConfig(
            backgroundColor = Color(0xFFE91E63),
            size = FabSize.Mini
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun FabMenuItemNoLabelPreview() {
    FabMenuItem(
        icon = Icons.Default.Download,
        fabConfig = FabsMenuDefaults.fabItemConfig(
            backgroundColor = Color(0xFF9C27B0)
        ),
        onClick = {}
    )
}
