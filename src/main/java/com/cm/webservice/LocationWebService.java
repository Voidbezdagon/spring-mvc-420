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

import com.cm.entity.Location;
import com.cm.entity.LocationItem;
import com.cm.entity.Position;
import com.cm.entity.Schedule;
import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleReport;
import com.cm.entity.User;
import com.cm.service.LocationItemService;
import com.cm.service.LocationService;
import com.cm.service.ScheduleActivityService;
import com.cm.service.ScheduleReportService;
import com.cm.service.ScheduleService;
import com.cm.service.UserService;
import com.cm.util.LocationFormValidator;

@RestController
@EnableWebMvc
public class LocationWebService extends BaseWebService<Location>{

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
	@Qualifier("locationValidator")
	private LocationFormValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Location/getAll", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Location>> getAllLocations(@RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
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
		return new ResponseEntity<List<Location>>(new ArrayList<Location>(), HttpStatus.FORBIDDEN);
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/Location/save", method = RequestMethod.POST)
	public ResponseEntity<Location> createLocation(@Valid @RequestBody Location item, BindingResult bindingResult, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<Location>(item, HttpStatus.BAD_REQUEST);
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
						return new ResponseEntity<Location>(item, HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<Location>(new Location(), HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Location/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<Location> getLocationById(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
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
		return new ResponseEntity<Location>(new Location(), HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Location/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Location> deleteLocation(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					for (LocationItem li : lService.getById(id).getLocationItems())
						liService.delete(li.getId());
					
					for (Schedule s : lService.getById(id).getSchedules())
					{
						for (ScheduleActivity sa : s.getActivities())
							saService.delete(sa.getId());
						
						for (ScheduleReport sr : s.getReports())
							srService.delete(sr.getId());
						
						sService.delete(s.getId());
					}
					
					return delete(id);
				}
			}
		}
		return new ResponseEntity<Location>(new Location(), HttpStatus.FORBIDDEN);
	}
}
