package com.example.matule.common.menu

/**
 * Purpose: Describes side menu navigation rules for Sprint 5.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SideMenuNavigator(
    private val items: List<SideMenuItem>
) {

    /**
     * Purpose: Returns destination for selected enabled menu item.
     */
    fun destinationFor(itemId: String): String? {
        return items.firstOrNull { item ->
            item.id == itemId && item.isEnabled
        }?.destination
    }
}
