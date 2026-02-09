package karsu.libs.fabsmenu.compose.state

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FabsMenuStateTest {

    @Test
    fun defaultState_isCollapsed() {
        val state = FabsMenuState()
        assertFalse(state.isExpanded)
    }

    @Test
    fun initialExpandedTrue_isExpanded() {
        val state = FabsMenuState(initialExpanded = true)
        assertTrue(state.isExpanded)
    }

    @Test
    fun initialExpandedFalse_isCollapsed() {
        val state = FabsMenuState(initialExpanded = false)
        assertFalse(state.isExpanded)
    }

    @Test
    fun expand_setsIsExpandedTrue() {
        val state = FabsMenuState()
        state.expand()
        assertTrue(state.isExpanded)
    }

    @Test
    fun collapse_setsIsExpandedFalse() {
        val state = FabsMenuState(initialExpanded = true)
        state.collapse()
        assertFalse(state.isExpanded)
    }

    @Test
    fun toggle_fromCollapsed_becomesExpanded() {
        val state = FabsMenuState()
        state.toggle()
        assertTrue(state.isExpanded)
    }

    @Test
    fun toggle_fromExpanded_becomesCollapsed() {
        val state = FabsMenuState(initialExpanded = true)
        state.toggle()
        assertFalse(state.isExpanded)
    }

    @Test
    fun doubleToggle_returnsToOriginalState() {
        val state = FabsMenuState()
        state.toggle()
        state.toggle()
        assertFalse(state.isExpanded)
    }

    @Test
    fun expand_isIdempotent() {
        val state = FabsMenuState()
        state.expand()
        state.expand()
        assertTrue(state.isExpanded)
    }

    @Test
    fun collapse_isIdempotent() {
        val state = FabsMenuState()
        state.collapse()
        state.collapse()
        assertFalse(state.isExpanded)
    }

    @Test
    fun saver_restoresExpandedState() {
        val restored = FabsMenuState.Saver.restore(true)!!
        assertTrue(restored.isExpanded)
    }

    @Test
    fun saver_restoresCollapsedState() {
        val restored = FabsMenuState.Saver.restore(false)!!
        assertFalse(restored.isExpanded)
    }

    @Test
    fun expandThenCollapse_finalStateIsCollapsed() {
        val state = FabsMenuState()
        state.expand()
        assertTrue(state.isExpanded)
        state.collapse()
        assertFalse(state.isExpanded)
    }
}
