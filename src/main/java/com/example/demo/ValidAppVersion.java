package com.example.demo;

import jakarta.validation.Constraint; // Import for defining a custom validation constraint
import jakarta.validation.Payload; // Import for carrying metadata (optional)
import java.lang.annotation.ElementType; // Import for defining where the annotation can be applied
import java.lang.annotation.Retention; // Import for defining the retention policy
import java.lang.annotation.RetentionPolicy; // Import for runtime retention policy
import java.lang.annotation.Target; // Import for specifying the target of the annotation

/**
 * Custom annotation to validate the application version.
 */
@Constraint(validatedBy = VersionValidator.class) // Indicates that the validation logic is handled by the VersionValidator class.
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE }) // Specifies where this annotation can be applied (fields, methods, parameters, or other annotations).
@Retention(RetentionPolicy.RUNTIME) // The annotation will be available at runtime.
public @interface ValidAppVersion {
    String message() default "Invalid app version"; // Default error message if validation fails.

    Class<?>[] groups() default {}; // Used for grouping constraints (optional).

    Class<? extends Payload>[] payload() default {}; // Can carry additional metadata (optional).
}
