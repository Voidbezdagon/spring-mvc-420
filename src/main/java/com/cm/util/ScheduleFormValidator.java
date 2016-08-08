package com.cm.util;

import java.util.Date;

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
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "startDate", "startDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "endDate", "endDate.required");
		
		Schedule item = (Schedule) obj;
		Date now = new Date();
		if (!item.getTitle().matches("[a-zA-Z ]+"))
			err.rejectValue("title", "scheduleTitle.invalid");
		if (item.getStartDate() != null)
			if (item.getStartDate().getTime() < now.getTime())
				err.rejectValue("startDate", "startDate.past");
		if (item.getStartDate() != null && item.getEndDate() != null)
			if (item.getEndDate().getTime() < item.getStartDate().getTime())
				err.rejectValue("endDate", "endDate.past");
		
	}
}
