package com.example.demo;

import jakarta.validation.ConstraintValidator; // Import for creating a custom validator
import jakarta.validation.ConstraintValidatorContext; // Import for context during validation

public class VersionValidator implements ConstraintValidator<ValidAppVersion, String> {

    /**
     * Initializes the validator.
     * (Not used in this implementation, but can be overridden for setup logic if needed.)
     */
    @Override
    public void initialize(ValidAppVersion constraintAnnotation) {
        // Any initialization logic can be placed here, if needed.
    }

    /**
     * Validates the version string.
     * @param version The version string to validate.
     * @param context Provides contextual data and operation when checking the constraint.
     * @return true if the version is valid, false otherwise.
     */
    @Override
    public boolean isValid(String version, ConstraintValidatorContext context) {
        // Allow null or empty versions (could be adjusted based on requirements)
        if (version == null || version.isEmpty()) {
            return true; // If the version is null or empty, consider it valid
        }
        // Check if the version length is exactly 3
        return version.length() == 3; // Return true if the version length is 3; otherwise, return false.
    }
}
