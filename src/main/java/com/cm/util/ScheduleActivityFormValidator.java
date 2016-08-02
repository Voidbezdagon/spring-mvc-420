package com.cm.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cm.entity.ScheduleActivity;

public class ScheduleActivityFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return ScheduleActivity.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "description.required");
		
	}
}
