package com.example.matule.presentation.auth.signup

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.validation.SignUpValidator
import com.example.matule.common.validation.Sprint2ValidationResult
import com.google.android.material.button.MaterialButton

/**
 * Purpose: Shows Sprint 2 registration screen and validates local sign-up fields.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class SignUpFragment : Fragment() {

    private val signUpValidator = SignUpValidator()
    private var isPasswordVisible = false

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordToggleButton: ImageButton
    private lateinit var agreementCheckBox: CheckBox
    private lateinit var signUpButton: MaterialButton
    private lateinit var signInLinkTextView: TextView
    private lateinit var privacyPolicyTextView: TextView
    private lateinit var backButton: TextView

    /**
     * Purpose: Creates Sign Up XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_sign_up, container, false)

    /**
     * Purpose: Finds views and attaches click handlers.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupClicks()
    }

    private fun bindViews(view: View) {
        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.signUpEmailEditText)
        passwordEditText = view.findViewById(R.id.signUpPasswordEditText)
        passwordToggleButton = view.findViewById(R.id.signUpPasswordToggleButton)
        agreementCheckBox = view.findViewById(R.id.agreementCheckBox)
        signUpButton = view.findViewById(R.id.signUpButton)
        signInLinkTextView = view.findViewById(R.id.signInLinkTextView)
        privacyPolicyTextView = view.findViewById(R.id.privacyPolicyTextView)
        backButton = view.findViewById(R.id.signUpBackButton)
    }

    /**
     * Purpose: Handles registration, navigation, password visibility, and policy dialog.
     */
    private fun setupClicks() {
        signUpButton.setOnClickListener { validateAndOpenHome() }
        passwordToggleButton.setOnClickListener { togglePasswordVisibility() }
        signInLinkTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        backButton.setOnClickListener { findNavController().navigateUp() }
        privacyPolicyTextView.setOnClickListener { showPrivacyPolicyDialog() }
    }

    private fun validateAndOpenHome() {
        val result = signUpValidator.validate(
            name = nameEditText.text?.toString().orEmpty(),
            email = emailEditText.text?.toString().orEmpty(),
            password = passwordEditText.text?.toString().orEmpty(),
            isAgreementChecked = agreementCheckBox.isChecked
        )

        when (result) {
            is Sprint2ValidationResult.Error -> showErrorDialog(toRussianMessage(result.message))
            Sprint2ValidationResult.Success -> findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        passwordEditText.inputType = if (isPasswordVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        passwordEditText.setSelection(passwordEditText.text?.length ?: 0)
    }

    private fun showPrivacyPolicyDialog() {
        // TODO: replace with real PDF policy file if provided.
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.privacy_policy_title)
            .setMessage(R.string.privacy_policy_message)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.validation_error_title)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private fun toRussianMessage(message: String): String {
        return when (message) {
            "Name is required" -> "Введите имя"
            "Email is required" -> "Введите email"
            "Email format is invalid" -> "Некорректный email"
            "Password is required" -> "Введите пароль"
            "Agreement is required" -> "Необходимо согласие с условиями"
            else -> message
        }
    }
}
