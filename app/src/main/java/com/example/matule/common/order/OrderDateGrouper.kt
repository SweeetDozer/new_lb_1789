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
            val label = labelFor(order.createdAt.toLocalDate())
            groups.getOrPut(label) { mutableListOf() }.add(order)
        }
        return groups
    }

    private fun labelFor(date: LocalDate): String {
        return when (date) {
            today -> "Недавние"
            today.minusDays(1) -> "Вчера"
            else -> "${date.dayOfMonth} ${monthName(date.monthValue)} ${date.year}"
        }
    }

    private fun monthName(month: Int): String {
        return listOf(
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
        )[month - 1]
    }
}
