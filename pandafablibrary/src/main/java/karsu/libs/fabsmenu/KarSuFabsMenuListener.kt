package karsu.libs.fabsmenu

/**
 * Abstract listener class for FAB menu events.
 *
 * This class is used to listen for open, close, and click events
 * of the [KarSuFabsMenu] component. Applications can extend this class
 * to react to menu state changes.
 *
 * Usage example:
 * ```kotlin
 * fabsMenu.menuListener = object : KarSuFabsMenuListener() {
 *     override fun onMenuExpanded(fabsMenu: KarSuFabsMenu) {
 *         // Actions when menu opens
 *         Log.d("FABsMenu", "Menu expanded")
 *     }
 *
 *     override fun onMenuCollapsed(fabsMenu: KarSuFabsMenu) {
 *         // Actions when menu closes
 *         Log.d("FABsMenu", "Menu collapsed")
 *     }
 * }
 * ```
 *
 * Memory Leak Prevention:
 * Set the listener to null when Activity/Fragment is destroyed:
 * ```kotlin
 * override fun onDestroy() {
 *     super.onDestroy()
 *     fabsMenu.menuListener = null
 * }
 * ```
 *
 * @see KarSuFabsMenu
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 * @date 2026-01-30
 */
abstract class KarSuFabsMenuListener {

    open fun onMenuClicked(fabsMenu: KarSuFabsMenu) {
        fabsMenu.toggle()
    }

    open fun onMenuExpanded(fabsMenu: KarSuFabsMenu) {}

    open fun onMenuCollapsed(fabsMenu: KarSuFabsMenu) {}
}
