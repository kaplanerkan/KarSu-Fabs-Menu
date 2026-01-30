package karsu.libs.fabsmenu.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import karsu.libs.fabsmenu.compose.state.ExpandDirection
import karsu.libs.fabsmenu.compose.state.FabsMenuDefaults
import karsu.libs.fabsmenu.compose.state.FabsMenuState
import karsu.libs.fabsmenu.compose.state.LabelsPosition
import karsu.libs.fabsmenu.compose.state.MenuButtonConfig
import karsu.libs.fabsmenu.compose.state.rememberFabsMenuState
import karsu.libs.fabsmenu.compose.theme.LocalFabsMenuTheme

/**
 * A layout container that provides an overlay backdrop when the FAB menu is expanded.
 * This is the recommended way to use KarSuFabsMenu in a full-screen context.
 *
 * @param modifier Modifier for the layout.
 * @param state State holder for the menu.
 * @param expandDirection Direction in which the menu expands.
 * @param labelsPosition Position of labels relative to FAB items.
 * @param menuButtonConfig Configuration for the main menu button.
 * @param animationDurationMs Duration of expand/collapse animations.
 * @param itemSpacing Spacing between FAB items.
 * @param fabPadding Padding around the FAB menu from screen edges.
 * @param overlayColor Color of the overlay when menu is expanded.
 * @param closeOnOverlayClick Whether to close the menu when overlay is clicked.
 * @param onMenuExpanded Callback when menu is fully expanded.
 * @param onMenuCollapsed Callback when menu is fully collapsed.
 * @param content Main screen content.
 * @param fabContent DSL content block for defining FAB menu items.
 */
@Composable
fun KarSuFabsMenuLayout(
    modifier: Modifier = Modifier,
    state: FabsMenuState = rememberFabsMenuState(),
    expandDirection: ExpandDirection = FabsMenuDefaults.DefaultExpandDirection,
    labelsPosition: LabelsPosition = FabsMenuDefaults.DefaultLabelsPosition,
    menuButtonConfig: MenuButtonConfig = FabsMenuDefaults.menuButtonConfig(),
    animationDurationMs: Int = FabsMenuDefaults.AnimationDurationMs,
    itemSpacing: Dp = FabsMenuDefaults.ItemSpacing,
    fabPadding: Dp = 16.dp,
    overlayColor: Color = FabsMenuDefaults.OverlayColor,
    closeOnOverlayClick: Boolean = true,
    onMenuExpanded: () -> Unit = {},
    onMenuCollapsed: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
    fabContent: FabsMenuScope.() -> Unit
) {
    val theme = LocalFabsMenuTheme.current

    // Determine FAB alignment based on expand direction
    val fabAlignment = when (expandDirection) {
        ExpandDirection.UP -> Alignment.BottomEnd
        ExpandDirection.DOWN -> Alignment.TopEnd
        ExpandDirection.LEFT -> Alignment.CenterEnd
        ExpandDirection.RIGHT -> Alignment.CenterStart
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Main content
        content()

        // Overlay
        AnimatedVisibility(
            visible = state.isExpanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overlayColor)
                    .then(
                        if (closeOnOverlayClick) {
                            Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { state.collapse() }
                            )
                        } else {
                            Modifier
                        }
                    )
            )
        }

        // FAB Menu
        Box(
            modifier = Modifier
                .align(fabAlignment)
                .padding(fabPadding)
        ) {
            KarSuFabsMenu(
                state = state,
                expandDirection = expandDirection,
                labelsPosition = labelsPosition,
                menuButtonConfig = menuButtonConfig,
                animationDurationMs = animationDurationMs,
                itemSpacing = itemSpacing,
                onMenuExpanded = onMenuExpanded,
                onMenuCollapsed = onMenuCollapsed,
                content = fabContent
            )
        }
    }
}

/**
 * A simplified layout that positions the FAB menu in a corner of the screen.
 *
 * @param modifier Modifier for the layout.
 * @param state State holder for the menu.
 * @param fabAlignment Corner alignment for the FAB menu.
 * @param expandDirection Direction in which the menu expands.
 * @param labelsPosition Position of labels relative to FAB items.
 * @param menuButtonConfig Configuration for the main menu button.
 * @param animationDurationMs Duration of expand/collapse animations.
 * @param fabPadding Padding around the FAB menu from screen edges.
 * @param showOverlay Whether to show an overlay when expanded.
 * @param overlayColor Color of the overlay.
 * @param onMenuExpanded Callback when menu is fully expanded.
 * @param onMenuCollapsed Callback when menu is fully collapsed.
 * @param content Main screen content.
 * @param fabContent DSL content block for defining FAB menu items.
 */
@Composable
fun KarSuFabsMenuScaffold(
    modifier: Modifier = Modifier,
    state: FabsMenuState = rememberFabsMenuState(),
    fabAlignment: Alignment = Alignment.BottomEnd,
    expandDirection: ExpandDirection = FabsMenuDefaults.DefaultExpandDirection,
    labelsPosition: LabelsPosition = FabsMenuDefaults.DefaultLabelsPosition,
    menuButtonConfig: MenuButtonConfig = FabsMenuDefaults.menuButtonConfig(),
    animationDurationMs: Int = FabsMenuDefaults.AnimationDurationMs,
    fabPadding: Dp = 16.dp,
    showOverlay: Boolean = true,
    overlayColor: Color = FabsMenuDefaults.OverlayColor,
    onMenuExpanded: () -> Unit = {},
    onMenuCollapsed: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
    fabContent: FabsMenuScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Main content
        content()

        // Overlay
        if (showOverlay) {
            AnimatedVisibility(
                visible = state.isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(overlayColor)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { state.collapse() }
                        )
                )
            }
        }

        // FAB Menu
        Box(
            modifier = Modifier
                .align(fabAlignment)
                .padding(fabPadding)
        ) {
            KarSuFabsMenu(
                state = state,
                expandDirection = expandDirection,
                labelsPosition = labelsPosition,
                menuButtonConfig = menuButtonConfig,
                animationDurationMs = animationDurationMs,
                onMenuExpanded = onMenuExpanded,
                onMenuCollapsed = onMenuCollapsed,
                content = fabContent
            )
        }
    }
}
