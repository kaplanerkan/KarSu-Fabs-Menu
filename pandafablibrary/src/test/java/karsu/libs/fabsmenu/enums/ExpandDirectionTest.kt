package karsu.libs.fabsmenu.enums

import org.junit.Assert.assertEquals
import org.junit.Test

class ExpandDirectionTest {

    @Test
    fun expandDirection_hasFourValues() {
        assertEquals(4, ExpandDirection.entries.size)
    }

    @Test
    fun expandDirection_valuesInCorrectOrder() {
        assertEquals(0, ExpandDirection.UP.ordinal)
        assertEquals(1, ExpandDirection.DOWN.ordinal)
        assertEquals(2, ExpandDirection.LEFT.ordinal)
        assertEquals(3, ExpandDirection.RIGHT.ordinal)
    }

    @Test
    fun fromOrdinal_returnsUpForZero() {
        assertEquals(ExpandDirection.UP, ExpandDirection.fromOrdinal(0))
    }

    @Test
    fun fromOrdinal_returnsDownForOne() {
        assertEquals(ExpandDirection.DOWN, ExpandDirection.fromOrdinal(1))
    }

    @Test
    fun fromOrdinal_returnsLeftForTwo() {
        assertEquals(ExpandDirection.LEFT, ExpandDirection.fromOrdinal(2))
    }

    @Test
    fun fromOrdinal_returnsRightForThree() {
        assertEquals(ExpandDirection.RIGHT, ExpandDirection.fromOrdinal(3))
    }

    @Test
    fun fromOrdinal_returnsUpForInvalidPositiveValue() {
        assertEquals(ExpandDirection.UP, ExpandDirection.fromOrdinal(99))
    }

    @Test
    fun fromOrdinal_returnsUpForNegativeValue() {
        assertEquals(ExpandDirection.UP, ExpandDirection.fromOrdinal(-1))
    }

    @Test
    fun fromOrdinal_returnsUpForLargeNegativeValue() {
        assertEquals(ExpandDirection.UP, ExpandDirection.fromOrdinal(-100))
    }
}
