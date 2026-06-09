package com.example.matule.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.profile.LoyaltyQrState

/**
 * Purpose: Shows fullscreen Sprint 5 loyalty QR placeholder and controls brightness.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class LoyaltyQrFragment : Fragment() {
    private val qrState = LoyaltyQrState(ProfileUiState.profile.loyaltyQrData)
    private var previousBrightness: Float? = null

    /**
     * Purpose: Creates QR fullscreen XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_loyalty_qr, container, false)

    /**
     * Purpose: Shows loyalty data and applies temporary brightness.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.qrDataTextView).text = qrState.loyaltyData
        view.findViewById<View>(R.id.qrCloseButton).setOnClickListener { findNavController().popBackStack() }
        applyQrBrightness()
    }

    override fun onDestroyView() {
        restoreBrightness()
        super.onDestroyView()
    }

    private fun applyQrBrightness() {
        val window = activity?.window ?: return
        val attributes = window.attributes
        previousBrightness = attributes.screenBrightness
        attributes.screenBrightness = qrState.brightnessPercent / 100f
        window.attributes = attributes
    }

    private fun restoreBrightness() {
        val window = activity?.window ?: return
        val attributes = window.attributes
        attributes.screenBrightness = previousBrightness ?: WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        window.attributes = attributes
    }
}

