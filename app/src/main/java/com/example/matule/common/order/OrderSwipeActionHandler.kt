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
            OrderSwipeAction.RIGHT -> repeatOrder()
            OrderSwipeAction.LEFT -> cancelOrder()
            OrderSwipeAction.UNKNOWN -> noAction()
        }
    }

    private fun repeatOrder(): OrderSwipeResult = OrderSwipeResult.REPEAT

    private fun cancelOrder(): OrderSwipeResult = OrderSwipeResult.CANCEL

    private fun noAction(): OrderSwipeResult = OrderSwipeResult.NONE
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
