package com.example.matule.presentation.auth.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.validation.ForgotPasswordValidator
import com.example.matule.common.validation.Sprint2ValidationResult
import com.example.matule.presentation.common.KeyboardHelper
import com.google.android.material.button.MaterialButton

/**
 * Purpose: Shows Sprint 2 forgot-password screen and validates email before OTP.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ForgotPasswordFragment : Fragment() {

    private val forgotPasswordValidator = ForgotPasswordValidator()

    private lateinit var emailEditText: EditText
    private lateinit var sendButton: MaterialButton
    private lateinit var backButton: TextView

    /**
     * Purpose: Creates Forgot Password XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_forgot_password, container, false)

    /**
     * Purpose: Finds views and prepares click handlers.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailEditText = view.findViewById(R.id.forgotEmailEditText)
        sendButton = view.findViewById(R.id.forgotSendButton)
        backButton = view.findViewById(R.id.forgotBackButton)

        sendButton.setOnClickListener { validateAndShowEmailDialog() }
        backButton.setOnClickListener { findNavController().navigateUp() }
    }

    private fun validateAndShowEmailDialog() {
        when (val result = forgotPasswordValidator.validate(emailEditText.text?.toString().orEmpty())) {
            is Sprint2ValidationResult.Error -> showErrorDialog(toRussianMessage(result.message))
            Sprint2ValidationResult.Success -> showEmailSentDialog()
        }
    }

    private fun showEmailSentDialog() {
        KeyboardHelper.hideKeyboard(this)
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.forgot_password_dialog_title)
            .setMessage(R.string.forgot_password_dialog_message)
            .setPositiveButton(R.string.dialog_ok) { _, _ ->
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_otpVerificationFragment)
            }
            .show()
    }

    private fun showErrorDialog(message: String) {
        KeyboardHelper.hideKeyboard(this)
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.validation_error_title)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private fun toRussianMessage(message: String): String {
        return when (message) {
            "Email is required" -> "Введите email"
            "Email format is invalid" -> "Некорректный email"
            else -> message
        }
    }
}
