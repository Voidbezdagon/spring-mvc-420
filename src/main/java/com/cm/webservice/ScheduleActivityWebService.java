package com.cm.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleReport;
import com.cm.entity.User;
import com.cm.service.UserService;
import com.cm.util.ScheduleActivityFormValidator;

@RestController
@EnableWebMvc
public class ScheduleActivityWebService extends BaseWebService<ScheduleActivity>{
	
	@Autowired
	UserService uService;
	
	@Autowired
	@Qualifier("scheduleActivityValidator")
	private ScheduleActivityFormValidator validator;
	
	@InitBinder("item")
	private void initBinder1(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivity/getAll/", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<ScheduleActivity>> getAllScheduleActivitys(@RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{	
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					return getAll();
				}
			}
		}
		return new ResponseEntity<List<ScheduleActivity>>(new ArrayList<ScheduleActivity>(), HttpStatus.FORBIDDEN);
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivity/save", method = RequestMethod.POST)
	public ResponseEntity<ScheduleActivity> createScheduleActivity(@Valid @RequestBody ScheduleActivity item, BindingResult bindingResult, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<ScheduleActivity>(item, HttpStatus.BAD_REQUEST);
		}
		
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					return save(item);
				}
			}
		}
		return new ResponseEntity<ScheduleActivity>(new ScheduleActivity(), HttpStatus.FORBIDDEN);
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivity/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<ScheduleActivity> getScheduleActivityById(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					return getById(id);
				}
			}
		}
		return new ResponseEntity<ScheduleActivity>(new ScheduleActivity(), HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivity/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ScheduleActivity> deleteScheduleActivity(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					return delete(id);
				}
			}
		}
		return new ResponseEntity<ScheduleActivity>(new ScheduleActivity(), HttpStatus.FORBIDDEN);
	}

}
