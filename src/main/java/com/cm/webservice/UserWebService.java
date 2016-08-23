package com.cm.webservice;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;

import com.cm.entity.User;
import com.cm.service.PositionService;
import com.cm.service.UserService;
import com.cm.util.UserFormValidator;

@RestController
@EnableWebMvc
public class UserWebService extends BaseWebService<User>{
	 
	@Autowired
	PositionService pService;
	
	@Autowired
	UserService uService;
	
	@Autowired
	@Qualifier("userValidator")
	private UserFormValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/User/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User item) throws InstantiationException, IllegalAccessException
	{
		User user = uService.getUserByUnamePwd(item.getUsername(), item.getPassword());
		if (user == null)
			return new ResponseEntity<User>(item, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/User/getAll", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<User>> getAllUsers(@RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		return getAll();
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/User/save", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@Valid @RequestBody User item, BindingResult bindingResult) throws InstantiationException, IllegalAccessException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<User>(item, HttpStatus.BAD_REQUEST);
		}
		
		if (item.getId() != null)
		{
			User user = uService.getById(item.getId());
			item.setAccesskey(user.getAccesskey());
			item.setTeams(user.getTeams());
		}
		else
		{
			User user = null;
			SecureRandom random = null;
			do {
				user = null;
				random = new SecureRandom();
				user = uService.getUserByKey(new BigInteger(130, random).toString(32));
			}
			while (user != null);
			item.setAccesskey(new BigInteger(130, random).toString(32));
		}
		
		item.setPosition(pService.getById(item.getPosition().getId()));
		item.setAvatar("/home/void/workspace/Content Management/upload/default_avatar.png");
		
		try {
			return save(item);
		} catch (Exception e) {
			System.out.println("Duplicate Name");
			return new ResponseEntity<User>(item, HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/User/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{
		return getById(id);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/User/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{
		User user = uService.getById(id);

		if (!user.getAvatar().equals("/home/void/workspace/Content Management/upload/default_avatar.png"))
        {
        	File oldFile = new File(user.getAvatar());
        	oldFile.delete();
        }
		
		return delete(id);
	}
}
