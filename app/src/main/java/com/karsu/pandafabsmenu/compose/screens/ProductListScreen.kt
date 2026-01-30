package com.karsu.pandafabsmenu.compose.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.filled.ChargingStation
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import karsu.libs.fabsmenu.compose.KarSuFabsMenu
import karsu.libs.fabsmenu.compose.state.ExpandDirection
import karsu.libs.fabsmenu.compose.state.FabsMenuDefaults
import karsu.libs.fabsmenu.compose.state.FabSize
import karsu.libs.fabsmenu.compose.state.LabelsPosition
import karsu.libs.fabsmenu.compose.state.rememberFabsMenuState
import java.text.NumberFormat
import java.util.Locale

/**
 * Product data class for the list.
 */
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val icon: ImageVector
)

/**
 * Product action types.
 */
enum class ProductAction(val displayName: String, val icon: ImageVector) {
    PRINT("Print", Icons.Default.Print),
    CHANGE_PRICE("Change Price", Icons.Default.AttachMoney),
    EDIT("Edit", Icons.Default.Edit),
    DELETE("Delete", Icons.Default.Delete)
}

/**
 * Data class for popup info.
 */
data class ActionPopupInfo(
    val product: Product,
    val action: ProductAction
)

/**
 * Get dummy products list (5 items).
 */
private fun getDummyProducts(): List<Product> = listOf(
    Product(
        id = 1,
        name = "Wireless Headphones",
        description = "Premium noise-cancelling headphones with 30-hour battery",
        price = 149.99,
        icon = Icons.Default.Headphones
    ),
    Product(
        id = 2,
        name = "Smart Watch Pro",
        description = "Fitness tracker with heart rate monitor and GPS",
        price = 299.99,
        icon = Icons.Default.Watch
    ),
    Product(
        id = 3,
        name = "Mechanical Keyboard",
        description = "RGB backlit gaming keyboard with cherry switches",
        price = 129.99,
        icon = Icons.Default.Keyboard
    ),
    Product(
        id = 4,
        name = "Webcam HD Pro",
        description = "1080p webcam with built-in microphone and auto-focus",
        price = 79.99,
        icon = Icons.Default.Videocam
    ),
    Product(
        id = 5,
        name = "Wireless Charger",
        description = "15W fast wireless charging for all Qi devices",
        price = 29.99,
        icon = Icons.Default.ChargingStation
    )
)

/**
 * Product list screen with FAB menu inside each item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onNavigateBack: () -> Unit = {}
) {
    val products = remember { getDummyProducts() }
    val priceFormat = remember { NumberFormat.getCurrencyInstance(Locale.US) }

    // Popup state
    var popupInfo by remember { mutableStateOf<ActionPopupInfo?>(null) }

    // Auto-dismiss popup after 5 seconds
    LaunchedEffect(popupInfo) {
        if (popupInfo != null) {
            delay(5000)
            popupInfo = null
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Products",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFE91E63),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(products, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        priceFormat = priceFormat,
                        onActionClick = { action ->
                            popupInfo = ActionPopupInfo(product, action)
                        }
                    )
                }
            }
        }

        // Action Info Popup
        ActionInfoPopup(
            popupInfo = popupInfo,
            priceFormat = priceFormat,
            onDismiss = { popupInfo = null }
        )
    }
}

@Composable
private fun ProductCard(
    product: Product,
    priceFormat: NumberFormat,
    onActionClick: (ProductAction) -> Unit
) {
    val fabsMenuState = rememberFabsMenuState()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Card content
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp), // Space for FAB
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Product Icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFE91E63).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = product.icon,
                        contentDescription = null,
                        tint = Color(0xFFE91E63),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Product Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = product.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = product.description,
                        fontSize = 11.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = priceFormat.format(product.price),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE91E63)
                    )
                }
            }
        }

        // FAB Menu - Top Right, expands DOWN
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 4.dp)
        ) {
            KarSuFabsMenu(
                state = fabsMenuState,
                expandDirection = ExpandDirection.DOWN,
                labelsPosition = LabelsPosition.LEFT,
                menuButtonConfig = FabsMenuDefaults.menuButtonConfig(
                    icon = Icons.Default.MoreVert,
                    backgroundColor = Color(0xFF26A69A),
                    size = FabSize.Mini
                ),
                itemSpacing = 6.dp
            ) {
                item(
                    id = "print",
                    icon = Icons.Default.Print,
                    label = "Print",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFF00BCD4),
                        size = FabSize.Mini
                    )
                ) {
                    fabsMenuState.collapse()
                    onActionClick(ProductAction.PRINT)
                }

                item(
                    id = "price",
                    icon = Icons.Default.AttachMoney,
                    label = "Price",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFF009688),
                        size = FabSize.Mini
                    )
                ) {
                    fabsMenuState.collapse()
                    onActionClick(ProductAction.CHANGE_PRICE)
                }

                item(
                    id = "edit",
                    icon = Icons.Default.Edit,
                    label = "Edit",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFF00897B),
                        size = FabSize.Mini
                    )
                ) {
                    fabsMenuState.collapse()
                    onActionClick(ProductAction.EDIT)
                }

                item(
                    id = "delete",
                    icon = Icons.Default.Delete,
                    label = "Delete",
                    fabConfig = FabsMenuDefaults.fabItemConfig(
                        backgroundColor = Color(0xFFF44336),
                        size = FabSize.Mini
                    )
                ) {
                    fabsMenuState.collapse()
                    onActionClick(ProductAction.DELETE)
                }
            }
        }
    }
}

/**
 * Action info popup that shows for 5 seconds.
 */
@Composable
private fun ActionInfoPopup(
    popupInfo: ActionPopupInfo?,
    priceFormat: NumberFormat,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = popupInfo != null,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        popupInfo?.let { info ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Header with action
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            when (info.action) {
                                                ProductAction.PRINT -> Color(0xFF00BCD4)
                                                ProductAction.CHANGE_PRICE -> Color(0xFF009688)
                                                ProductAction.EDIT -> Color(0xFF00897B)
                                                ProductAction.DELETE -> Color(0xFFF44336)
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = info.action.icon,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text(
                                    text = info.action.displayName,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE91E63)
                                )
                            }
                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Product info
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFE91E63).copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = info.product.icon,
                                    contentDescription = null,
                                    tint = Color(0xFFE91E63),
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = info.product.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = info.product.description,
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Text(
                                text = priceFormat.format(info.product.price),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE91E63)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Auto-dismiss info
                        Text(
                            text = "Auto-dismisses in 5 seconds",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun ProductListScreenPreview() {
    ProductListScreen()
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    val priceFormat = NumberFormat.getCurrencyInstance(Locale.US)
    ProductCard(
        product = Product(
            id = 1,
            name = "Wireless Headphones",
            description = "Premium noise-cancelling headphones with 30-hour battery",
            price = 149.99,
            icon = Icons.Default.Headphones
        ),
        priceFormat = priceFormat,
        onActionClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ActionInfoPopupPreview() {
    val priceFormat = NumberFormat.getCurrencyInstance(Locale.US)
    Box(modifier = Modifier.fillMaxSize()) {
        ActionInfoPopup(
            popupInfo = ActionPopupInfo(
                product = Product(
                    id = 1,
                    name = "Wireless Headphones",
                    description = "Premium noise-cancelling headphones with 30-hour battery",
                    price = 149.99,
                    icon = Icons.Default.Headphones
                ),
                action = ProductAction.EDIT
            ),
            priceFormat = priceFormat,
            onDismiss = {}
        )
    }
}
