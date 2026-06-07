package com.example.matule.presentation.auth.otp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.validation.OtpTimerLogic
import com.example.matule.common.validation.OtpValidator
import com.example.matule.common.validation.Sprint2ValidationResult

/**
 * Purpose: Shows Sprint 2 OTP verification screen with simple timer and local code check.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class OtpVerificationFragment : Fragment() {

    private val otpValidator = OtpValidator(expectedOtp = DEMO_OTP)
    private var timerLogic = OtpTimerLogic()
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var otpEditText: EditText
    private lateinit var errorTextView: TextView
    private lateinit var resendTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var backButton: TextView

    private val timerRunnable = object : Runnable {
        override fun run() {
            val seconds = timerLogic.tick()
            renderTimer(seconds)
            if (seconds > 0) {
                handler.postDelayed(this, ONE_SECOND_MS)
            }
        }
    }

    /**
     * Purpose: Creates OTP XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_otp_verification, container, false)

    /**
     * Purpose: Prepares OTP input, timer, resend, and back navigation.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupClicks()
        setupOtpWatcher()
        startTimer()
    }

    override fun onDestroyView() {
        handler.removeCallbacks(timerRunnable)
        super.onDestroyView()
    }

    private fun bindViews(view: View) {
        otpEditText = view.findViewById(R.id.otpEditText)
        errorTextView = view.findViewById(R.id.otpErrorTextView)
        resendTextView = view.findViewById(R.id.resendTextView)
        timerTextView = view.findViewById(R.id.timerTextView)
        backButton = view.findViewById(R.id.otpBackButton)
    }

    private fun setupClicks() {
        backButton.setOnClickListener { findNavController().navigateUp() }
        resendTextView.setOnClickListener {
            if (timerLogic.isResendAvailable(timerLogic.currentSeconds())) {
                restartTimer()
            }
        }
    }

    private fun setupOtpWatcher() {
        otpEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                errorTextView.visibility = View.GONE
                otpEditText.setBackgroundResource(R.drawable.bg_input)
                if ((s?.length ?: 0) == OTP_LENGTH) {
                    validateOtp(s.toString())
                }
            }
        })
    }

    private fun validateOtp(otp: String) {
        when (otpValidator.validate(otp)) {
            is Sprint2ValidationResult.Error -> showOtpError()
            Sprint2ValidationResult.Success -> findNavController().navigate(R.id.action_otpVerificationFragment_to_updatePasswordFragment)
        }
    }

    private fun showOtpError() {
        otpEditText.setBackgroundResource(R.drawable.bg_input_error)
        errorTextView.visibility = View.VISIBLE
        Toast.makeText(requireContext(), R.string.otp_wrong_code, Toast.LENGTH_SHORT).show()
    }

    private fun startTimer() {
        renderTimer(timerLogic.currentSeconds())
        handler.removeCallbacks(timerRunnable)
        handler.postDelayed(timerRunnable, ONE_SECOND_MS)
    }

    private fun restartTimer() {
        timerLogic = OtpTimerLogic()
        startTimer()
    }

    private fun renderTimer(seconds: Int) {
        timerTextView.text = "00:${seconds.toString().padStart(2, '0')}"
        val resendAvailable = timerLogic.isResendAvailable(seconds)
        resendTextView.isEnabled = resendAvailable
        resendTextView.alpha = if (resendAvailable) ENABLED_ALPHA else DISABLED_ALPHA
    }

    private companion object {
        const val DEMO_OTP = "1234"
        const val OTP_LENGTH = 4
        const val ONE_SECOND_MS = 1000L
        const val ENABLED_ALPHA = 1.0f
        const val DISABLED_ALPHA = 0.5f
    }
}
