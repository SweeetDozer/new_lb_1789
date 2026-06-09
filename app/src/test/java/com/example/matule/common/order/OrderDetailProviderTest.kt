package com.example.matule.common.order

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDateTime

/**
 * Purpose: Tests Sprint 5 order detail lookup.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderDetailProviderTest {

    @Test
    fun existingOrderDetailsAreReturnedById() {
        val order = order("1")

        val result = OrderDetailProvider(listOf(order)).findById("1")

        assertEquals(order, result)
    }

    @Test
    fun unknownOrderIdReturnsNull() {
        val result = OrderDetailProvider(listOf(order("1"))).findById("404")

        assertNull(result)
    }

    private fun order(id: String): Order {
        return Order(
            id = id,
            items = listOf("Nike"),
            totalPrice = 100.0,
            createdAt = LocalDateTime.of(2026, 6, 9, 12, 0),
            status = OrderStatus.CREATED
        )
    }
}

