package com.cm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.cm.entity.LocationItem;

@Repository
public class LocationItemRepository extends BaseRepository<LocationItem>{
	
	@Override
	public List<LocationItem> getAllByString(String column, String searchString) throws Exception {
		ArrayList<LocationItem> result = new ArrayList<LocationItem>();
		switch (column)
		{
		case "Name":
			result = (ArrayList<LocationItem>) getAll().stream().filter(p -> p.getName().contains(searchString)).collect(Collectors.toList());
			break;
		case "Floor":
			result = (ArrayList<LocationItem>) getAll().stream().filter(p -> p.getFloor() == Integer.parseInt(searchString)).collect(Collectors.toList());
			break;
		case "Number":
			result = (ArrayList<LocationItem>) getAll().stream().filter(p -> p.getNumber() == Integer.parseInt(searchString)).collect(Collectors.toList());
			break;
		case "Details":
			result = (ArrayList<LocationItem>) getAll().stream().filter(p -> p.getDetails().contains(searchString)).collect(Collectors.toList());
			break;
		case "Location":
			result = (ArrayList<LocationItem>) getAll().stream().filter(p -> p.getLocation().getName().contains(searchString)).collect(Collectors.toList());
			break;
		}	
		return result;
	}
	
}
