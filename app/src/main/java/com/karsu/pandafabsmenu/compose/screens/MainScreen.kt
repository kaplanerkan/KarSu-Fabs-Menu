package com.karsu.pandafabsmenu.compose.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import karsu.libs.fabsmenu.compose.KarSuFabsMenuLayout
import karsu.libs.fabsmenu.compose.state.ExpandDirection
import karsu.libs.fabsmenu.compose.state.FabsMenuDefaults
import karsu.libs.fabsmenu.compose.state.LabelsPosition
import karsu.libs.fabsmenu.compose.state.rememberFabsMenuState
import kotlinx.coroutines.launch

/**
 * Main demo screen showcasing the KarSuFabsMenu composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToProductList: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {
    val context = LocalContext.current
    val fabsMenuState = rememberFabsMenuState()
    val scope = rememberCoroutineScope()

    // Settings state
    var expandDirection by remember { mutableStateOf(ExpandDirection.UP) }
    var labelsPosition by remember { mutableStateOf(LabelsPosition.LEFT) }
    var animationDuration by remember { mutableStateOf(500) }

    // Bottom sheet state
    var showSettingsSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "KarSu FABs Menu",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE91E63),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        KarSuFabsMenuLayout(
            modifier = Modifier.padding(paddingValues),
            state = fabsMenuState,
            expandDirection = expandDirection,
            labelsPosition = labelsPosition,
            menuButtonConfig = FabsMenuDefaults.menuButtonConfig(
                backgroundColor = Color(0xFFE91E63)
            ),
            animationDurationMs = animationDuration,
            overlayColor = Color.Black.copy(alpha = 0.5f),
            onMenuExpanded = {
                // Menu expanded callback
            },
            onMenuCollapsed = {
                // Menu collapsed callback
            },
            content = {
                // Main content
                MainContent(
                    expandDirection = expandDirection,
                    labelsPosition = labelsPosition,
                    animationDuration = animationDuration,
                    onOpenSettings = { showSettingsSheet = true },
                    onNavigateToProductList = onNavigateToProductList
                )
            },
            fabContent = {
                item(
                    id = "download",
                    icon = Icons.Default.Download,
                    label = "Download",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFF2196F3)
                    )
                ) {
                    fabsMenuState.collapse()
                    Toast.makeText(context, "Download clicked!", Toast.LENGTH_SHORT).show()
                }

                item(
                    id = "camera",
                    icon = Icons.Default.CameraAlt,
                    label = "Camera",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFF4CAF50)
                    )
                ) {
                    fabsMenuState.collapse()
                    Toast.makeText(context, "Camera clicked!", Toast.LENGTH_SHORT).show()
                }

                item(
                    id = "share",
                    icon = Icons.Default.Share,
                    label = "Share",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFFFF9800)
                    )
                ) {
                    fabsMenuState.collapse()
                    Toast.makeText(context, "Share clicked!", Toast.LENGTH_SHORT).show()
                }

                item(
                    id = "edit",
                    icon = Icons.Default.Edit,
                    label = "Edit",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFF9C27B0)
                    )
                ) {
                    fabsMenuState.collapse()
                    Toast.makeText(context, "Edit clicked!", Toast.LENGTH_SHORT).show()
                }

                item(
                    id = "settings",
                    icon = Icons.Default.Settings,
                    label = "Settings",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFF607D8B)
                    )
                ) {
                    fabsMenuState.collapse()
                    showSettingsSheet = true
                }
            }
        )

        // Settings Bottom Sheet
        if (showSettingsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSettingsSheet = false },
                sheetState = sheetState
            ) {
                SettingsSheetContent(
                    expandDirection = expandDirection,
                    labelsPosition = labelsPosition,
                    animationDuration = animationDuration,
                    onExpandDirectionChange = { expandDirection = it },
                    onLabelsPositionChange = { labelsPosition = it },
                    onAnimationDurationChange = { animationDuration = it },
                    onDismiss = {
                        scope.launch {
                            sheetState.hide()
                            showSettingsSheet = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun MainContent(
    expandDirection: ExpandDirection,
    labelsPosition: LabelsPosition,
    animationDuration: Int,
    onOpenSettings: () -> Unit,
    onNavigateToProductList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "KarSu FABs Menu - Compose",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "A customizable Floating Action Button menu for Jetpack Compose.",
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        // Current Settings Card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Current Settings",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(12.dp))

                SettingRow(label = "Expand Direction", value = expandDirection.name)
                SettingRow(label = "Labels Position", value = labelsPosition.name)
                SettingRow(label = "Animation Duration", value = "${animationDuration}ms")
            }
        }

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onOpenSettings,
                modifier = Modifier.weight(1f)
            ) {
                Text("Settings")
            }

            Button(
                onClick = onNavigateToProductList,
                modifier = Modifier.weight(1f)
            ) {
                Text("Product List")
            }
        }

        // Features Card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Features",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(12.dp))

                FeatureItem("Expand in 4 directions (UP, DOWN, LEFT, RIGHT)")
                FeatureItem("Customizable labels with left/right positioning")
                FeatureItem("Smooth overshoot animations")
                FeatureItem("Staggered item animations")
                FeatureItem("Overlay backdrop with click-to-close")
                FeatureItem("DSL-based item configuration")
                FeatureItem("Theme support with color schemes")
                FeatureItem("State preservation across config changes")
            }
        }

        // Spacer for FAB
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun SettingRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FeatureItem(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .width(6.dp)
                .height(6.dp)
                .background(
                    color = Color(0xFFE91E63),
                    shape = MaterialTheme.shapes.small
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}

@Preview(showBackground = true)
@Composable
private fun MainContentPreview() {
    MainContent(
        expandDirection = ExpandDirection.UP,
        labelsPosition = LabelsPosition.LEFT,
        animationDuration = 500,
        onOpenSettings = {},
        onNavigateToProductList = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingRowPreview() {
    SettingRow(label = "Expand Direction", value = "UP")
}

@Preview(showBackground = true)
@Composable
private fun FeatureItemPreview() {
    FeatureItem("Expand in 4 directions (UP, DOWN, LEFT, RIGHT)")
}
