package karsu.libs.fabsmenu

abstract class KarSuFabsMenuListener {

    open fun onMenuClicked(fabsMenu: KarSuFabsMenu) {
        fabsMenu.toggle()
    }

    open fun onMenuExpanded(fabsMenu: KarSuFabsMenu) {}

    open fun onMenuCollapsed(fabsMenu: KarSuFabsMenu) {}
}
