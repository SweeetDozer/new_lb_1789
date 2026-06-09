package com.example.matule.common.checkout

import com.example.matule.common.catalog.CartManager
import com.example.matule.common.validation.EmailValidator
import com.example.matule.common.validation.Sprint2ValidationResult

/**
 * Purpose: Validates checkout data before order confirmation.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class CheckoutValidator {
    private val emailValidator = EmailValidator()

    /**
     * Purpose: Checks contact fields, address, and cart state.
     */
    fun validate(data: CheckoutData, cartManager: CartManager): Sprint2ValidationResult {
        return when {
            isEmptyEmail(data) -> Sprint2ValidationResult.Error(EMAIL_EMPTY_ERROR)
            hasInvalidEmail(data) -> Sprint2ValidationResult.Error(EMAIL_INVALID_ERROR)
            isEmptyPhone(data) -> Sprint2ValidationResult.Error(PHONE_EMPTY_ERROR)
            hasInvalidPhone(data) -> Sprint2ValidationResult.Error(PHONE_INVALID_ERROR)
            isEmptyAddress(data) -> Sprint2ValidationResult.Error(ADDRESS_EMPTY_ERROR)
            isEmptyCart(cartManager) -> Sprint2ValidationResult.Error(CART_EMPTY_ERROR)
            else -> Sprint2ValidationResult.Success
        }
    }

    private fun isEmptyEmail(data: CheckoutData): Boolean {
        return data.email.isBlank()
    }

    private fun hasInvalidEmail(data: CheckoutData): Boolean {
        return !emailValidator.isValid(data.email.trim())
    }

    private fun isEmptyPhone(data: CheckoutData): Boolean {
        return data.phone.isBlank()
    }

    private fun hasInvalidPhone(data: CheckoutData): Boolean {
        return !isValidPhone(data.phone.trim())
    }

    private fun isEmptyAddress(data: CheckoutData): Boolean {
        return data.address.isBlank()
    }

    private fun isEmptyCart(cartManager: CartManager): Boolean {
        return cartManager.totalCount() == 0
    }

    private fun isValidPhone(phone: String): Boolean {
        return PHONE_PATTERN.matches(phone)
    }

    private companion object {
        const val EMAIL_EMPTY_ERROR = "Р’РІРµРґРёС‚Рµ email"
        const val EMAIL_INVALID_ERROR = "РќРµРєРѕСЂСЂРµРєС‚РЅС‹Р№ email"
        const val PHONE_EMPTY_ERROR = "Р’РІРµРґРёС‚Рµ С‚РµР»РµС„РѕРЅ"
        const val PHONE_INVALID_ERROR = "РќРµРєРѕСЂСЂРµРєС‚РЅС‹Р№ С‚РµР»РµС„РѕРЅ"
        const val ADDRESS_EMPTY_ERROR = "Р’РІРµРґРёС‚Рµ Р°РґСЂРµСЃ"
        const val CART_EMPTY_ERROR = "РљРѕСЂР·РёРЅР° РїСѓСЃС‚Р°"
        val PHONE_PATTERN = Regex("^\\+?[0-9]{10,15}$")
    }
}
