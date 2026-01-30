package com.karsu.pandafabsmenu.compose.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val Pink = Color(0xFFE91E63)
private val PinkLight = Color(0xFFFF6090)
private val PinkDark = Color(0xFFB0003A)

private val LightColorScheme = lightColorScheme(
    primary = Pink,
    primaryContainer = PinkLight,
    onPrimary = Color.White,
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF03DAC6),
    secondaryContainer = Color(0xFF018786),
    tertiary = Color(0xFF6200EE),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

private val DarkColorScheme = darkColorScheme(
    primary = PinkLight,
    primaryContainer = PinkDark,
    onPrimary = Color.Black,
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF03DAC6),
    secondaryContainer = Color(0xFF018786),
    tertiary = Color(0xFFBB86FC),
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5),
)

@Composable
fun KarSuFabsMenuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
