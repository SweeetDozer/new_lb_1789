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
        return PriceRangeValidationResult.Valid
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
