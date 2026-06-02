package com.example.matule.common.onboarding

import com.example.matule.domain.model.OnboardingPage

/**
 * Purpose: Stores onboarding pages and exposes simple navigation state for indicators.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class OnboardingQueue(
    private val pages: List<OnboardingPage>
) {
    private var currentIndex: Int = 0

    /**
     * Purpose: Returns the page that should currently be visible.
     */
    fun currentPage(): OnboardingPage {
        return pages.getOrNull(currentIndex) ?: OnboardingPage(
            imageResName = "",
            title = "",
            description = ""
        )
    }

    /**
     * Purpose: Moves the queue to the next page when possible.
     */
    fun moveToNext() {
        if (currentIndex < pages.lastIndex) {
            currentIndex++
        }
    }

    /**
     * Purpose: Returns total amount of onboarding pages.
     */
    fun count(): Int = pages.size

    /**
     * Purpose: Reports whether the onboarding contains more than one page.
     */
    fun hasMultiplePages(): Boolean = pages.size > 1

    /**
     * Purpose: Returns zero-based indicator position for the current page.
     */
    fun currentIndicatorIndex(): Int = currentIndex
}
