package com.cm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cm.entity.User;

@Repository
public class UserRepository extends BaseRepository<User>{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public User getUserByUnamePwd(String uname, String pwd) throws InstantiationException, IllegalAccessException
	{
		ArrayList<User> result = new ArrayList<User>();
		result = (ArrayList<User>) getAll().stream()
				.filter(p -> p.getUsername().equals(uname))
				.filter(p -> p.getPassword().equals(pwd))
				.collect(Collectors.toList());
		
		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}
	
	public User getUserByUname(String uname) throws InstantiationException, IllegalAccessException
	{
		ArrayList<User> result = new ArrayList<User>();
		result = (ArrayList<User>) getAll().stream()
				.filter(p -> p.getUsername().equals(uname))
				.collect(Collectors.toList());
		
		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}

	@Override
	public List<User> getAllByString(String column, String searchString) throws Exception {
		ArrayList<User> result = new ArrayList<User>();
		switch (column)
		{
		case "First Name":
			result = (ArrayList<User>) getAll().stream().filter(p -> p.getFirstname().contains(searchString)).collect(Collectors.toList());
			break;
		case "Last Name":
			result = (ArrayList<User>) getAll().stream().filter(p -> p.getLastname().contains(searchString)).collect(Collectors.toList());
			break;
		case "Username":
			result = (ArrayList<User>) getAll().stream().filter(p -> p.getUsername().contains(searchString)).collect(Collectors.toList());
			break;
		case "Password":
			result = (ArrayList<User>) getAll().stream().filter(p -> p.getPassword().contains(searchString)).collect(Collectors.toList());
			break;
		case "Admin":
			if (searchString.equalsIgnoreCase("true"))
				result = (ArrayList<User>) getAll().stream().filter(p -> p.getAdmin() == true).collect(Collectors.toList());
			else
				result = (ArrayList<User>) getAll().stream().filter(p -> p.getAdmin() == false).collect(Collectors.toList());
		}	
		return result;
	}
}
