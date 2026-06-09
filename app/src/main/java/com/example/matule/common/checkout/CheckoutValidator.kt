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
            data.email.isBlank() -> Sprint2ValidationResult.Error("Введите email")
            !emailValidator.isValid(data.email.trim()) -> Sprint2ValidationResult.Error("Некорректный email")
            data.phone.isBlank() -> Sprint2ValidationResult.Error("Введите телефон")
            !isValidPhone(data.phone.trim()) -> Sprint2ValidationResult.Error("Некорректный телефон")
            data.address.isBlank() -> Sprint2ValidationResult.Error("Введите адрес")
            cartManager.totalCount() == 0 -> Sprint2ValidationResult.Error("Корзина пуста")
            else -> Sprint2ValidationResult.Success
        }
    }

    private fun isValidPhone(phone: String): Boolean {
        return PHONE_PATTERN.matches(phone)
    }

    private companion object {
        val PHONE_PATTERN = Regex("^\\+?[0-9]{10,15}$")
    }
}
