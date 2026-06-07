package com.example.matule.common.catalog

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 3 price range validation for Filters screen.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class PriceRangeValidatorTest {

    private val validator = PriceRangeValidator()

    @Test
    fun emptyMinAndMaxIsValid() {
        val result = validator.validate("", "")

        assertTrue(result is PriceRangeValidationResult.Valid)
    }

    @Test
    fun nonNumberMinIsInvalid() {
        val result = validator.validate("abc", "100")

        assertTrue(result is PriceRangeValidationResult.Error)
    }

    @Test
    fun nonNumberMaxIsInvalid() {
        val result = validator.validate("10", "max")

        assertTrue(result is PriceRangeValidationResult.Error)
    }

    @Test
    fun negativePriceIsInvalid() {
        val result = validator.validate("-1", "100")

        assertTrue(result is PriceRangeValidationResult.Error)
    }

    @Test
    fun minGreaterThanMaxIsInvalid() {
        val result = validator.validate("200", "100")

        assertTrue(result is PriceRangeValidationResult.Error)
    }

    @Test
    fun validRangeIsValid() {
        val result = validator.validate("100", "200")

        assertTrue(result is PriceRangeValidationResult.Valid)
    }
}
