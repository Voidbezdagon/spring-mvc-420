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
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.Location;
import com.cm.entity.LocationItem;
import com.cm.service.LocationItemService;
import com.cm.service.LocationService;
import com.cm.util.LocationItemValidator;
import com.cm.util.ReflectionHelper;

@Controller
public class LocationItemsController extends BaseController<LocationItem> {
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationItemService locationItemService;
	
	@Autowired
	@Qualifier("locationItemValidator")
	private LocationItemValidator validator;
	
	@InitBinder("item")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@Override
	public List<LocationItem> customList(HttpServletRequest request) throws InstantiationException, IllegalAccessException{
		if(request.getParameter("parentId")!=null)
		{
			if (!request.getParameter("parentId").equals(""))
			{
				Long id = Long.parseLong(request.getParameter("parentId"));
				Location location = locationService.getById(id);
				List<LocationItem> locationItems = location.getLocationItems();
				return locationItems;
			}
		}
			
		return null;
	}
	
	@Override
	public List<LocationItem> filterAllByString(String column, String searchName, List<LocationItem> result) {
		switch (column)
		{
		case "Name":
			return (ArrayList<LocationItem>) result.stream().filter(p -> p.getName().contains(searchName)).collect(Collectors.toList());
		case "Floor":
			return (ArrayList<LocationItem>) result.stream().filter(p -> p.getFloor().toString().contains(searchName)).collect(Collectors.toList());
		case "Number":
			return (ArrayList<LocationItem>) result.stream().filter(p -> p.getNumber().toString().contains(searchName)).collect(Collectors.toList());
		case "Details":
			return (ArrayList<LocationItem>) result.stream().filter(p -> p.getDetails().contains(searchName)).collect(Collectors.toList());
		case "Location":
			return (ArrayList<LocationItem>) result.stream().filter(p -> p.getLocation().getName().contains(searchName)).collect(Collectors.toList());
		}
		return result;	
	}
	
	@RequestMapping(value="LocationItem/create")
	public ModelAndView createLocationItem(HttpServletRequest request, @RequestParam Long parentId) throws Exception
	{
		request.getSession().setAttribute("parentId", parentId);
		return create(request);
	}
	
	@RequestMapping(value="LocationItem/edit")
	public ModelAndView editLocationItem(HttpServletRequest request) throws Exception
	{
		return edit(request);
	}
	
	@RequestMapping(value="LocationItem/delete")
	public ModelAndView deleteLocationItem(HttpServletRequest request) throws Exception
	{
		locationItemService.delete(Long.parseLong(request.getParameter("id")));
		return new ModelAndView("redirect:/Location/getAll");
	}
	
	@RequestMapping(value="LocationItem/save")
	public ModelAndView saveLocationItem(@ModelAttribute("item") @Validated LocationItem item, BindingResult bindingResult, HttpServletRequest request) throws Exception
	{
		if (bindingResult.hasErrors()) {
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}
		
		if (item.getId() == null)
			item.setLocation(locationService.getById((long) request.getSession().getAttribute("parentId")));
		else
			item.setLocation(locationItemService.getById(item.getId()).getLocation());
		
		request.getSession().removeAttribute("parentId");
		
		if (item.getId() == null)
			locationItemService.create(item);
		else
			locationItemService.update(item);
		
		return new ModelAndView("redirect:/Location/getAll");
	}
	
	@RequestMapping(value = "LocationItem/getAll")
	public ModelAndView getAllLocationsItem(HttpServletRequest request) throws Exception
	{
		if (request.getParameter("parentId") != null || request.getParameter("parentId").equals(""))
		{
			request.setAttribute("parentId", request.getParameter("parentId").toString());
		}
		return getAll(request);
	}

	@Override
	public void feedSort(List<LocationItem> List, String column) {
		switch (column)
		{
		case "Name":
			Collections.sort(List, (p1, p2) -> p1.getName().compareTo(p2.getName()));
			break;
		case "Floor":
			Collections.sort(List, (p1, p2) -> p1.getFloor().compareTo(p2.getFloor()));
			break;
		case "Number":
			Collections.sort(List, (p1, p2) -> p1.getNumber().compareTo(p2.getNumber()));
			break;
		case "Details":
			Collections.sort(List, (p1, p2) -> p1.getDetails().compareTo(p2.getDetails()));
			break;
		case "Location":
			Collections.sort(List, (p1, p2) -> p1.getLocation().getName().compareTo(p2.getLocation().getName()));
			break;
		}	
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		Map.put("name", "Name");
		Map.put("floor", "Floor");
		Map.put("number", "Number");
		Map.put("details", "Details");
		Map.put("location", "Location");
	}

	@Override
	public void setAvatars(List<LocationItem> List) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
