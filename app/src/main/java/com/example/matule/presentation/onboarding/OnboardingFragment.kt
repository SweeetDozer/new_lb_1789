package com.example.matule.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.onboarding.OnboardingQueue
import com.example.matule.domain.model.OnboardingPage

/**
 * Purpose: Shows three Sprint 1 onboarding pages using Figma image assets.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class OnboardingFragment : Fragment() {

    private lateinit var queue: OnboardingQueue
    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var skipTextView: TextView
    private lateinit var nextButton: Button
    private lateinit var dotsContainer: LinearLayout

    private val imageNames = listOf("img_onboard_1", "img_onboard_2", "img_onboard_3")

    /**
     * Purpose: Creates onboarding XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_onboarding, container, false)

    /**
     * Purpose: Prepares page data and screen actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        queue = OnboardingQueue(createPages())
        createDots()
        showCurrentPage(animate = false)
        setupClicks()
    }

    /**
     * Purpose: Finds onboarding views from XML.
     */
    private fun bindViews(view: View) {
        imageView = view.findViewById(R.id.onboardingImageView)
        titleTextView = view.findViewById(R.id.onboardingTitleTextView)
        descriptionTextView = view.findViewById(R.id.onboardingDescriptionTextView)
        skipTextView = view.findViewById(R.id.skipTextView)
        nextButton = view.findViewById(R.id.nextButton)
        dotsContainer = view.findViewById(R.id.dotsContainer)
    }

    /**
     * Purpose: Builds onboarding pages from resources.
     */
    private fun createPages(): List<OnboardingPage> {
        return listOf(
            OnboardingPage(imageNames[0], getString(R.string.onboarding_title_1), getString(R.string.onboarding_description_1)),
            OnboardingPage(imageNames[1], getString(R.string.onboarding_title_2), getString(R.string.onboarding_description_2)),
            OnboardingPage(imageNames[2], getString(R.string.onboarding_title_3), getString(R.string.onboarding_description_3))
        )
    }

    /**
     * Purpose: Creates one visual dot for each onboarding page.
     */
    private fun createDots() {
        dotsContainer.removeAllViews()
        repeat(queue.count()) {
            val dot = View(requireContext())
            val margin = resources.getDimensionPixelSize(R.dimen.dot_margin)
            val params = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.dot_width),
                resources.getDimensionPixelSize(R.dimen.dot_height)
            )
            params.setMargins(margin, 0, margin, 0)
            dotsContainer.addView(dot, params)
        }
    }

    /**
     * Purpose: Updates visible page content and controls.
     */
    private fun showCurrentPage(animate: Boolean) {
        val page = queue.currentPage()
        if (animate) {
            fadeToPage(page)
        } else {
            bindPage(page)
        }
        updateDots()
        updateButtons()
    }

    /**
     * Purpose: Changes page with a small fade animation.
     */
    private fun fadeToPage(page: OnboardingPage) {
        val animatedViews = listOf(imageView, titleTextView, descriptionTextView)
        animatedViews.forEach { it.animate().alpha(0f).setDuration(FADE_DURATION_MS).start() }
        titleTextView.postDelayed({
            bindPage(page)
            animatedViews.forEach { it.animate().alpha(1f).setDuration(FADE_DURATION_MS).start() }
        }, FADE_DURATION_MS)
    }

    /**
     * Purpose: Places current onboarding page data into XML views.
     */
    private fun bindPage(page: OnboardingPage) {
        titleTextView.text = page.title
        descriptionTextView.text = page.description
        imageView.setImageResource(imageResourceId(page.imageResName))
    }

    /**
     * Purpose: Gets drawable resource id by stored image name.
     */
    private fun imageResourceId(imageName: String): Int {
        return resources.getIdentifier(imageName, "drawable", requireContext().packageName)
    }

    /**
     * Purpose: Refreshes active and inactive onboarding dots.
     */
    private fun updateDots() {
        for (index in 0 until dotsContainer.childCount) {
            val dotBackground = if (index == queue.currentIndicatorIndex()) {
                R.drawable.dot_active
            } else {
                R.drawable.dot_inactive
            }
            dotsContainer.getChildAt(index).setBackgroundResource(dotBackground)
        }
    }

    /**
     * Purpose: Sets skip visibility and current page button text.
     */
    private fun updateButtons() {
        val index = queue.currentIndicatorIndex()
        skipTextView.visibility = if (index == FIRST_PAGE_INDEX) View.VISIBLE else View.INVISIBLE
        nextButton.text = when (index) {
            FIRST_PAGE_INDEX -> getString(R.string.onboarding_start)
            queue.count() - 1 -> getString(R.string.onboarding_sign_in)
            else -> getString(R.string.onboarding_next)
        }
    }

    /**
     * Purpose: Handles skip and next button clicks.
     */
    private fun setupClicks() {
        skipTextView.setOnClickListener { openSignIn() }
        nextButton.setOnClickListener {
            if (queue.currentIndicatorIndex() == queue.count() - 1) {
                openSignIn()
            } else {
                queue.moveToNext()
                showCurrentPage(animate = true)
            }
        }
    }

    /**
     * Purpose: Opens Sign In after onboarding.
     */
    private fun openSignIn() {
        findNavController().navigate(R.id.action_onboardingFragment_to_signInFragment)
    }

    private companion object {
        const val FIRST_PAGE_INDEX = 0
        const val FADE_DURATION_MS = 160L
    }
}
