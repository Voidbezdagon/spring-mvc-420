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
import com.cm.entity.User;
import com.cm.service.LocationService;
import com.cm.service.UserService;
import com.cm.util.LocationItemValidator;

@RestController
@EnableWebMvc
public class LocationItemWebService extends BaseWebService<LocationItem>{
	
	@Autowired
	UserService uService;
	
	@Autowired
	LocationService lService;
	
	@Autowired
	@Qualifier("locationItemValidator")
	private LocationItemValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/LocationItem/getAll/{id}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<LocationItem>> getAllLocationItems(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{	
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					Location location = lService.getById(id);
					List<LocationItem> locationItems = location.getLocationItems();
					return new ResponseEntity<List<LocationItem>>(locationItems, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<List<LocationItem>>(new ArrayList<LocationItem>(), HttpStatus.FORBIDDEN);
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/LocationItem/save", method = RequestMethod.POST)
	public ResponseEntity<LocationItem> createLocationItem(@Valid @RequestBody LocationItem item, BindingResult bindingResult, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<LocationItem>(item, HttpStatus.BAD_REQUEST);
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
						return new ResponseEntity<LocationItem>(item, HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<LocationItem>(new LocationItem(), HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/LocationItem/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<LocationItem> getLocationItemById(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
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
		return new ResponseEntity<LocationItem>(new LocationItem(), HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/LocationItem/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<LocationItem> deleteLocationItem(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
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
		return new ResponseEntity<LocationItem>(new LocationItem(), HttpStatus.FORBIDDEN);
	}
}
