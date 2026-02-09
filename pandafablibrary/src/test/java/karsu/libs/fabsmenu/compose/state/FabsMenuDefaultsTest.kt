package karsu.libs.fabsmenu.compose.state

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FabsMenuDefaultsTest {

    @Test
    fun animationDurationMs_isThreeHundred() {
        assertEquals(300, FabsMenuDefaults.AnimationDurationMs)
    }

    @Test
    fun staggerDelayMs_isThirty() {
        assertEquals(30, FabsMenuDefaults.StaggerDelayMs)
    }

    @Test
    fun menuButtonRotation_is135() {
        assertEquals(135f, FabsMenuDefaults.MenuButtonRotation, 0.001f)
    }

    @Test
    fun defaultExpandDirection_isUp() {
        assertEquals(ExpandDirection.UP, FabsMenuDefaults.DefaultExpandDirection)
    }

    @Test
    fun defaultLabelsPosition_isLeft() {
        assertEquals(LabelsPosition.LEFT, FabsMenuDefaults.DefaultLabelsPosition)
    }

    @Test
    fun menuButtonConfig_defaultSize_isNormal() {
        val config = FabsMenuDefaults.menuButtonConfig()
        assertEquals(FabSize.Normal, config.size)
    }

    @Test
    fun menuButtonConfig_defaultRotation_is135() {
        val config = FabsMenuDefaults.menuButtonConfig()
        assertEquals(135f, config.rotationAngle, 0.001f)
    }

    @Test
    fun fabItemConfig_defaultSize_isMini() {
        val config = FabsMenuDefaults.fabItemConfig()
        assertEquals(FabSize.Mini, config.size)
    }

    @Test
    fun labelConfig_defaultClickable_isTrue() {
        val config = FabsMenuDefaults.labelConfig()
        assertTrue(config.clickable)
    }

    @Test
    fun fabSize_miniIs40dp() {
        assertEquals(40, FabSize.Mini.sizeDp.value.toInt())
    }

    @Test
    fun fabSize_normalIs56dp() {
        assertEquals(56, FabSize.Normal.sizeDp.value.toInt())
    }

    @Test
    fun fabSize_largeIs72dp() {
        assertEquals(72, FabSize.Large.sizeDp.value.toInt())
    }

    @Test
    fun fabSize_hasThreeValues() {
        assertEquals(3, FabSize.entries.size)
    }
}
