package com.example.matule.common.catalog

/**
 * Purpose: Validates text price range values from Sprint 3 filters.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class PriceRangeValidator {

    /**
     * Purpose: Checks that min and max prices are empty or valid non-negative numbers.
     */
    fun validate(minPriceText: String, maxPriceText: String): PriceRangeValidationResult {
        val minPrice = parsePrice(minPriceText)
        val maxPrice = parsePrice(maxPriceText)

        return when {
            minPriceText.isNotBlank() && minPrice == null -> PriceRangeValidationResult.Error("Invalid min price")
            maxPriceText.isNotBlank() && maxPrice == null -> PriceRangeValidationResult.Error("Invalid max price")
            minPrice != null && minPrice < 0 -> PriceRangeValidationResult.Error("Price cannot be negative")
            maxPrice != null && maxPrice < 0 -> PriceRangeValidationResult.Error("Price cannot be negative")
            minPrice != null && maxPrice != null && minPrice > maxPrice -> PriceRangeValidationResult.Error("Min price must be less than max price")
            else -> PriceRangeValidationResult.Valid
        }
    }

    private fun parsePrice(value: String): Double? {
        return if (value.isBlank()) {
            null
        } else {
            value.toDoubleOrNull()
        }
    }
}

/**
 * Purpose: Represents price range validation result for filters.
 * Creation date: 2026-06-07
 * Author: Mors
 */
sealed class PriceRangeValidationResult {
    object Valid : PriceRangeValidationResult()
    data class Error(val message: String) : PriceRangeValidationResult()
}
