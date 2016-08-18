package com.cm.webservice;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cm.entity.ScheduleActivity;
import com.cm.util.ScheduleActivityFormValidator;

@RestController
@EnableWebMvc
public class ScheduleActivityWebService extends BaseWebService<ScheduleActivity>{
	
	@Autowired
	@Qualifier("scheduleActivityValidator")
	private ScheduleActivityFormValidator validator;
	
	@InitBinder("item")
	private void initBinder1(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivity/getAll", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<ScheduleActivity>> getAllScheduleActivitys(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{	
		return getAll();
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivity/save", method = RequestMethod.POST)
	public ResponseEntity<ScheduleActivity> createScheduleActivity(@Valid @RequestBody ScheduleActivity item, BindingResult bindingResult) throws InstantiationException, IllegalAccessException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<ScheduleActivity>(item, HttpStatus.BAD_REQUEST);
		}
		
		return save(item);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivity/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<ScheduleActivity> getScheduleActivityById(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{
		return getById(id);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/ScheduleActivity/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ScheduleActivity> deleteScheduleActivity(@PathVariable("id") long id) throws InstantiationException, IllegalAccessException
	{
		return delete(id);
	}

}
