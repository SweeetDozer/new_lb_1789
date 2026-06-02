package com.example.matule.common.onboarding

import com.example.matule.domain.model.OnboardingPage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for onboarding queue behavior.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class OnboardingQueueTest {

    private val pages = listOf(
        OnboardingPage("onboarding_first", "Welcome", "Find the shoes you need"),
        OnboardingPage("onboarding_second", "Comfort", "Choose your favorite model"),
        OnboardingPage("onboarding_third", "Start", "Move to the shop")
    )

    /**
     * Purpose: Checks that onboarding data is returned in the same order as prepared.
     */
    @Test
    fun imageAndTextAreExtractedInCorrectOrder() {
        val queue = OnboardingQueue(pages)

        assertEquals("onboarding_first", queue.currentPage().imageResName)
        assertEquals("Welcome", queue.currentPage().title)
        assertEquals("Find the shoes you need", queue.currentPage().description)
    }

    /**
     * Purpose: Checks queue movement and indicator index after opening the next page.
     */
    @Test
    fun afterMovingToNextItemQueueAndCurrentIndexChangeCorrectly() {
        val queue = OnboardingQueue(pages)

        queue.moveToNext()

        assertEquals("onboarding_second", queue.currentPage().imageResName)
        assertEquals(1, queue.currentIndicatorIndex())
    }

    /**
     * Purpose: Checks indicator count when several onboarding pages exist.
     */
    @Test
    fun ifThereAreSeveralOnboardingPagesIndicatorCountIsCorrect() {
        val queue = OnboardingQueue(pages)

        assertEquals(3, queue.count())
        assertTrue(queue.hasMultiplePages())
    }
}
