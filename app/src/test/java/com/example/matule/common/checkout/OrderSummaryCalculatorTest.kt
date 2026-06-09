package com.example.matule.common.checkout

import com.example.matule.common.catalog.CartManager
import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Purpose: Tests Sprint 4 order summary calculation.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderSummaryCalculatorTest {

    @Test
    fun subtotalIsCalculatedCorrectly() {
        val cart = CartManager()
        cart.add(product("1", 100.0))
        cart.add(product("1", 100.0))
        cart.add(product("2", 50.0))

        assertEquals(250.0, OrderSummaryCalculator().subtotal(cart), 0.0)
    }

    @Test
    fun totalIncludesDeliveryPrice() {
        val cart = CartManager()
        cart.add(product("1", 100.0))

        assertEquals(125.0, OrderSummaryCalculator().total(cart, deliveryPrice = 25.0), 0.0)
    }

    @Test
    fun totalForEmptyCartIsZero() {
        val cart = CartManager()

        assertEquals(0.0, OrderSummaryCalculator().total(cart, deliveryPrice = 25.0), 0.0)
    }

    private fun product(id: String, price: Double): Product {
        return Product(
            id = id,
            name = "Summary $id",
            category = "Shoes",
            price = price,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.RED),
            type = ShoeType.CASUAL,
            imageName = "$id.png",
            description = "Summary product"
        )
    }
}
