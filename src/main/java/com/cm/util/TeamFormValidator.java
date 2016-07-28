package com.cm.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cm.entity.Team;

public class TeamFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> paramClass) {
		return Team.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "teamname", "teamname.required");
		
		Team item = (Team) obj;
		if (!item.getTeamname().matches("[a-zA-Z ]+"))
			err.rejectValue("teamname", "teamname.invalid");
	}
}
