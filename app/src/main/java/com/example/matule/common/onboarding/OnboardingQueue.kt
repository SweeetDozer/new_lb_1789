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

    /**
     * Purpose: Returns the page that should currently be visible.
     */
    fun currentPage(): OnboardingPage = TODO("GREEN stage will return current onboarding page")

    /**
     * Purpose: Moves the queue to the next page when possible.
     */
    fun moveToNext() {
        TODO("GREEN stage will update current onboarding index")
    }

    /**
     * Purpose: Returns total amount of onboarding pages.
     */
    fun count(): Int = TODO("GREEN stage will return onboarding pages count")

    /**
     * Purpose: Reports whether the onboarding contains more than one page.
     */
    fun hasMultiplePages(): Boolean = TODO("GREEN stage will check onboarding page amount")

    /**
     * Purpose: Returns zero-based indicator position for the current page.
     */
    fun currentIndicatorIndex(): Int = TODO("GREEN stage will return current indicator index")
}
