package com.cm.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cm.entity.User;

public class UserFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> paramClass) {
		return User.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "firstname", "firstname.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "lastname", "lastname.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "username", "username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "password", "password.required");
		
		User user = (User) obj;
		if (!user.getFirstname().matches("[a-zA-Z]+"))
			err.rejectValue("firstname", "firstname.invalid");
		if (!user.getLastname().matches("[a-zA-Z]+"))
			err.rejectValue("lastname", "lastname.invalid");
		if (!user.getUsername().matches("[a-zA-Z0-9_-]+"))
			err.rejectValue("username", "username.invalid");
	}
}
