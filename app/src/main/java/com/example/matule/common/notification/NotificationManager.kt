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
        return items.toList()
    }

    /**
     * Purpose: Counts unread notifications.
     */
    fun unreadCount(): Int {
        return items.count { item -> isUnread(item) }
    }

    /**
     * Purpose: Marks selected notification as read.
     */
    fun markAsRead(id: String) {
        val index = findIndexById(id)
        if (index != -1) {
            markItemAsRead(index)
        }
    }

    private fun isUnread(item: NotificationItem): Boolean {
        return !item.isRead
    }

    private fun findIndexById(id: String): Int {
        return items.indexOfFirst { item -> item.id == id }
    }

    private fun markItemAsRead(index: Int) {
        items[index] = items[index].copy(isRead = true)
    }
}
