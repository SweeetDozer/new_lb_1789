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
        val minPrice = parseNullablePrice(minPriceText)
        val maxPrice = parseNullablePrice(maxPriceText)

        return when {
            minPrice.isInvalid -> PriceRangeValidationResult.Error("Invalid min price")
            maxPrice.isInvalid -> PriceRangeValidationResult.Error("Invalid max price")
            minPrice.isNegative() || maxPrice.isNegative() -> PriceRangeValidationResult.Error("Price cannot be negative")
            minPrice.isGreaterThan(maxPrice) -> PriceRangeValidationResult.Error("Min price must be less than max price")
            else -> PriceRangeValidationResult.Valid
        }
    }

    private fun parseNullablePrice(value: String): ParsedPrice {
        return if (value.isBlank()) {
            ParsedPrice(value = null, isInvalid = false)
        } else {
            val parsedValue = value.toDoubleOrNull()
            ParsedPrice(value = parsedValue, isInvalid = parsedValue == null)
        }
    }

    private data class ParsedPrice(
        val value: Double?,
        val isInvalid: Boolean
    ) {
        fun isNegative(): Boolean {
            return value != null && value < 0
        }

        fun isGreaterThan(other: ParsedPrice): Boolean {
            return value != null && other.value != null && value > other.value
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
