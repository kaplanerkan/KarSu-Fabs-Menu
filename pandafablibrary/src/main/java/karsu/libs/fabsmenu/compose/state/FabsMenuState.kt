package karsu.libs.fabsmenu.compose.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

/**
 * State holder for KarSuFabsMenu.
 * Manages the expanded/collapsed state and animation state of the FAB menu.
 */
@Stable
class FabsMenuState(initialExpanded: Boolean = false) {

    /**
     * Whether the menu is currently expanded.
     */
    var isExpanded by mutableStateOf(initialExpanded)
        private set

    /**
     * Expands the menu.
     */
    fun expand() {
        if (!isExpanded) {
            isExpanded = true
        }
    }

    /**
     * Collapses the menu.
     */
    fun collapse() {
        if (isExpanded) {
            isExpanded = false
        }
    }

    /**
     * Toggles between expanded and collapsed states.
     */
    fun toggle() {
        isExpanded = !isExpanded
    }

    companion object {
        /**
         * Saver for preserving state across configuration changes.
         */
        val Saver: Saver<FabsMenuState, Boolean> = Saver(
            save = { it.isExpanded },
            restore = { FabsMenuState(it) }
        )
    }
}

/**
 * Creates and remembers a [FabsMenuState].
 * The state is preserved across configuration changes.
 *
 * @param initialExpanded Initial expanded state of the menu.
 */
@Composable
fun rememberFabsMenuState(initialExpanded: Boolean = false): FabsMenuState {
    return rememberSaveable(saver = FabsMenuState.Saver) {
        FabsMenuState(initialExpanded)
    }
}
