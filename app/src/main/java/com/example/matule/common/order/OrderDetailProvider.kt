package com.example.matule.common.order

/**
 * Purpose: Finds selected order details by order id.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderDetailProvider(
    private val orders: List<Order>
) {

    /**
     * Purpose: Returns order by id or null when it does not exist.
     */
    fun findById(id: String): Order? {
        TODO("Sprint 5 GREEN")
    }
}

