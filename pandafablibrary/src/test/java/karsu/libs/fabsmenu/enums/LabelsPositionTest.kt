package karsu.libs.fabsmenu.enums

import org.junit.Assert.assertEquals
import org.junit.Test

class LabelsPositionTest {

    @Test
    fun labelsPosition_hasTwoValues() {
        assertEquals(2, LabelsPosition.entries.size)
    }

    @Test
    fun labelsPosition_valuesInCorrectOrder() {
        assertEquals(0, LabelsPosition.LEFT.ordinal)
        assertEquals(1, LabelsPosition.RIGHT.ordinal)
    }

    @Test
    fun fromOrdinal_returnsLeftForZero() {
        assertEquals(LabelsPosition.LEFT, LabelsPosition.fromOrdinal(0))
    }

    @Test
    fun fromOrdinal_returnsRightForOne() {
        assertEquals(LabelsPosition.RIGHT, LabelsPosition.fromOrdinal(1))
    }

    @Test
    fun fromOrdinal_returnsLeftForInvalidPositiveValue() {
        assertEquals(LabelsPosition.LEFT, LabelsPosition.fromOrdinal(99))
    }

    @Test
    fun fromOrdinal_returnsLeftForNegativeValue() {
        assertEquals(LabelsPosition.LEFT, LabelsPosition.fromOrdinal(-1))
    }

    @Test
    fun fromOrdinal_returnsLeftForBoundaryValue() {
        assertEquals(LabelsPosition.LEFT, LabelsPosition.fromOrdinal(2))
    }
}
