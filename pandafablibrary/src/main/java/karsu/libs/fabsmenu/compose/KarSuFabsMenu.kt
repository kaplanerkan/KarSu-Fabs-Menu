package karsu.libs.fabsmenu.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import karsu.libs.fabsmenu.compose.animation.FabsMenuAnimations
import karsu.libs.fabsmenu.compose.state.ExpandDirection
import karsu.libs.fabsmenu.compose.state.FabItemConfig
import karsu.libs.fabsmenu.compose.state.FabMenuItemData
import karsu.libs.fabsmenu.compose.state.FabSize
import karsu.libs.fabsmenu.compose.state.FabsMenuDefaults
import karsu.libs.fabsmenu.compose.state.FabsMenuState
import karsu.libs.fabsmenu.compose.state.LabelConfig
import karsu.libs.fabsmenu.compose.state.LabelsPosition
import karsu.libs.fabsmenu.compose.state.MenuButtonConfig
import karsu.libs.fabsmenu.compose.state.rememberFabsMenuState
import karsu.libs.fabsmenu.compose.theme.LocalFabsMenuTheme
import kotlin.math.roundToInt

/**
 * DSL scope for building FAB menu items.
 */
class FabsMenuScope internal constructor() {
    internal val items = mutableListOf<FabMenuItemData>()

    /**
     * Adds a FAB item to the menu.
     *
     * @param id Unique identifier for the item.
     * @param icon The icon to display.
     * @param label Optional label text.
     * @param contentDescription Accessibility description.
     * @param fabConfig Configuration for the FAB appearance.
     * @param labelConfig Configuration for the label appearance.
     * @param enabled Whether the item is enabled.
     * @param onClick Click handler for the item.
     */
    fun item(
        id: String,
        icon: ImageVector,
        label: String? = null,
        contentDescription: String = label ?: id,
        fabConfig: FabItemConfig = FabsMenuDefaults.fabItemConfig(),
        labelConfig: LabelConfig = FabsMenuDefaults.labelConfig(),
        enabled: Boolean = true,
        onClick: () -> Unit = {}
    ) {
        items.add(
            FabMenuItemData(
                id = id,
                icon = icon,
                label = label,
                contentDescription = contentDescription,
                fabConfig = fabConfig,
                labelConfig = labelConfig,
                enabled = enabled,
                onClick = onClick
            )
        )
    }
}

/**
 * KarSu FABs Menu - A customizable Floating Action Button menu for Jetpack Compose.
 *
 * @param modifier Modifier for the menu container.
 * @param state State holder for the menu. Use [rememberFabsMenuState] to create.
 * @param expandDirection Direction in which the menu expands.
 * @param labelsPosition Position of labels relative to FAB items.
 * @param menuButtonConfig Configuration for the main menu button.
 * @param animationDurationMs Duration of expand/collapse animations in milliseconds.
 * @param itemSpacing Spacing between FAB items.
 * @param onMenuExpanded Callback when menu is fully expanded.
 * @param onMenuCollapsed Callback when menu is fully collapsed.
 * @param content DSL content block for defining menu items.
 */
@Composable
fun KarSuFabsMenu(
    modifier: Modifier = Modifier,
    state: FabsMenuState = rememberFabsMenuState(),
    expandDirection: ExpandDirection = FabsMenuDefaults.DefaultExpandDirection,
    labelsPosition: LabelsPosition = FabsMenuDefaults.DefaultLabelsPosition,
    menuButtonConfig: MenuButtonConfig = FabsMenuDefaults.menuButtonConfig(),
    animationDurationMs: Int = FabsMenuDefaults.AnimationDurationMs,
    itemSpacing: Dp = FabsMenuDefaults.ItemSpacing,
    onMenuExpanded: () -> Unit = {},
    onMenuCollapsed: () -> Unit = {},
    content: FabsMenuScope.() -> Unit
) {
    val theme = LocalFabsMenuTheme.current

    // Build items using the DSL
    val scope = remember { FabsMenuScope() }
    scope.items.clear()
    scope.content()
    val items = scope.items.toList()

    // Menu button rotation animation
    val menuRotation by animateFloatAsState(
        targetValue = if (state.isExpanded) menuButtonConfig.rotationAngle else 0f,
        animationSpec = FabsMenuAnimations.rotationSpec(animationDurationMs / 2),
        label = "menuRotation",
        finishedListener = {
            if (state.isExpanded) {
                onMenuExpanded()
            } else {
                onMenuCollapsed()
            }
        }
    )

    val menuButtonColor = if (menuButtonConfig.backgroundColor != Color.Unspecified) {
        menuButtonConfig.backgroundColor
    } else {
        theme.colors.menuButtonBackground
    }

    val menuIcon = menuButtonConfig.icon ?: Icons.Default.Add

    // Determine alignment based on expand direction
    val alignment = when (expandDirection) {
        ExpandDirection.UP -> Alignment.BottomCenter
        ExpandDirection.DOWN -> Alignment.TopCenter
        ExpandDirection.LEFT -> Alignment.CenterEnd
        ExpandDirection.RIGHT -> Alignment.CenterStart
    }

    // Vertical arrangement for Column
    val verticalArrangement: Arrangement.Vertical = when (expandDirection) {
        ExpandDirection.UP -> Arrangement.spacedBy(itemSpacing, Alignment.Bottom)
        ExpandDirection.DOWN -> Arrangement.spacedBy(itemSpacing, Alignment.Top)
        else -> Arrangement.spacedBy(itemSpacing)
    }

    // Horizontal arrangement for Row
    val horizontalArrangement: Arrangement.Horizontal = when (expandDirection) {
        ExpandDirection.LEFT -> Arrangement.spacedBy(itemSpacing, Alignment.End)
        ExpandDirection.RIGHT -> Arrangement.spacedBy(itemSpacing, Alignment.Start)
        else -> Arrangement.spacedBy(itemSpacing)
    }

    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        // Menu items
        when (expandDirection) {
            ExpandDirection.UP, ExpandDirection.DOWN -> {
                Column(
                    horizontalAlignment = when (labelsPosition) {
                        LabelsPosition.LEFT -> Alignment.End
                        LabelsPosition.RIGHT -> Alignment.Start
                    },
                    verticalArrangement = verticalArrangement
                ) {
                    if (expandDirection == ExpandDirection.DOWN) {
                        MenuButton(
                            icon = menuIcon,
                            rotation = menuRotation,
                            size = menuButtonConfig.size,
                            backgroundColor = menuButtonColor,
                            iconTint = menuButtonConfig.iconTint,
                            contentDescription = menuButtonConfig.contentDescription,
                            onClick = { state.toggle() }
                        )
                    }

                    items.forEachIndexed { index, itemData ->
                        val itemIndex = if (expandDirection == ExpandDirection.UP) {
                            items.size - 1 - index
                        } else {
                            index
                        }

                        val delay = FabsMenuAnimations.calculateStaggerDelay(
                            index = itemIndex,
                            totalItems = items.size,
                            baseDelayMs = FabsMenuDefaults.StaggerDelayMs,
                            isExpanding = state.isExpanded
                        )

                        val itemProgress by animateFloatAsState(
                            targetValue = if (state.isExpanded) 1f else 0f,
                            animationSpec = if (state.isExpanded) {
                                FabsMenuAnimations.expandSpec(animationDurationMs, delay)
                            } else {
                                FabsMenuAnimations.collapseSpec(animationDurationMs / 2, delay)
                            },
                            label = "itemProgress_$index"
                        )

                        if (itemProgress > 0f) {
                            val translationYValue = when (expandDirection) {
                                ExpandDirection.UP -> (1f - itemProgress) * 50f
                                ExpandDirection.DOWN -> (1f - itemProgress) * -50f
                                ExpandDirection.LEFT, ExpandDirection.RIGHT -> 0f
                            }

                            FabMenuItem(
                                data = itemData,
                                labelsPosition = labelsPosition,
                                animationProgress = itemProgress,
                                modifier = Modifier.offset {
                                    IntOffset(0, (translationYValue * density).roundToInt())
                                }
                            )
                        }
                    }

                    if (expandDirection == ExpandDirection.UP) {
                        MenuButton(
                            icon = menuIcon,
                            rotation = menuRotation,
                            size = menuButtonConfig.size,
                            backgroundColor = menuButtonColor,
                            iconTint = menuButtonConfig.iconTint,
                            contentDescription = menuButtonConfig.contentDescription,
                            onClick = { state.toggle() }
                        )
                    }
                }
            }

            ExpandDirection.LEFT, ExpandDirection.RIGHT -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = horizontalArrangement
                ) {
                    if (expandDirection == ExpandDirection.RIGHT) {
                        MenuButton(
                            icon = menuIcon,
                            rotation = menuRotation,
                            size = menuButtonConfig.size,
                            backgroundColor = menuButtonColor,
                            iconTint = menuButtonConfig.iconTint,
                            contentDescription = menuButtonConfig.contentDescription,
                            onClick = { state.toggle() }
                        )
                    }

                    items.forEachIndexed { index, itemData ->
                        val itemIndex = if (expandDirection == ExpandDirection.LEFT) {
                            items.size - 1 - index
                        } else {
                            index
                        }

                        val delay = FabsMenuAnimations.calculateStaggerDelay(
                            index = itemIndex,
                            totalItems = items.size,
                            baseDelayMs = FabsMenuDefaults.StaggerDelayMs,
                            isExpanding = state.isExpanded
                        )

                        val itemProgress by animateFloatAsState(
                            targetValue = if (state.isExpanded) 1f else 0f,
                            animationSpec = if (state.isExpanded) {
                                FabsMenuAnimations.expandSpec(animationDurationMs, delay)
                            } else {
                                FabsMenuAnimations.collapseSpec(animationDurationMs / 2, delay)
                            },
                            label = "itemProgress_$index"
                        )

                        if (itemProgress > 0f) {
                            val translationXValue = when (expandDirection) {
                                ExpandDirection.LEFT -> (1f - itemProgress) * 50f
                                ExpandDirection.RIGHT -> (1f - itemProgress) * -50f
                                ExpandDirection.UP, ExpandDirection.DOWN -> 0f
                            }

                            FabMenuItem(
                                data = itemData,
                                labelsPosition = labelsPosition,
                                animationProgress = itemProgress,
                                modifier = Modifier.offset {
                                    IntOffset((translationXValue * density).roundToInt(), 0)
                                }
                            )
                        }
                    }

                    if (expandDirection == ExpandDirection.LEFT) {
                        MenuButton(
                            icon = menuIcon,
                            rotation = menuRotation,
                            size = menuButtonConfig.size,
                            backgroundColor = menuButtonColor,
                            iconTint = menuButtonConfig.iconTint,
                            contentDescription = menuButtonConfig.contentDescription,
                            onClick = { state.toggle() }
                        )
                    }
                }
            }
        }
    }
}

/**
 * The main menu button that toggles the FAB menu.
 */
@Composable
private fun MenuButton(
    icon: ImageVector,
    rotation: Float,
    size: FabSize,
    backgroundColor: Color,
    iconTint: Color,
    contentDescription: String,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .size(size.sizeDp)
            .rotate(rotation),
        shape = CircleShape,
        containerColor = backgroundColor,
        contentColor = iconTint,
        elevation = FloatingActionButtonDefaults.elevation()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint
        )
    }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun KarSuFabsMenuExpandUpPreview() {
    val state = rememberFabsMenuState(initialExpanded = true)
    KarSuFabsMenu(
        state = state,
        expandDirection = ExpandDirection.UP,
        labelsPosition = LabelsPosition.LEFT,
        menuButtonConfig = FabsMenuDefaults.menuButtonConfig(
            backgroundColor = Color(0xFFE91E63)
        )
    ) {
        item(
            id = "download",
            icon = Icons.Default.Download,
            label = "Download",
            fabConfig = FabsMenuDefaults.fabItemConfig(
                backgroundColor = Color(0xFF2196F3)
            )
        ) {}
        item(
            id = "edit",
            icon = Icons.Default.Edit,
            label = "Edit",
            fabConfig = FabsMenuDefaults.fabItemConfig(
                backgroundColor = Color(0xFF4CAF50)
            )
        ) {}
        item(
            id = "share",
            icon = Icons.Default.Share,
            label = "Share",
            fabConfig = FabsMenuDefaults.fabItemConfig(
                backgroundColor = Color(0xFFFF9800)
            )
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun KarSuFabsMenuExpandDownPreview() {
    val state = rememberFabsMenuState(initialExpanded = true)
    KarSuFabsMenu(
        state = state,
        expandDirection = ExpandDirection.DOWN,
        labelsPosition = LabelsPosition.LEFT,
        menuButtonConfig = FabsMenuDefaults.menuButtonConfig(
            backgroundColor = Color(0xFF26A69A)
        )
    ) {
        item(
            id = "download",
            icon = Icons.Default.Download,
            fabConfig = FabsMenuDefaults.fabItemConfig(
                backgroundColor = Color(0xFF00BCD4),
                size = FabSize.Mini
            )
        ) {}
        item(
            id = "edit",
            icon = Icons.Default.Edit,
            fabConfig = FabsMenuDefaults.fabItemConfig(
                backgroundColor = Color(0xFF009688),
                size = FabSize.Mini
            )
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun KarSuFabsMenuCollapsedPreview() {
    val state = rememberFabsMenuState(initialExpanded = false)
    KarSuFabsMenu(
        state = state,
        expandDirection = ExpandDirection.UP,
        menuButtonConfig = FabsMenuDefaults.menuButtonConfig(
            backgroundColor = Color(0xFFE91E63)
        )
    ) {
        item(
            id = "download",
            icon = Icons.Default.Download,
            label = "Download"
        ) {}
    }
}
