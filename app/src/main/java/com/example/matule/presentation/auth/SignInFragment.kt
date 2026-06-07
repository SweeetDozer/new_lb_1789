package com.example.matule.presentation.auth

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.validation.SignInValidationResult
import com.example.matule.common.validation.SignInValidator

/**
 * Purpose: Shows Sprint 1 Sign In screen with local validation and mock login.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class SignInFragment : Fragment() {

    private val signInValidator = SignInValidator()
    private var isPasswordVisible = false

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordToggleButton: ImageButton
    private lateinit var signInButton: Button
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var createUserTextView: TextView

    /**
     * Purpose: Creates Sign In XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_sign_in, container, false)

    /**
     * Purpose: Prepares form fields and click handlers.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupClicks()
    }

    /**
     * Purpose: Finds Sign In views from XML.
     */
    private fun bindViews(view: View) {
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        passwordToggleButton = view.findViewById(R.id.passwordToggleButton)
        signInButton = view.findViewById(R.id.signInButton)
        forgotPasswordTextView = view.findViewById(R.id.forgotPasswordTextView)
        createUserTextView = view.findViewById(R.id.createUserTextView)
    }

    /**
     * Purpose: Handles Sign In, password visibility, and Sprint 2 navigation.
     */
    private fun setupClicks() {
        signInButton.setOnClickListener { validateAndOpenHome() }
        passwordToggleButton.setOnClickListener { togglePasswordVisibility() }
        forgotPasswordTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }
        createUserTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    /**
     * Purpose: Validates fields and opens Home after successful mock login.
     */
    private fun validateAndOpenHome() {
        val email = emailEditText.text?.toString().orEmpty()
        val password = passwordEditText.text?.toString().orEmpty()

        when (val result = signInValidator.validate(email, password)) {
            is SignInValidationResult.Error -> showErrorDialog(toRussianMessage(result.message))
            SignInValidationResult.Success -> {
                Toast.makeText(requireContext(), R.string.mock_login_success, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            }
        }
    }

    /**
     * Purpose: Maps test-compatible validator messages to Russian UI messages.
     */
    private fun toRussianMessage(message: String): String {
        return when (message) {
            EMAIL_REQUIRED_ERROR -> "Введите email"
            PASSWORD_REQUIRED_ERROR -> "Введите пароль"
            INVALID_EMAIL_ERROR -> "Некорректный email"
            else -> message
        }
    }

    /**
     * Purpose: Switches password field between hidden and visible modes.
     */
    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        passwordEditText.inputType = if (isPasswordVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        passwordEditText.setSelection(passwordEditText.text?.length ?: 0)
    }

    /**
     * Purpose: Shows validation message in a user-closed dialog.
     */
    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.validation_error_title)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private companion object {
        const val EMAIL_REQUIRED_ERROR = "Email is required"
        const val PASSWORD_REQUIRED_ERROR = "Password is required"
        const val INVALID_EMAIL_ERROR = "Email format is invalid"
    }
}
