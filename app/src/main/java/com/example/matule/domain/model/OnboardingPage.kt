package com.example.matule.domain.model

/**
 * Purpose: Describes one page from the Matule onboarding flow.
 * Creation date: 2026-06-03
 * Author: Mors
 *
 * @property imageResName name of the image resource prepared for the page.
 * @property title main onboarding title text.
 * @property description short onboarding description text.
 */
data class OnboardingPage(
    val imageResName: String,
    val title: String,
    val description: String
)
