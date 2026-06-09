package com.example.matule.common.order

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        return if (isToday(createdAt)) formatRecentTime(createdAt) else formatClockTime(createdAt)
    }

    private fun isToday(createdAt: LocalDateTime): Boolean {
        return createdAt.toLocalDate() == now.toLocalDate()
    }

    private fun formatRecentTime(createdAt: LocalDateTime): String {
        val minutes = minutesPassed(createdAt)
        return "$minutes ${minuteWord(minutes)} $AGO_WORD"
    }

    private fun minutesPassed(createdAt: LocalDateTime): Long {
        return Duration.between(createdAt, now).toMinutes().coerceAtLeast(0)
    }

    private fun formatClockTime(createdAt: LocalDateTime): String {
        return createdAt.format(TIME_FORMATTER)
    }

    private fun minuteWord(minutes: Long): String {
        val lastTwo = minutes % 100
        val lastOne = minutes % 10
        return when {
            lastTwo in 11..14 -> "минут"
            lastOne == 1L -> "минута"
            lastOne in 2..4 -> "минуты"
            else -> "минут"
        }
    }

    private companion object {
        const val AGO_WORD = "назад"
        val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }
}
