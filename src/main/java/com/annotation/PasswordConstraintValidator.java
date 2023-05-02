package com.annotation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = getPasswordValidator();
		RuleResult result = validator.validate(new PasswordData(password));
		if (result.isValid())
			return true;
		List<String> errorMessages = validator.getMessages(result);
		String messageTemplate = errorMessages.stream().collect(Collectors.joining(","));
		context.buildConstraintViolationWithTemplate(messageTemplate)
			.addConstraintViolation()
			.disableDefaultConstraintViolation();
		return false;
	}
	
	public PasswordValidator getPasswordValidator() {
		return new PasswordValidator(Arrays.asList(
				new LengthRule(8, 30),
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				new CharacterRule(EnglishCharacterData.Digit, 1),
				new WhitespaceRule()));
	}

}
