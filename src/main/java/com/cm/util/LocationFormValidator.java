package com.cm.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cm.entity.Location;

public class LocationFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return Location.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "name.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "region", "locationRegion.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "city", "city.required");

		ValidationUtils.rejectIfEmptyOrWhitespace(err, "zip", "zip.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "street", "street.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "streetNumber", "streetNumber.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "details", "details.required");
		
		Location item = (Location) obj;
		if (!item.getName().matches("[a-zA-Z ]+"))
			err.rejectValue("name", "name.invalid");
		if (!item.getStreet().matches("[a-zA-Z ]+"))
			err.rejectValue("street", "street.invalid");
		if (!item.getCity().matches("[a-zA-Z]+"))
			err.rejectValue("city", "city.invalid");
		if (!item.getRegion().matches("[a-zA-Z]+"))
			err.rejectValue("region", "region.invalid");
	}
}
