package com.cm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.Location;
import com.cm.entity.User;
import com.cm.service.LocationService;
import com.cm.service.UserService;

@Controller
public class LocationController extends BaseController<Location>{

	@Autowired
	private LocationService locationService;
	
	/*@Autowired
	@Qualifier("teamValidator")
	private LocationFormValidator validator;

	@InitBinder("item")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}*/
	
	//START BASE CONTROLLER
	
	@RequestMapping(value="Location/create")
	public ModelAndView createLocation(HttpServletRequest request) throws Exception
	{
		return create(request);
	}
	
	@RequestMapping(value="Location/edit")
	public ModelAndView editLocation(HttpServletRequest request) throws Exception
	{
		return edit(request);
	}
	
	@RequestMapping(value="Location/delete")
	public ModelAndView deleteLocation(HttpServletRequest request) throws Exception
	{
		return delete(request);
	}
	
	@RequestMapping(value="Location/save")
	public ModelAndView saveLocation(@ModelAttribute("item") Location item, BindingResult bindingResult, @RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request) throws Exception
	{	
		/*if (bindingResult.hasErrors()) {
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}*/
		
		/*List<User> noob = new ArrayList<User>();
		for (User user : item.getUsers())
		{
			if (user.getId() != null)
				noob.add(userService.getById(user.getId()));
		}
		
		item.setUsers(noob);*/
		return save(item, request);
	}
	
	@RequestMapping(value = "Location/getAll")
	public ModelAndView getAllLocations(HttpServletRequest request) throws Exception
	{
		return getAll(request);
	}
	
	@Override
	public void feedSort(List<Location> List, String column) {
		switch (column)
		{
		case "Name": Collections.sort(List, (p1, p2) -> p1.getName().compareTo(p2.getName()));
			break;
		case "Region": Collections.sort(List, (p1, p2) -> p1.getRegion().compareTo(p2.getRegion()));
			break;
		case "City": Collections.sort(List, (p1, p2) -> p1.getCity().compareTo(p2.getCity()));
			break;
		case "ZIP": Collections.sort(List, (p1, p2) -> p1.getZip().compareTo(p2.getZip()));
			break;
		case "Street": Collections.sort(List, (p1, p2) -> p1.getStreet().compareTo(p2.getStreet()));
			break;
		case "Street Number": Collections.sort(List, (p1, p2) -> p1.getStreetNumber().compareTo(p2.getStreetNumber()));
			break;
		case "Details": Collections.sort(List, (p1, p2) -> p1.getDetails().compareTo(p2.getDetails()));
			break;
		}	
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		Map.put("name", "Name");
		Map.put("region", "Region");
		Map.put("city", "City");
		Map.put("zip", "ZIP");
		Map.put("street", "Street");
		Map.put("streetNumber", "Street Number");
		Map.put("details", "Details");
	}
	
	@Override
	public void setAvatars(List<Location> List) {
	}
	//END BASE CONTROLLER
	
	public LinkedHashMap<String, String> feedLocationList() throws Exception
	{
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		List<Location> list = locationService.getAll();
		for (Location item: list)
		{
			//result.put(item.getId().toString(), item.getLocationname());
		}
		return null;
	}

}