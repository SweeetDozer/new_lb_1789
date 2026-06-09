package com.example.matule.common.order

import java.time.LocalDate

/**
 * Purpose: Groups orders by display date labels.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderDateGrouper(
    private val today: LocalDate
) {

    /**
     * Purpose: Returns orders grouped by Sprint 5 date labels.
     */
    fun group(orders: List<Order>): Map<String, List<Order>> {
        TODO("Sprint 5 GREEN")
    }
}

