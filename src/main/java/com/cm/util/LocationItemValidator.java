package com.cm.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cm.entity.Location;
import com.cm.entity.LocationItem;

public class LocationItemValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return LocationItem.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "name.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "floor", "floor.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "number", "number.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "details", "details.required");
		
		LocationItem item = (LocationItem) obj;
		if (!item.getName().matches("[a-zA-Z]+"))
			err.rejectValue("name", "name.invalid");
	}
}
