package com.example.matule.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R

/**
 * Purpose: Shows the Sprint 1 Matule splash screen before onboarding.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class SplashFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())
    private val openOnboardingRunnable = Runnable {
        if (isAdded) {
            findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
        }
    }

    /**
     * Purpose: Creates splash XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_splash, container, false)

    /**
     * Purpose: Starts delayed navigation after splash is visible.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed(openOnboardingRunnable, SPLASH_DELAY_MS)
    }

    /**
     * Purpose: Removes delayed callback when view is destroyed.
     */
    override fun onDestroyView() {
        handler.removeCallbacks(openOnboardingRunnable)
        super.onDestroyView()
    }

    private companion object {
        const val SPLASH_DELAY_MS = 1800L
    }
}
