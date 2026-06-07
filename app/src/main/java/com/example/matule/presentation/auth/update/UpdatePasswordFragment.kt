package com.example.matule.presentation.auth.update

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.validation.CaptchaValidator
import com.example.matule.common.validation.PasswordStrength
import com.example.matule.common.validation.PasswordStrengthCalculator
import com.example.matule.common.validation.Sprint2ValidationResult
import com.example.matule.common.validation.UpdatePasswordValidator
import com.example.matule.presentation.common.KeyboardHelper
import com.google.android.material.button.MaterialButton

/**
 * Purpose: Shows Sprint 2 update-password screen with strength and CAPTCHA checks.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class UpdatePasswordFragment : Fragment() {

    private val strengthCalculator = PasswordStrengthCalculator()
    private val captchaValidator = CaptchaValidator(EXPECTED_CAPTCHA)
    private val updatePasswordValidator = UpdatePasswordValidator(captchaValidator = captchaValidator)

    private var isPasswordVisible = false
    private var isRepeatPasswordVisible = false

    private lateinit var newPasswordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var captchaEditText: EditText
    private lateinit var strengthTextView: TextView
    private lateinit var saveButton: MaterialButton
    private lateinit var newPasswordToggleButton: ImageButton
    private lateinit var repeatPasswordToggleButton: ImageButton
    private lateinit var backButton: TextView

    /**
     * Purpose: Creates Update Password XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_update_password, container, false)

    /**
     * Purpose: Prepares password fields, strength text, CAPTCHA, and save action.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupClicks()
        setupWatchers()
        updateFormState()
    }

    private fun bindViews(view: View) {
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText)
        repeatPasswordEditText = view.findViewById(R.id.repeatPasswordEditText)
        captchaEditText = view.findViewById(R.id.captchaEditText)
        strengthTextView = view.findViewById(R.id.passwordStrengthTextView)
        saveButton = view.findViewById(R.id.savePasswordButton)
        newPasswordToggleButton = view.findViewById(R.id.newPasswordToggleButton)
        repeatPasswordToggleButton = view.findViewById(R.id.repeatPasswordToggleButton)
        backButton = view.findViewById(R.id.updateBackButton)
    }

    private fun setupClicks() {
        backButton.setOnClickListener { findNavController().navigateUp() }
        saveButton.setOnClickListener { validateAndOpenHome() }
        newPasswordToggleButton.setOnClickListener { toggleNewPasswordVisibility() }
        repeatPasswordToggleButton.setOnClickListener { toggleRepeatPasswordVisibility() }
    }

    private fun setupWatchers() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) = updateFormState()
        }
        newPasswordEditText.addTextChangedListener(watcher)
        repeatPasswordEditText.addTextChangedListener(watcher)
        captchaEditText.addTextChangedListener(watcher)
    }

    private fun updateFormState() {
        renderStrength(newPasswordEditText.text?.toString().orEmpty())
        saveButton.isEnabled = isCurrentFormValid()
        renderSaveButton()
    }

    private fun renderStrength(password: String) {
        when (strengthCalculator.calculate(password)) {
            PasswordStrength.EMPTY,
            PasswordStrength.WEAK -> {
                strengthTextView.setText(R.string.update_password_strength_weak)
                strengthTextView.setTextColor(requireContext().getColor(R.color.matule_error))
            }
            PasswordStrength.MEDIUM -> {
                strengthTextView.setText(R.string.update_password_strength_medium)
                strengthTextView.setTextColor(requireContext().getColor(R.color.matule_text_gray))
            }
            PasswordStrength.STRONG -> {
                strengthTextView.setText(R.string.update_password_strength_strong)
                strengthTextView.setTextColor(requireContext().getColor(R.color.matule_success))
            }
        }
    }

    private fun isCurrentFormValid(): Boolean {
        return updatePasswordValidator.validate(
            password = newPasswordEditText.text?.toString().orEmpty(),
            repeatPassword = repeatPasswordEditText.text?.toString().orEmpty(),
            captchaInput = captchaEditText.text?.toString().orEmpty()
        ) is Sprint2ValidationResult.Success
    }

    private fun validateAndOpenHome() {
        when (val result = updatePasswordValidator.validate(
            password = newPasswordEditText.text?.toString().orEmpty(),
            repeatPassword = repeatPasswordEditText.text?.toString().orEmpty(),
            captchaInput = captchaEditText.text?.toString().orEmpty()
        )) {
            is Sprint2ValidationResult.Error -> showErrorDialog(toRussianMessage(result.message))
            Sprint2ValidationResult.Success -> {
                KeyboardHelper.hideKeyboard(this)
                findNavController().navigate(R.id.action_updatePasswordFragment_to_homeFragment)
            }
        }
    }

    private fun renderSaveButton() {
        val buttonColor = if (saveButton.isEnabled) {
            requireContext().getColor(R.color.matule_primary)
        } else {
            requireContext().getColor(R.color.matule_primary_disabled)
        }
        saveButton.backgroundTintList = ColorStateList.valueOf(buttonColor)
        saveButton.alpha = ENABLED_ALPHA
    }

    private fun toggleNewPasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        applyPasswordInputType(newPasswordEditText, isPasswordVisible)
    }

    private fun toggleRepeatPasswordVisibility() {
        isRepeatPasswordVisible = !isRepeatPasswordVisible
        applyPasswordInputType(repeatPasswordEditText, isRepeatPasswordVisible)
    }

    private fun applyPasswordInputType(editText: EditText, isVisible: Boolean) {
        editText.inputType = if (isVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        editText.setSelection(editText.text?.length ?: 0)
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
            "Password is required" -> "Введите пароль"
            "Password is invalid" -> "Пароль не соответствует требованиям"
            "Repeat password is required" -> "Повторите пароль"
            "Passwords do not match" -> "Пароли не совпадают"
            "Captcha is required" -> "Введите CAPTCHA"
            "Captcha is invalid" -> "Некорректная CAPTCHA"
            else -> message
        }
    }

    private companion object {
        const val EXPECTED_CAPTCHA = "A7K9"
        const val ENABLED_ALPHA = 1.0f
    }
}
