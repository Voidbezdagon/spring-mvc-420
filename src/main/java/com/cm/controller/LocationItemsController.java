package com.cm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.Location;
import com.cm.entity.LocationItem;
import com.cm.service.LocationService;

@Controller
public class LocationItemsController extends BaseController<LocationItem> {
	
	@Autowired
	private LocationService locationService;
	
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
	public void filterAllByString(String column, String searchName, List<LocationItem> result) {
		switch (column)
		{
		case "Name":
			result = (ArrayList<LocationItem>) result.stream().filter(p -> p.getName().contains(searchName)).collect(Collectors.toList());
			break;
		case "Floor":
			result = (ArrayList<LocationItem>) result.stream().filter(p -> p.getFloor() == Integer.parseInt(searchName)).collect(Collectors.toList());
			break;
		case "Number":
			result = (ArrayList<LocationItem>) result.stream().filter(p -> p.getNumber() == Integer.parseInt(searchName)).collect(Collectors.toList());
			break;
		case "Details":
			result = (ArrayList<LocationItem>) result.stream().filter(p -> p.getDetails().contains(searchName)).collect(Collectors.toList());
			break;
		case "Location":
			result = (ArrayList<LocationItem>) result.stream().filter(p -> p.getLocation().getName().contains(searchName)).collect(Collectors.toList());
			break;
		}	
		
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
		return delete(request);
	}
	
	@RequestMapping(value="LocationItem/save")
	public ModelAndView saveLocationItem(@ModelAttribute LocationItem item, HttpServletRequest request) throws Exception
	{
		System.out.println(request.getSession().getAttribute("parentId"));
		item.setLocation(locationService.getById((long) request.getSession().getAttribute("parentId")));
		request.getSession().removeAttribute("parentId");
		return save(item, request);
	}
	
	@RequestMapping(value = "LocationItem/getAll")
	public ModelAndView getAllLocationsItem(HttpServletRequest request) throws Exception
	{
		if (request.getAttribute("parentId") != null)
			request.setAttribute("parentId", request.getAttribute("parentId"));
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
