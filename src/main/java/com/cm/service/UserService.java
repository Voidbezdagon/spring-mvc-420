package com.cm.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.entity.User;
import com.cm.repository.UserRepository;

@Service
@Transactional
public class UserService extends BaseService<User> {
	@Autowired
	protected UserRepository userRepository;
	
	public User getUserByUnamePwd(String uname, String pwd) throws InstantiationException, IllegalAccessException
	{
		return userRepository.getUserByUnamePwd(uname, pwd);
	}
	
	public User getUserByUname(String uname) throws InstantiationException, IllegalAccessException
	{
		return userRepository.getUserByUname(uname);
	}
}
