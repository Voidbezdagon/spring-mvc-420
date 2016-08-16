package com.cm.webservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cm.entity.Guz;
import com.cm.entity.Schedule;
import com.cm.entity.Team;
import com.cm.entity.User;
import com.cm.service.GuzService;
import com.cm.service.ScheduleService;
import com.cm.service.UserService;

@RestController
@EnableWebMvc
public class UserWebService {
	@Autowired
    UserService userService;
	
	@Autowired
    GuzService guzService;
	
	@Autowired
    ScheduleService scheduleService;
	
	 @RequestMapping(value = "/Noob/", method = RequestMethod.GET, produces="application/json")
	 public ResponseEntity<List<User>> getAllUsers() throws InstantiationException, IllegalAccessException 
	 {
		 List<User> users = userService.getAll();
		 if(users.isEmpty())
			 return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		 return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	 }
	 
	 @RequestMapping(value = "/ebane/", method = RequestMethod.GET, produces="application/json")
	 public Guz porno()
	 {
		Guz guz = new Guz();
		guz.setId((long) 1);
		guz.setName("Golqm Motor");
		return guz;
	 }
	 
	 @RequestMapping(value = "/ebanee/", method = RequestMethod.GET, produces="application/json")
	 public List<User> pornoo() throws InstantiationException, IllegalAccessException
	 {
		User guz2 = userService.getById(4);
		User guza = new User();
		User guz = new User();
		guz.setUsername(guz2.getUsername());
		guza.setUsername(guz2.getPassword());
		List<User> guze = new ArrayList<User>();
		guze = userService.getAll();
		guze.add(guz);
		guze.add(guza);
		return guze;
	 }
	 
	 @RequestMapping(value = "/ebaneee/", method = RequestMethod.GET, produces="application/json")
	 public List<Schedule> pornoooo() throws InstantiationException, IllegalAccessException
	 {
		return scheduleService.getAll();
	 }
}
