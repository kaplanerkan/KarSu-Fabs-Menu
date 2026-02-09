# KarSu FABs Menu

> **Who is KarSu?** 
> KarSu is my little angel. :-)
>
> ![KarSu](screenshots/karsu.png)

A beautiful, customizable Floating Action Button (FAB) menu library for Android with Material Design 3 support. Create expandable FAB menus with smooth animations, title labels, and overlay effects.

**Now with Jetpack Compose support!** 🎉

## Acknowledgments

This project is inspired by and rewritten based on [FABsMenu by Jahir Fiquitiva](https://github.com/jahirfiquitiva/FABsMenu). The original library provided the foundational concepts and design patterns that made this implementation possible. Thank you to Jahir Fiquitiva for the excellent work!

[![](https://jitpack.io/v/kaplanerkan/KarSu-Fabs-Menu.svg)](https://jitpack.io/#kaplanerkan/KarSu-Fabs-Menu)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kaplanerkan/karsu-fabs-menu.svg)](https://central.sonatype.com/artifact/io.github.kaplanerkan/karsu-fabs-menu)
![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-1.7+-green.svg)

## Features

- **Expandable Menu** - Smooth animations with customizable expand directions (Up, Down, Left, Right)
- **Title Labels** - Each FAB can have a customizable title label with background color, text color, and corner radius
- **Overlay Support** - Optional overlay that dims the background when the menu is open
- **Live Settings** - Customize all menu properties programmatically in real-time
- **Material Design 3** - Modern design with Material You theming support
- **Mini & Normal FAB Sizes** - Support for both FAB sizes
- **Click Events** - Handle clicks on both FABs and their title labels

## Screenshots

### Main Screen
| Collapsed | Expanded | Settings |
|-----------|----------|----------|
| ![Collapsed](screenshots/scrcpy_K2Z2f51hze.png) | ![Expanded](screenshots/scrcpy_Rovnzph2K1.png) | ![Settings](screenshots/scrcpy_AWr3vCUqLx.png) |

### Product List with FAB Menu
| Products List | Menu Expanded | Action Popup |
|---------------|---------------|--------------|
| ![Products](screenshots/scrcpy_zjUh6IlybJ.png) | ![Menu](screenshots/scrcpy_3YGnDE1t7G.png) | ![Popup](screenshots/scrcpy_qvQhDb3mve.png) |

### Demo Video

https://github.com/user-attachments/assets/3ce0be38-edaa-4915-9990-c1ee526052bc

## Installation

### Option 1: JitPack (Recommended)

[![](https://jitpack.io/v/kaplanerkan/KarSu-Fabs-Menu.svg)](https://jitpack.io/#kaplanerkan/KarSu-Fabs-Menu)

**Step 1.** Add JitPack repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Or for Groovy (`settings.gradle`):

```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2.** Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.kaplanerkan:KarSu-Fabs-Menu:1.2.0")
}
```

Or for Groovy (`build.gradle`):

```groovy
dependencies {
    implementation 'com.github.kaplanerkan:KarSu-Fabs-Menu:1.2.0'
}
```

### Option 2: Maven Central

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kaplanerkan/karsu-fabs-menu.svg)](https://central.sonatype.com/artifact/io.github.kaplanerkan/karsu-fabs-menu)

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.kaplanerkan:karsu-fabs-menu:1.2.0")
}
```

Or for Groovy (`build.gradle`):

```groovy
dependencies {
    implementation 'io.github.kaplanerkan:karsu-fabs-menu:1.2.0'
}
```

## Usage

### Basic Setup

#### 1. Add FABsMenuLayout to your layout

```xml
<karsu.libs.fabsmenu.KarSuFabsMenuLayout
    android:id="@+id/fabsMenuLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:fabs_menu_overlayColor="#99000000"
    app:fabs_menu_clickableOverlay="true">

    <karsu.libs.fabsmenu.KarSuFabsMenu
        android:id="@+id/fabsMenu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        app:fab_menuMargins="16dp"
        app:fab_moreButtonPlusIcon="@drawable/ic_plus"
        app:fab_moreButtonBackgroundColor="@color/your_color"
        app:fab_moreButtonRippleColor="@color/your_ripple_color"
        app:fab_moreButtonSize="normal"
        app:fab_expandDirection="up"
        app:fab_labelsPosition="left">

        <karsu.libs.fabsmenu.KarsuTitleFab
            android:id="@+id/fabAction1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_action"
            app:backgroundTint="@color/fab_color"
            app:fab_title="Action 1"
            app:fab_title_backgroundColor="@color/white"
            app:fab_title_textColor="@color/black"
            app:fab_title_cornerRadius="8dp"
            app:fab_enableTitleClick="true"
            app:tint="@color/icon_color" />

        <!-- Add more TitleFABs as needed -->

    </karsu.libs.fabsmenu.KarSuFabsMenu>

</karsu.libs.fabsmenu.KarSuFabsMenuLayout>
```

#### 2. Handle click events in your Activity/Fragment

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFabsMenu()
    }

    private fun setupFabsMenu() {
        // Set menu listener
        binding.fabsMenu.menuListener = object : FABsMenuListener() {
            override fun onMenuExpanded(fabsMenu: FABsMenu) {
                // Menu expanded
            }

            override fun onMenuCollapsed(fabsMenu: FABsMenu) {
                // Menu collapsed
            }
        }

        // Handle FAB clicks
        binding.fabAction1.setOnClickListener {
            Toast.makeText(this, "Action 1 clicked", Toast.LENGTH_SHORT).show()
            binding.fabsMenu.collapse()
        }
    }

    // Handle back press to collapse menu
    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.fabsMenu.isExpanded) {
                    binding.fabsMenu.collapse()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}
```

## XML Attributes

### FABsMenuLayout

| Attribute | Description | Default |
|-----------|-------------|---------|
| `app:fabs_menu_overlayColor` | Overlay background color when menu is expanded | `#4D000000` |
| `app:fabs_menu_clickableOverlay` | Whether clicking overlay collapses the menu | `true` |

### FABsMenu

| Attribute | Description | Default |
|-----------|-------------|---------|
| `app:fab_moreButtonPlusIcon` | Icon drawable for the menu button | - |
| `app:fab_moreButtonBackgroundColor` | Background color of the menu button | - |
| `app:fab_moreButtonRippleColor` | Ripple color of the menu button | - |
| `app:fab_moreButtonSize` | Size of menu button (`normal`, `mini`, or `auto`) | `normal` |
| `app:fab_moreButtonCustomSize` | Custom size for the menu button in dp | - |
| `app:fab_moreButtonIconSize` | Custom icon size inside the menu button | - |
| `app:fab_moreButtonIconTint` | Tint color for the menu button icon | - |
| `app:fab_menuMargins` | Margins around the menu | `16dp` |
| `app:fab_expandDirection` | Direction to expand (`up`, `down`, `left`, `right`) | `up` |
| `app:fab_labelsPosition` | Position of labels (`left` or `right`) | `left` |
| `app:fab_animationDuration` | Animation duration in milliseconds | `500` |

### TitleFAB

| Attribute | Description | Default |
|-----------|-------------|---------|
| `app:fab_title` | Title text for the label | - |
| `app:fab_title_backgroundColor` | Background color of the label | `#FFFFFF` |
| `app:fab_title_textColor` | Text color of the label | `#000000` |
| `app:fab_title_cornerRadius` | Corner radius of the label | `4dp` |
| `app:fab_title_textPadding` | Padding inside the label | `8dp` |
| `app:fab_enableTitleClick` | Enable click on title label | `true` |

## Programmatic Customization

You can customize the menu programmatically at runtime:

```kotlin
// Change expand direction
fabsMenu.updateExpandDirection(ExpandDirection.UP)
fabsMenu.updateExpandDirection(ExpandDirection.DOWN)
fabsMenu.updateExpandDirection(ExpandDirection.LEFT)
fabsMenu.updateExpandDirection(ExpandDirection.RIGHT)

// Change labels position
fabsMenu.updateLabelsPosition(LabelsPosition.LEFT)
fabsMenu.updateLabelsPosition(LabelsPosition.RIGHT)

// Change menu button size
fabsMenu.setMenuButtonSize(FloatingActionButton.SIZE_NORMAL)
fabsMenu.setMenuButtonSize(FloatingActionButton.SIZE_MINI)

// Change margins
fabsMenu.setMenuMargins(dpToPx(16))

// Change animation duration
fabsMenu.setAnimationDuration(500)

// Update all labels at once
fabsMenu.updateAllLabelsBackgroundColor(Color.WHITE)
fabsMenu.updateAllLabelsTextColor(Color.BLACK)
fabsMenu.updateAllLabelsCornerRadius(8f)
fabsMenu.updateAllLabelsTextPadding(12)
fabsMenu.updateAllLabelsTitleClickEnabled(true)

// Overlay customization
fabsMenuLayout.setOverlayColor(Color.argb(128, 0, 0, 0))
fabsMenuLayout.setClickableOverlay(true)

// Control menu state
fabsMenu.expand()
fabsMenu.collapse()
fabsMenu.toggle()
val isExpanded = fabsMenu.isExpanded
```

## Using in RecyclerView

You can also use FABsMenu inside RecyclerView items for per-item action menus:

```xml
<!-- item_layout.xml -->
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:clipChildren="false"
    android:clipToPadding="false">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:clipChildren="false"
        android:clipToPadding="false">

        <!-- Your content -->

        <karsu.libs.fabsmenu.KarSuFabsMenu
            android:id="@+id/fabsMenu"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:fab_moreButtonSize="mini"
            app:fab_expandDirection="left"
            app:fab_labelsPosition="left"
            app:fab_menuMargins="0dp">

            <karsu.libs.fabsmenu.KarsuTitleFab
                android:id="@+id/fabEdit"
                app:fabSize="mini"
                app:fab_title="Edit"
                ... />

        </karsu.libs.fabsmenu.KarSuFabsMenu>

    </androidx.constraintlayout.widget.ConstraintLayout>

</com.google.android.material.card.MaterialCardView>
```

**Important:** Set `clipChildren="false"` and `clipToPadding="false"` on parent views to allow the FABs to expand outside their bounds.

### Using as Badge-Style Menu Button

You can create a compact badge-style FAB menu for use in list items:

```xml
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:clipChildren="false"
    android:clipToPadding="false">

    <com.google.android.material.card.MaterialCardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:layout_marginHorizontal="12dp"
        app:cardCornerRadius="12dp"
        app:cardElevation="2dp">

        <!-- Card content here -->

    </com.google.android.material.card.MaterialCardView>

    <!-- FABsMenu positioned as badge -->
    <karsu.libs.fabsmenu.KarSuFabsMenu
        android:id="@+id/fabsMenu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="top|end"
        android:layout_marginTop="4dp"
        android:translationZ="10dp"
        app:fab_moreButtonSize="auto"
        app:fab_moreButtonCustomSize="32dp"
        app:fab_moreButtonIconSize="14dp"
        app:fab_moreButtonIconTint="@color/white"
        app:fab_moreButtonPlusIcon="@drawable/ic_more_vert"
        app:fab_expandDirection="down"
        app:fab_labelsPosition="left"
        app:fab_menuMargins="0dp">

        <!-- TitleFABs here -->

    </karsu.libs.fabsmenu.KarSuFabsMenu>

</FrameLayout>
```

**Key points for badge-style usage:**
- Use `FrameLayout` as root to position FAB outside CardView
- Set `translationZ` on FABsMenu to ensure it appears above the CardView
- Use `fab_moreButtonCustomSize` for precise size control
- Use `fab_moreButtonIconSize` to control the icon size inside the FAB

## Best Practices

### Memory Management

When using FABsMenu in Activities, always clear the listener in `onDestroy()` to prevent memory leaks:

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setup code...

        binding.fabsMenu.menuListener = object : FABsMenuListener() {
            override fun onMenuExpanded(fabsMenu: FABsMenu) { }
            override fun onMenuCollapsed(fabsMenu: FABsMenu) { }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clear listener to prevent memory leak
        binding.fabsMenu.menuListener = null
    }
}
```

### RecyclerView Adapter Best Practices

When using FABsMenu in RecyclerView items, follow these practices to avoid memory leaks and ensure proper view recycling:

```kotlin
class ProductAdapter(
    private val onActionClick: (Product, ProductAction) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    private var expandedPosition: Int = -1

    override fun onViewRecycled(holder: ProductViewHolder) {
        super.onViewRecycled(holder)
        holder.cleanup()  // Clean up when view is recycled
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentProduct: Product? = null

        init {
            // Set up listeners ONCE in init, not in bind()
            setupListeners()
        }

        private fun setupListeners() {
            binding.fabsMenu.menuListener = object : FABsMenuListener() {
                override fun onMenuExpanded(fabsMenu: FABsMenu) {
                    val position = bindingAdapterPosition
                    if (position == RecyclerView.NO_POSITION) return
                    // Handle expansion...
                }

                override fun onMenuCollapsed(fabsMenu: FABsMenu) {
                    val position = bindingAdapterPosition
                    if (position == RecyclerView.NO_POSITION) return
                    // Handle collapse...
                }
            }

            binding.fabAction.setOnClickListener {
                // Use currentProduct instead of capturing in closure
                currentProduct?.let { product ->
                    onActionClick(product, ProductAction.EDIT)
                    binding.fabsMenu.collapse()
                }
            }
        }

        fun bind(product: Product) {
            currentProduct = product  // Update current product reference
            // Bind data...

            // Collapse menu if this item shouldn't be expanded
            if (bindingAdapterPosition != RecyclerView.NO_POSITION &&
                expandedPosition != bindingAdapterPosition &&
                binding.fabsMenu.isExpanded) {
                binding.fabsMenu.collapseImmediately()
            }
        }

        fun cleanup() {
            currentProduct = null
            if (binding.fabsMenu.isExpanded) {
                binding.fabsMenu.collapseImmediately()
            }
        }
    }
}
```

**Key points:**
- Set up listeners in `init` block, not in `bind()` method
- Use `currentProduct` variable instead of capturing product in lambda closures
- Always check `bindingAdapterPosition != RecyclerView.NO_POSITION`
- Implement `onViewRecycled()` to clean up resources
- Use `collapseImmediately()` for instant collapse without animation during recycling

---

## Jetpack Compose

The library now includes full Jetpack Compose support with a modern DSL-based API.

### Compose Features

- **DSL-based API** - Intuitive Kotlin DSL for defining menu items
- **State Management** - Built-in state holder with configuration change survival
- **Smooth Animations** - Overshoot animations with staggered item reveals
- **Overlay Support** - Optional backdrop overlay when menu is expanded
- **Flexible Positioning** - Expand in 4 directions (UP, DOWN, LEFT, RIGHT)
- **Customizable Labels** - Position labels on LEFT or RIGHT of FAB items
- **Theme Support** - CompositionLocal-based theming

### Basic Compose Usage

#### 1. Add the dependency

```kotlin
dependencies {
    implementation("io.github.kaplanerkan:karsu-fabs-menu:1.2.0")
}
```

#### 2. Simple FAB Menu

```kotlin
@Composable
fun MyScreen() {
    val fabsMenuState = rememberFabsMenuState()

    KarSuFabsMenu(
        state = fabsMenuState,
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
        ) {
            fabsMenuState.collapse()
            // Handle click
        }

        item(
            id = "share",
            icon = Icons.Default.Share,
            label = "Share",
            fabConfig = FabsMenuDefaults.fabItemConfig(
                backgroundColor = Color(0xFF4CAF50)
            )
        ) {
            fabsMenuState.collapse()
            // Handle click
        }
    }
}
```

#### 3. Full-Screen Layout with Overlay

Use `KarSuFabsMenuLayout` for a complete screen setup with overlay support:

```kotlin
@Composable
fun MainScreen() {
    val fabsMenuState = rememberFabsMenuState()

    Scaffold(
        topBar = { /* Your TopAppBar */ }
    ) { paddingValues ->
        KarSuFabsMenuLayout(
            modifier = Modifier.padding(paddingValues),
            state = fabsMenuState,
            expandDirection = ExpandDirection.UP,
            labelsPosition = LabelsPosition.LEFT,
            menuButtonConfig = FabsMenuDefaults.menuButtonConfig(
                backgroundColor = Color(0xFFE91E63)
            ),
            overlayColor = Color.Black.copy(alpha = 0.5f),
            closeOnOverlayClick = true,
            content = {
                // Your main screen content here
                LazyColumn {
                    items(items) { item ->
                        ItemCard(item)
                    }
                }
            },
            fabContent = {
                item(
                    id = "camera",
                    icon = Icons.Default.CameraAlt,
                    label = "Camera"
                ) {
                    fabsMenuState.collapse()
                }

                item(
                    id = "edit",
                    icon = Icons.Default.Edit,
                    label = "Edit"
                ) {
                    fabsMenuState.collapse()
                }
            }
        )
    }
}
```

#### 4. FAB Menu Inside List Items

Perfect for per-item action menus in LazyColumn:

```kotlin
@Composable
fun ProductCard(
    product: Product,
    onActionClick: (ProductAction) -> Unit
) {
    val fabsMenuState = rememberFabsMenuState()

    Box(modifier = Modifier.fillMaxWidth()) {
        // Card content
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
        ) {
            // Product info
        }

        // FAB Menu - positioned at top right, expands down
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
```

### Compose API Reference

#### FabsMenuState

```kotlin
// Create and remember state
val state = rememberFabsMenuState(initialExpanded = false)

// Control the menu
state.expand()      // Expand the menu
state.collapse()    // Collapse the menu
state.toggle()      // Toggle expand/collapse
state.isExpanded    // Check if menu is expanded
```

#### KarSuFabsMenu Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `state` | `FabsMenuState` | `rememberFabsMenuState()` | State holder for the menu |
| `expandDirection` | `ExpandDirection` | `UP` | Direction to expand (UP, DOWN, LEFT, RIGHT) |
| `labelsPosition` | `LabelsPosition` | `LEFT` | Position of labels (LEFT, RIGHT) |
| `menuButtonConfig` | `MenuButtonConfig` | Default pink FAB | Configuration for menu button |
| `animationDurationMs` | `Int` | `300` | Animation duration in milliseconds |
| `itemSpacing` | `Dp` | `16.dp` | Spacing between FAB items |
| `onMenuExpanded` | `() -> Unit` | `{}` | Callback when menu is fully expanded |
| `onMenuCollapsed` | `() -> Unit` | `{}` | Callback when menu is fully collapsed |

#### MenuButtonConfig

```kotlin
FabsMenuDefaults.menuButtonConfig(
    icon = Icons.Default.Add,           // Menu button icon
    backgroundColor = Color(0xFFE91E63), // Background color
    iconTint = Color.White,              // Icon tint color
    size = FabSize.Normal,               // FabSize.Normal or FabSize.Mini
    rotationAngle = 135f                 // Rotation angle when expanded
)
```

#### FabItemConfig

```kotlin
FabsMenuDefaults.fabItemConfig(
    backgroundColor = Color(0xFF2196F3), // FAB background color
    iconTint = Color.White,              // Icon tint color
    size = FabSize.Mini                  // FabSize.Normal or FabSize.Mini
)
```

#### LabelConfig

```kotlin
FabsMenuDefaults.labelConfig(
    backgroundColor = Color.White,  // Label background
    textColor = Color.Black,        // Label text color
    cornerRadius = 4.dp,            // Corner radius
    clickable = true                // Enable label click
)
```

#### DSL Item Function

```kotlin
item(
    id = "unique_id",                    // Unique identifier
    icon = Icons.Default.Download,       // Item icon
    label = "Download",                  // Optional label text
    contentDescription = "Download",     // Accessibility description
    fabConfig = FabsMenuDefaults.fabItemConfig(),
    labelConfig = FabsMenuDefaults.labelConfig(),
    enabled = true,                      // Whether item is enabled
    onClick = { }                        // Click handler
)
```

### Compose Theming

You can customize the default theme using `FabsMenuTheme`:

```kotlin
FabsMenuTheme(
    colors = FabsMenuColors(
        menuButtonBackground = Color(0xFFE91E63),
        menuButtonIcon = Color.White,
        fabItemBackground = Color(0xFF2196F3),
        fabItemIcon = Color.White,
        labelBackground = Color.White,
        labelText = Color.Black,
        overlay = Color.Black.copy(alpha = 0.5f)
    )
) {
    // Your content with themed FAB menus
    KarSuFabsMenu { /* ... */ }
}
```

---

## Sample App

The repository includes a sample app demonstrating both View-based and Compose implementations:

### View-Based Demo
- Basic FABsMenu usage with 5 action buttons
- Live settings panel to customize all properties in real-time
- Product list with per-item FABsMenu integration
- Action popup feedback when items are clicked

### Jetpack Compose Demo
- Main screen with FABsMenu and overlay backdrop
- Settings bottom sheet for live customization
- Product list with per-item FAB menus (expand down)
- Action info popup showing product details for 5 seconds
- Preview composables for all screens and components

## Requirements

- **Minimum SDK:** 21 (Android 5.0 Lollipop)
- **Target SDK:** 35
- **Kotlin:** 1.9+
- **Material Components:** 1.12.0+

## Dependencies

```groovy
dependencies {
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
}
```

## Changelog

### Version 1.2.0
- **Jetpack Compose Support:**
  - Added `KarSuFabsMenu` composable with DSL-based API
  - Added `KarSuFabsMenuLayout` for full-screen layouts with overlay
  - Added `KarSuFabsMenuScaffold` for simplified positioning
  - Added `FabMenuItem` and `FabMenuLabel` composables
  - Added `FabsMenuState` with `rememberFabsMenuState()` for state management
  - Added `FabsMenuDefaults` for default configurations
  - Added `FabsMenuTheme` with `CompositionLocal` support
  - Smooth overshoot animations with staggered item reveals
  - Support for all 4 expand directions (UP, DOWN, LEFT, RIGHT)
  - Label positioning (LEFT, RIGHT)
  - Mini and Normal FAB sizes
  - Preview composables for all components

- **Sample App:**
  - Added launcher activity to choose between View and Compose demos
  - Added Compose main screen with settings bottom sheet
  - Added Compose product list with per-item FAB menus
  - Added action info popup with 5-second auto-dismiss

### Version 1.1.0
- **New Features:**
  - Added `fab_moreButtonCustomSize` attribute for precise FAB size control
  - Added `fab_moreButtonIconSize` attribute to customize icon size inside the FAB
  - Added `fab_moreButtonIconTint` attribute to tint the menu button icon
  - Added `collapseImmediately()` method for instant collapse without animation
  - Responsive FABsMenu sizing - only measures collapsed size when collapsed

- **Bug Fixes & Improvements:**
  - Fixed memory leak in `setMenuButtonIcon(Uri)` - InputStream now properly closed
  - Fixed animation listener accumulation in FABsMenuLayout
  - Improved RecyclerView support with proper view recycling
  - Added proper cleanup methods to prevent context leaks

- **Performance:**
  - Optimized `onMeasure()` to only calculate expanded size when needed
  - Reduced memory allocations in animation callbacks

## License

```
Copyright 2025 Erkan Kaplan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Author

**Erkan Kaplan**

---

If you find this library useful, please consider giving it a star on GitHub!
