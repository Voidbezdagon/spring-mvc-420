package com.cm.repository;

import java.util.ArrayList;
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
}
