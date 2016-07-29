package com.cm.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cm.entity.Location;
import com.cm.entity.LocationItem;
import com.cm.service.LocationService;

@Controller
public class LocationItemsController extends BaseController<LocationItem> {
	
	@Autowired
	private LocationService locationService;
	
	public List<LocationItem> customList(Long id) throws InstantiationException, IllegalAccessException{
		Location location = locationService.getById(id);
		List<LocationItem> locationItems = location.getLocationItems();
		return locationItems;
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

	@Override
	public void feedSort(List<LocationItem> List, String column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAvatars(List<LocationItem> List) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
