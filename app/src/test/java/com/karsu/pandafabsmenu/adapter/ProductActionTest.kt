package com.karsu.pandafabsmenu.adapter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ProductActionTest {

    @Test
    fun productAction_hasFourValues() {
        assertEquals(4, ProductAction.entries.size)
    }

    @Test
    fun productAction_containsPrint() {
        assertNotNull(ProductAction.valueOf("PRINT"))
    }

    @Test
    fun productAction_containsChangePrice() {
        assertNotNull(ProductAction.valueOf("CHANGE_PRICE"))
    }

    @Test
    fun productAction_containsEdit() {
        assertNotNull(ProductAction.valueOf("EDIT"))
    }

    @Test
    fun productAction_containsDelete() {
        assertNotNull(ProductAction.valueOf("DELETE"))
    }

    @Test
    fun productAction_ordinalValues() {
        assertEquals(0, ProductAction.PRINT.ordinal)
        assertEquals(1, ProductAction.CHANGE_PRICE.ordinal)
        assertEquals(2, ProductAction.EDIT.ordinal)
        assertEquals(3, ProductAction.DELETE.ordinal)
    }

    @Test(expected = IllegalArgumentException::class)
    fun productAction_invalidValueThrowsException() {
        ProductAction.valueOf("INVALID")
    }
}
