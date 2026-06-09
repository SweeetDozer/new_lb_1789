package com.example.matule.common.checkout

import com.example.matule.common.catalog.CartManager
import com.example.matule.common.validation.Sprint2ValidationResult
import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 4 checkout validation.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class CheckoutValidatorTest {

    @Test
    fun emptyEmailReturnsError() {
        assertError(data(email = ""))
    }

    @Test
    fun invalidEmailReturnsError() {
        assertError(data(email = "bad-email"))
    }

    @Test
    fun emptyPhoneReturnsError() {
        assertError(data(phone = ""))
    }

    @Test
    fun invalidPhoneReturnsError() {
        assertError(data(phone = "abc"))
    }

    @Test
    fun emptyAddressReturnsError() {
        assertError(data(address = ""))
    }

    @Test
    fun emptyCartReturnsError() {
        val result = CheckoutValidator().validate(data(), CartManager())

        assertTrue(result is Sprint2ValidationResult.Error)
    }

    @Test
    fun validCheckoutDataReturnsSuccess() {
        val cart = CartManager()
        cart.add(product())

        val result = CheckoutValidator().validate(data(), cart)

        assertTrue(result is Sprint2ValidationResult.Success)
    }

    private fun assertError(data: CheckoutData) {
        val cart = CartManager()
        cart.add(product())

        val result = CheckoutValidator().validate(data, cart)

        assertTrue(result is Sprint2ValidationResult.Error)
    }

    private fun data(
        email: String = "test123@mail.com",
        phone: String = "+79990001122",
        address: String = "Kaliningrad, Lenina 1"
    ): CheckoutData {
        return CheckoutData(
            email = email,
            phone = phone,
            address = address,
            deliverySource = DeliveryDataSource.PROFILE
        )
    }

    private fun product(): Product {
        return Product(
            id = "1",
            name = "Checkout",
            category = "Shoes",
            price = 100.0,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.WHITE),
            type = ShoeType.SPORT,
            imageName = "checkout.png",
            description = "Checkout product"
        )
    }
}
