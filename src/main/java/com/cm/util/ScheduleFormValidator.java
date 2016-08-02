package com.cm.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cm.entity.Schedule;

public class ScheduleFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return Schedule.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "description.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "title", "title.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "recurringTime", "recurringTime.required");
		
		Schedule item = (Schedule) obj;
		if (!item.getTitle().matches("[a-zA-Z ]+"))
			err.rejectValue("title", "scheduleTitle.invalid");
	}
}
