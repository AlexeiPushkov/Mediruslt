package com.diplom.mediresult.domain.use_cases

import android.util.Patterns
import com.diplom.mediresult.domain.use_cases.ValidationResult

class ValidateEmail{
    fun execute(email: String): ValidationResult {
        // Ensure email is trimmed to avoid issues with leading/trailing spaces
        val trimmedEmail = email.trim()

        // Check if email is blank
        if (trimmedEmail.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }

        // Check for maximum length (254 characters as per standards)
        if (email.trim().length > 254) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email must not exceed 254 characters"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter a valid email"
            )
        }

        return ValidationResult(successful = true)
    }
}