package com.karsu.pandafabsmenu.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductTest {

    @Test
    fun getDummyProducts_returns5Products() {
        val products = Product.getDummyProducts()
        assertEquals(5, products.size)
    }

    @Test
    fun getDummyProducts_allIdsAreUnique() {
        val products = Product.getDummyProducts()
        val ids = products.map { it.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun getDummyProducts_allNamesAreNotEmpty() {
        val products = Product.getDummyProducts()
        products.forEach { product ->
            assertTrue(
                "Product name should not be empty for id=${product.id}",
                product.name.isNotEmpty()
            )
        }
    }

    @Test
    fun getDummyProducts_allDescriptionsAreNotEmpty() {
        val products = Product.getDummyProducts()
        products.forEach { product ->
            assertTrue(
                "Product description should not be empty for id=${product.id}",
                product.description.isNotEmpty()
            )
        }
    }

    @Test
    fun getDummyProducts_allPricesArePositive() {
        val products = Product.getDummyProducts()
        products.forEach { product ->
            assertTrue(
                "Product price should be positive for id=${product.id}",
                product.price > 0.0
            )
        }
    }

    @Test
    fun getDummyProducts_idsAreSequential() {
        val products = Product.getDummyProducts()
        val ids = products.map { it.id }
        assertEquals(listOf(1, 2, 3, 4, 5), ids)
    }

    @Test
    fun product_equalityWorksCorrectly() {
        val product1 = Product(1, "Test", "Desc", 10.0, 0)
        val product2 = Product(1, "Test", "Desc", 10.0, 0)
        assertEquals(product1, product2)
    }

    @Test
    fun product_inequalityForDifferentIds() {
        val product1 = Product(1, "Test", "Desc", 10.0, 0)
        val product2 = Product(2, "Test", "Desc", 10.0, 0)
        assertNotEquals(product1, product2)
    }

    @Test
    fun product_hashCodeConsistency() {
        val product1 = Product(1, "Test", "Desc", 10.0, 0)
        val product2 = Product(1, "Test", "Desc", 10.0, 0)
        assertEquals(product1.hashCode(), product2.hashCode())
    }

    @Test
    fun product_copyCreatesIndependentInstance() {
        val original = Product(1, "Test", "Desc", 10.0, 0)
        val copy = original.copy(name = "Modified")
        assertEquals("Modified", copy.name)
        assertEquals("Test", original.name)
        assertEquals(original.id, copy.id)
    }

    @Test
    fun getDummyProducts_firstProductIsWirelessHeadphones() {
        val products = Product.getDummyProducts()
        assertEquals("Wireless Headphones", products[0].name)
        assertEquals(149.99, products[0].price, 0.001)
    }

    @Test
    fun getDummyProducts_lastProductIsWirelessCharger() {
        val products = Product.getDummyProducts()
        assertEquals("Wireless Charger", products[4].name)
        assertEquals(29.99, products[4].price, 0.001)
    }
}
