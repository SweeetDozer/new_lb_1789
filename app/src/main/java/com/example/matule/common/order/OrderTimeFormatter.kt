package com.example.matule.common.order

import java.time.LocalDateTime

/**
 * Purpose: Formats order creation time for Sprint 5 order cards.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderTimeFormatter(
    private val now: LocalDateTime
) {

    /**
     * Purpose: Returns minutes passed for recent orders or HH:mm for older orders.
     */
    fun format(createdAt: LocalDateTime): String {
        TODO("Sprint 5 GREEN")
    }
}

