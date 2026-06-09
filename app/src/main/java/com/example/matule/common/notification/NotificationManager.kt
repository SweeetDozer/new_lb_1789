package com.example.matule.common.notification

/**
 * Purpose: Manages notification list and read state.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class NotificationManager(
    notifications: List<NotificationItem> = emptyList()
) {
    private val items = notifications.toMutableList()

    /**
     * Purpose: Returns all notifications.
     */
    fun list(): List<NotificationItem> {
        TODO("Sprint 5 GREEN")
    }

    /**
     * Purpose: Counts unread notifications.
     */
    fun unreadCount(): Int {
        TODO("Sprint 5 GREEN")
    }

    /**
     * Purpose: Marks selected notification as read.
     */
    fun markAsRead(id: String) {
        TODO("Sprint 5 GREEN")
    }
}

