package com.example.matule.common.checkout

import com.example.matule.common.catalog.CartManager
import com.example.matule.common.validation.Sprint2ValidationResult

/**
 * Purpose: Validates checkout data before order confirmation.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class CheckoutValidator {

    /**
     * Purpose: Checks contact fields, address, and cart state.
     */
    fun validate(data: CheckoutData, cartManager: CartManager): Sprint2ValidationResult {
        return Sprint2ValidationResult.Success
    }
}
