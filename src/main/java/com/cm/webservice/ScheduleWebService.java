package com.cm.webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import com.cm.entity.Schedule;
import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleReport;
import com.cm.entity.Team;
import com.cm.entity.User;
import com.cm.service.LocationItemService;
import com.cm.service.LocationService;
import com.cm.service.ScheduleActivityService;
import com.cm.service.ScheduleReportService;
import com.cm.service.ScheduleService;
import com.cm.service.UserService;
import com.cm.util.ScheduleFormValidator;

@RestController
@EnableWebMvc
public class ScheduleWebService extends BaseWebService<Schedule>{
	@Autowired
	UserService uService;
	
	@Autowired
	LocationService lService;
	
	@Autowired
	LocationItemService liService;
	
	@Autowired
	ScheduleService sService;
	
	@Autowired
	ScheduleActivityService saService;
	
	@Autowired
	ScheduleReportService srService;
	
	@Autowired
	@Qualifier("scheduleValidator")
	private ScheduleFormValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		binder.setValidator(validator);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Schedule/getAll/", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Schedule>> getAllSchedules(@RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{	
		//System.out.println(key);
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					return getAll();
				}
				else
				{
					List<Team> teams = user.getTeams();
					List<Schedule> result = new ArrayList<Schedule>();
					
					for (Team team : teams)
					{
						for (Schedule schedule : team.getSchedules())
						{
							if(!result.contains(schedule))
								result.add(schedule);
						}
					}
					return new ResponseEntity<List<Schedule>>(result, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<List<Schedule>>(new ArrayList<Schedule>(), HttpStatus.FORBIDDEN);
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/Schedule/save", method = RequestMethod.POST)
	public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule item, BindingResult bindingResult, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<Schedule>(item, HttpStatus.BAD_REQUEST);
		}
		
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					try 
					{
						return save(item);
					} 
					catch (Exception e) 
					{
						System.out.println("Duplicate Name");
						return new ResponseEntity<Schedule>(item, HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		
		return new ResponseEntity<Schedule>(new Schedule(), HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Schedule/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<Schedule> getScheduleById(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
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
		
		return new ResponseEntity<Schedule>(new Schedule(), HttpStatus.FORBIDDEN);
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Schedule/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Schedule> deleteSchedule(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					Schedule schedule = sService.getById(id);
					
					for (ScheduleActivity sa : schedule.getActivities())
						saService.delete(sa.getId());
					
					for (ScheduleReport sr : schedule.getReports())
						srService.delete(sr.getId());
					
					schedule.setLocation(null);
					sService.update(schedule);
					
					return delete(id);
				}
			}
		}
		return new ResponseEntity<Schedule>(new Schedule(), HttpStatus.FORBIDDEN);
	}
}
