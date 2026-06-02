package com.example.matule.common.onboarding

import com.example.matule.domain.model.OnboardingPage

/**
 * Purpose: Stores onboarding pages and exposes simple navigation state for indicators.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class OnboardingQueue(
    private val onboardingPages: List<OnboardingPage>
) {
    private var selectedPageIndex: Int = FIRST_PAGE_INDEX

    /**
     * Purpose: Returns the page that should currently be visible or an empty page when the list has no data.
     */
    fun currentPage(): OnboardingPage {
        return onboardingPages.getOrNull(selectedPageIndex) ?: EMPTY_PAGE
    }

    /**
     * Purpose: Moves the queue to the next page when possible.
     */
    fun moveToNext() {
        if (canMoveToNextPage()) {
            selectedPageIndex++
        }
    }

    /**
     * Purpose: Returns total amount of onboarding pages.
     */
    fun count(): Int = onboardingPages.size

    /**
     * Purpose: Reports whether the onboarding contains more than one page.
     */
    fun hasMultiplePages(): Boolean = onboardingPages.size > SINGLE_PAGE_COUNT

    /**
     * Purpose: Returns zero-based indicator position for the current page.
     */
    fun currentIndicatorIndex(): Int = selectedPageIndex

    /**
     * Purpose: Checks whether the selected page can move forward without leaving the list bounds.
     */
    private fun canMoveToNextPage(): Boolean = selectedPageIndex < onboardingPages.lastIndex

    private companion object {
        const val FIRST_PAGE_INDEX = 0
        const val SINGLE_PAGE_COUNT = 1

        val EMPTY_PAGE = OnboardingPage(
            imageResName = "",
            title = "",
            description = ""
        )
    }
}
