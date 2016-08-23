package com.cm.webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.cm.entity.Position;
import com.cm.entity.ScheduleActivityReport;
import com.cm.entity.User;
import com.cm.service.PositionService;
import com.cm.service.UserService;
import com.cm.util.PositionFormValidator;

@RestController
@EnableWebMvc
public class PositionWebService extends BaseWebService<Position>{

	@Autowired
	PositionService pService;
	
	@Autowired
	UserService uService;
	
	@Autowired
	@Qualifier("positionValidator")
	private PositionFormValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Position/getAll", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Position>> getAllPositions(@RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
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
		return new ResponseEntity<List<Position>>(new ArrayList<Position>(), HttpStatus.FORBIDDEN);
	}
	 
	@CrossOrigin
	@RequestMapping(value = "/api/Position/save", method = RequestMethod.POST)
	public ResponseEntity<Position> createPosition(@Valid @RequestBody Position item, BindingResult bindingResult, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (bindingResult.hasErrors()) {
			System.out.println("deba maznata pi6ka");	
			return new ResponseEntity<Position>(item, HttpStatus.BAD_REQUEST);
		}
		
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					if (item.getParentId() == 0)
						item.setLevel((long) 0);
					else
						item.setLevel(pService.getById(item.getParentId()).getLevel() + 1);
					
					try 
					{
						return save(item);
					} 
					catch (Exception e) 
					{
						System.out.println("Duplicate Name");
						return new ResponseEntity<Position>(item, HttpStatus.BAD_REQUEST);
					}
				}
			}
		}
		return new ResponseEntity<Position>(new Position(), HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Position/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<Position> getPositionById(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
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
		return new ResponseEntity<Position>(new Position(), HttpStatus.FORBIDDEN);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/Position/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Position> deletePosition(@PathVariable("id") long id, @RequestHeader("access-key") String key) throws InstantiationException, IllegalAccessException
	{
		if (key != null)
		{
			User user = uService.getUserByKey(key);
			if (user != null)
			{
				if (user.getAdmin() == true)
				{
					List<Position> positions = pService.getAll();
					for (Position i : positions)
					{
						if (i.getParentId().equals(id))
						{
							i.setParentId((long) 0);
							pService.update(i);
						}
					}		

					Position pos = pService.getById(id);
					for (User u : pos.getUsers())
					{
						u.setPosition(null);
						uService.update(u);
					}
					
					return delete(id);
				}
			}
		}
		return new ResponseEntity<Position>(new Position(), HttpStatus.FORBIDDEN);
	}
}
