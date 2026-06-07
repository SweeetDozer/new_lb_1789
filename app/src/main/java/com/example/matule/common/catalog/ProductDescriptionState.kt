package com.example.matule.common.catalog

/**
 * Purpose: Stores expanded/collapsed state for product description on Details screen.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ProductDescriptionState {
    var isExpanded: Boolean = false
        private set

    /**
     * Purpose: Returns max lines for description according to current state.
     */
    fun maxLines(): Int {
        return if (isExpanded) {
            EXPANDED_MAX_LINES
        } else {
            COLLAPSED_MAX_LINES
        }
    }

    /**
     * Purpose: Expands description.
     */
    fun expand() {
        setExpanded(true)
    }

    /**
     * Purpose: Collapses description.
     */
    fun collapse() {
        setExpanded(false)
    }

    /**
     * Purpose: Switches description between expanded and collapsed states.
     */
    fun toggle() {
        setExpanded(!isExpanded)
    }

    private fun setExpanded(expanded: Boolean) {
        isExpanded = expanded
    }

    private companion object {
        const val COLLAPSED_MAX_LINES = 2
        const val EXPANDED_MAX_LINES = Int.MAX_VALUE
    }
}
