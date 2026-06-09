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
        val item = findMenuItem(itemId)
        return if (canNavigate(item)) item?.destination else null
    }

    private fun findMenuItem(itemId: String): SideMenuItem? {
        return items.firstOrNull { item -> item.id == itemId }
    }

    private fun canNavigate(item: SideMenuItem?): Boolean {
        return item != null && item.isEnabled
    }
}
