package com.cm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cm.entity.User;
import com.cm.service.UserService;

@Component
public class TeamMemberConverter implements Converter<Object, User>{

	@Autowired
    UserService userService;
	
	@Override
	public User convert(Object element) {
		Long id = Long.parseLong((String)element);
        User user = new User();
		try {
			user = userService.getById(id);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return user;
	}

}
