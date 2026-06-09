package com.example.matule.common.menu

/**
 * Purpose: Contains pure swipe-back decision logic for the side menu.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SwipeBackState(
    private val threshold: Float = 120f
) {

    /**
     * Purpose: Returns true when swipe distance is enough to close the menu.
     */
    fun shouldClose(distance: Float): Boolean {
        return isLongEnough(distance)
    }

    private fun isLongEnough(distance: Float): Boolean {
        return distance > threshold
    }
}
