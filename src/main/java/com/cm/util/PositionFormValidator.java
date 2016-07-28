package com.cm.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cm.entity.Position;
import com.cm.entity.User;

public class PositionFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> paramClass) {
		return Position.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "name.required");
		
		Position item = (Position) obj;
		if (!item.getName().matches("[a-zA-Z ]+"))
			err.rejectValue("name", "name.invalid");
	}
}
