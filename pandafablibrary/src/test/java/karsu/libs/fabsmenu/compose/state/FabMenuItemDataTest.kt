package karsu.libs.fabsmenu.compose.state

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FabMenuItemDataTest {

    @Test
    fun menuButtonConfig_equality() {
        val config1 = MenuButtonConfig()
        val config2 = MenuButtonConfig()
        assertEquals(config1, config2)
    }

    @Test
    fun menuButtonConfig_defaultContentDescription() {
        val config = MenuButtonConfig()
        assertEquals("Menu", config.contentDescription)
    }

    @Test
    fun menuButtonConfig_defaultIcon_isNull() {
        val config = MenuButtonConfig()
        assertNull(config.icon)
    }

    @Test
    fun menuButtonConfig_defaultSize_isNormal() {
        val config = MenuButtonConfig()
        assertEquals(FabSize.Normal, config.size)
    }

    @Test
    fun labelConfig_defaultClickable_isTrue() {
        val config = LabelConfig()
        assertTrue(config.clickable)
    }

    @Test
    fun labelConfig_defaultShowShadow_isTrue() {
        val config = LabelConfig()
        assertTrue(config.showShadow)
    }

    @Test
    fun fabItemConfig_defaultSize_isMini() {
        val config = FabItemConfig()
        assertEquals(FabSize.Mini, config.size)
    }

    @Test
    fun fabItemConfig_defaultShowShadow_isTrue() {
        val config = FabItemConfig()
        assertTrue(config.showShadow)
    }

    @Test
    fun paddingValues_defaultHorizontal() {
        val padding = PaddingValues()
        assertEquals(8, padding.horizontal.value.toInt())
    }

    @Test
    fun paddingValues_defaultVertical() {
        val padding = PaddingValues()
        assertEquals(4, padding.vertical.value.toInt())
    }

    @Test
    fun paddingValues_equality() {
        val p1 = PaddingValues()
        val p2 = PaddingValues()
        assertEquals(p1, p2)
    }
}
