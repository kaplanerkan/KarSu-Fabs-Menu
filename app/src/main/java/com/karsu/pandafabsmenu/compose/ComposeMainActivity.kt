package com.karsu.pandafabsmenu.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.karsu.pandafabsmenu.compose.screens.MainScreen
import com.karsu.pandafabsmenu.compose.screens.ProductListScreen
import com.karsu.pandafabsmenu.compose.theme.KarSuFabsMenuTheme

/**
 * Main activity for the Jetpack Compose version of the demo app.
 */
class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KarSuFabsMenuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FabsMenuApp()
                }
            }
        }
    }
}

/**
 * Navigation destinations.
 */
sealed class Screen {
    data object Main : Screen()
    data object ProductList : Screen()
}

/**
 * Main app composable with simple navigation.
 */
@Composable
fun FabsMenuApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Main) }

    when (currentScreen) {
        Screen.Main -> {
            MainScreen(
                onNavigateToProductList = { currentScreen = Screen.ProductList },
                onNavigateToSettings = { /* Handled by bottom sheet */ }
            )
        }
        Screen.ProductList -> {
            ProductListScreen(
                onNavigateBack = { currentScreen = Screen.Main }
            )
        }
    }
}
