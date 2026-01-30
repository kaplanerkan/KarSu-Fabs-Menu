package karsu.libs.fabsmenu.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Color scheme for the FABs menu.
 */
@Immutable
data class FabsMenuColors(
    val menuButtonBackground: Color = Color(0xFFE91E63),
    val menuButtonIcon: Color = Color.White,
    val fabItemBackground: Color = Color(0xFFE91E63),
    val fabItemIcon: Color = Color.White,
    val labelBackground: Color = Color.White,
    val labelText: Color = Color.Black,
    val overlayBackground: Color = Color.Black.copy(alpha = 0.5f)
)

/**
 * Dimension specifications for the FABs menu.
 */
@Immutable
data class FabsMenuDimensions(
    val menuButtonSize: Dp = 56.dp,
    val fabItemSize: Dp = 40.dp,
    val itemSpacing: Dp = 16.dp,
    val labelSpacing: Dp = 12.dp,
    val labelCornerRadius: Dp = 4.dp,
    val labelPaddingHorizontal: Dp = 12.dp,
    val labelPaddingVertical: Dp = 8.dp
)

/**
 * Animation parameters for the FABs menu.
 */
@Immutable
data class FabsMenuAnimationConfig(
    val expandDurationMs: Int = 500,
    val collapseDurationMs: Int = 300,
    val staggerDelayMs: Int = 50,
    val menuButtonRotation: Float = 135f
)

/**
 * Complete theme configuration for the FABs menu.
 */
@Immutable
data class FabsMenuTheme(
    val colors: FabsMenuColors = FabsMenuColors(),
    val dimensions: FabsMenuDimensions = FabsMenuDimensions(),
    val animationConfig: FabsMenuAnimationConfig = FabsMenuAnimationConfig()
)

/**
 * CompositionLocal for providing FabsMenuTheme to the composable tree.
 */
val LocalFabsMenuTheme = staticCompositionLocalOf { FabsMenuTheme() }

/**
 * Provides a custom theme to the FABs menu composables.
 *
 * @param theme The theme configuration to apply.
 * @param content The composable content.
 */
@Composable
fun FabsMenuThemeProvider(
    theme: FabsMenuTheme = FabsMenuTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalFabsMenuTheme provides theme) {
        content()
    }
}

/**
 * Predefined color schemes for common use cases.
 */
object FabsMenuColorSchemes {

    /**
     * Default pink color scheme.
     */
    val Pink = FabsMenuColors(
        menuButtonBackground = Color(0xFFE91E63),
        fabItemBackground = Color(0xFFE91E63)
    )

    /**
     * Blue color scheme.
     */
    val Blue = FabsMenuColors(
        menuButtonBackground = Color(0xFF2196F3),
        fabItemBackground = Color(0xFF2196F3)
    )

    /**
     * Green color scheme.
     */
    val Green = FabsMenuColors(
        menuButtonBackground = Color(0xFF4CAF50),
        fabItemBackground = Color(0xFF4CAF50)
    )

    /**
     * Orange color scheme.
     */
    val Orange = FabsMenuColors(
        menuButtonBackground = Color(0xFFFF9800),
        fabItemBackground = Color(0xFFFF9800)
    )

    /**
     * Purple color scheme.
     */
    val Purple = FabsMenuColors(
        menuButtonBackground = Color(0xFF9C27B0),
        fabItemBackground = Color(0xFF9C27B0)
    )

    /**
     * Red color scheme.
     */
    val Red = FabsMenuColors(
        menuButtonBackground = Color(0xFFF44336),
        fabItemBackground = Color(0xFFF44336)
    )

    /**
     * Teal color scheme.
     */
    val Teal = FabsMenuColors(
        menuButtonBackground = Color(0xFF009688),
        fabItemBackground = Color(0xFF009688)
    )

    /**
     * Dark color scheme.
     */
    val Dark = FabsMenuColors(
        menuButtonBackground = Color(0xFF424242),
        fabItemBackground = Color(0xFF616161),
        labelBackground = Color(0xFF303030),
        labelText = Color.White
    )
}
