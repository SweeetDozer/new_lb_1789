package com.example.matule.common.order

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Purpose: Tests Sprint 5 order date grouping.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderDateGrouperTest {
    private val today = LocalDate.of(2026, 6, 9)
    private val grouper = OrderDateGrouper(today)

    @Test
    fun todayOrdersAreGroupedIntoRecent() {
        val groups = grouper.group(listOf(order("1", today.atTime(10, 0))))

        assertTrue(groups.containsKey("Недавние"))
    }

    @Test
    fun yesterdayOrdersAreGroupedIntoYesterday() {
        val groups = grouper.group(listOf(order("1", today.minusDays(1).atTime(10, 0))))

        assertTrue(groups.containsKey("Вчера"))
    }

    @Test
    fun olderOrdersUseRussianDateFormat() {
        val groups = grouper.group(listOf(order("1", LocalDateTime.of(2025, 3, 3, 14, 35))))

        assertTrue(groups.containsKey("3 марта 2025"))
    }

    @Test
    fun groupsPreserveOrderInsideGroup() {
        val first = order("1", today.atTime(10, 0))
        val second = order("2", today.atTime(11, 0))

        val group = grouper.group(listOf(first, second)).getValue("Недавние")

        assertEquals(listOf(first, second), group)
    }

    private fun order(id: String, createdAt: LocalDateTime): Order {
        return Order(
            id = id,
            items = listOf("Nike"),
            totalPrice = 100.0,
            createdAt = createdAt,
            status = OrderStatus.CREATED
        )
    }
}

