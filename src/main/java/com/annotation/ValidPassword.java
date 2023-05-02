package com.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, ANNOTATION_TYPE })
public @interface ValidPassword {
	
	String message() default "Invalid password";
	
	Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
