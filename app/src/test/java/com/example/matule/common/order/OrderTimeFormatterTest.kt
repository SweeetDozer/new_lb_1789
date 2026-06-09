package com.example.matule.common.order

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

/**
 * Purpose: Tests Sprint 5 order time formatting.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderTimeFormatterTest {
    private val now = LocalDateTime.of(2026, 6, 9, 15, 30)
    private val formatter = OrderTimeFormatter(now)

    @Test
    fun todayRecentOrderShowsMinutesPassed() {
        assertEquals("5 минут назад", formatter.format(now.minusMinutes(5)))
    }

    @Test
    fun olderOrderShowsHourAndMinute() {
        assertEquals("14:35", formatter.format(LocalDateTime.of(2026, 6, 8, 14, 35)))
    }
}

