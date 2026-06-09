package com.example.matule.common.order

import java.time.LocalDate
import java.util.LinkedHashMap

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
        val groups = LinkedHashMap<String, MutableList<Order>>()
        for (order in orders) {
            val label = groupNameFor(order.createdAt.toLocalDate())
            groups.getOrPut(label) { mutableListOf() }.add(order)
        }
        return groups
    }

    private fun groupNameFor(date: LocalDate): String {
        return when (date) {
            today -> RECENT_GROUP
            today.minusDays(1) -> YESTERDAY_GROUP
            else -> formatOlderDate(date)
        }
    }

    private fun formatOlderDate(date: LocalDate): String {
        return "${date.dayOfMonth} ${monthName(date.monthValue)} ${date.year}"
    }

    private fun monthName(month: Int): String {
        return MONTH_NAMES[month - 1]
    }

    private companion object {
        const val RECENT_GROUP = "Недавние"
        const val YESTERDAY_GROUP = "Вчера"
        val MONTH_NAMES = listOf(
            "января",
            "февраля",
            "марта",
            "апреля",
            "мая",
            "июня",
            "июля",
            "августа",
            "сентября",
            "октября",
            "ноября",
            "декабря"
        )
    }
}
