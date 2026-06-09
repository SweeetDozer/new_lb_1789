package com.example.matule.common.order

/**
 * Purpose: Converts order card swipe direction into order action.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderSwipeActionHandler {

    /**
     * Purpose: Returns action for a swipe direction.
     */
    fun handle(action: OrderSwipeAction): OrderSwipeResult {
        return when (action) {
            OrderSwipeAction.RIGHT -> OrderSwipeResult.REPEAT
            OrderSwipeAction.LEFT -> OrderSwipeResult.CANCEL
            OrderSwipeAction.UNKNOWN -> OrderSwipeResult.NONE
        }
    }
}

/**
 * Purpose: Lists supported order swipe directions.
 */
enum class OrderSwipeAction {
    RIGHT,
    LEFT,
    UNKNOWN
}

/**
 * Purpose: Lists results of order swipe processing.
 */
enum class OrderSwipeResult {
    REPEAT,
    CANCEL,
    NONE
}
