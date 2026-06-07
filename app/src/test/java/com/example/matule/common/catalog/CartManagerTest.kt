package com.example.matule.common.catalog

import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests local cart behavior for Sprint 3 Cart interactions.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class CartManagerTest {

    private val product = product("1")

    @Test
    fun addProductToCart() {
        val manager = CartManager()

        manager.add(product)

        assertEquals(1, manager.getItem(product.id)?.quantity)
    }

    @Test
    fun addingSameProductIncreasesQuantity() {
        val manager = CartManager()

        manager.add(product)
        manager.add(product)

        assertEquals(2, manager.getItem(product.id)?.quantity)
    }

    @Test
    fun increaseQuantityWorks() {
        val manager = CartManager()

        manager.add(product)
        manager.increase(product.id)

        assertEquals(2, manager.getItem(product.id)?.quantity)
    }

    @Test
    fun decreaseQuantityWorks() {
        val manager = CartManager()

        manager.add(product)
        manager.increase(product.id)
        manager.decrease(product.id)

        assertEquals(1, manager.getItem(product.id)?.quantity)
    }

    @Test
    fun quantityDoesNotBecomeNegative() {
        val manager = CartManager()

        manager.add(product)
        manager.decrease(product.id)
        manager.decrease(product.id)

        assertTrue((manager.getItem(product.id)?.quantity ?: 0) >= 0)
    }

    @Test
    fun removeProductFromCart() {
        val manager = CartManager()

        manager.add(product)
        manager.remove(product.id)

        assertNull(manager.getItem(product.id))
    }

    @Test
    fun totalItemCountIsCorrect() {
        val manager = CartManager()
        val secondProduct = product("2")

        manager.add(product)
        manager.add(product)
        manager.add(secondProduct)

        assertEquals(3, manager.totalCount())
    }

    private fun product(id: String): Product {
        return Product(
            id = id,
            name = "Cart $id",
            category = "Shoes",
            price = 150.0,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.WHITE),
            type = ShoeType.SPORT,
            imageName = "$id.png",
            description = "Cart product"
        )
    }
}
