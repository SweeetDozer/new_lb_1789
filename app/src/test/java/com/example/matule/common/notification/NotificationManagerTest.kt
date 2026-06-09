package com.example.matule.common.notification

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

/**
 * Purpose: Tests Sprint 5 notification logic.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class NotificationManagerTest {

    @Test
    fun notificationListCanBeEmpty() {
        assertTrue(NotificationManager().list().isEmpty())
    }

    @Test
    fun unreadCountIsCalculated() {
        val manager = NotificationManager(listOf(notification("1", false), notification("2", true)))

        assertEquals(1, manager.unreadCount())
    }

    @Test
    fun markingNotificationAsReadReducesUnreadCount() {
        val manager = NotificationManager(listOf(notification("1", false), notification("2", false)))

        manager.markAsRead("1")

        assertEquals(1, manager.unreadCount())
    }

    @Test
    fun notificationRemainsInListAfterRead() {
        val manager = NotificationManager(listOf(notification("1", false)))

        manager.markAsRead("1")

        assertEquals(1, manager.list().size)
    }

    private fun notification(id: String, isRead: Boolean): NotificationItem {
        return NotificationItem(
            id = id,
            title = "Sale",
            message = "New sale",
            createdAt = LocalDateTime.of(2026, 6, 9, 12, 0),
            isRead = isRead
        )
    }
}

