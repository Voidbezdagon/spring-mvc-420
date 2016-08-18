package com.cm.webservice;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;

import com.cm.entity.Position;
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
	@RequestMapping(value = "/api/User/getAll", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<User>> getAllUsers() throws InstantiationException, IllegalAccessException
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
			item.setTeams(user.getTeams());
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
