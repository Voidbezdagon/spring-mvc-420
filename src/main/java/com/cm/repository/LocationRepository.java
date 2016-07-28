package com.cm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.cm.entity.Location;

@Repository
public class LocationRepository extends BaseRepository<Location>{
	
	@Override
	public List<Location> getAllByString(String column, String searchString) throws Exception {
		ArrayList<Location> result = new ArrayList<Location>();
		switch (column)
		{
		case "Name":
			result = (ArrayList<Location>) getAll().stream().filter(p -> p.getName().contains(searchString)).collect(Collectors.toList());
			break;
		case "Region":
			result = (ArrayList<Location>) getAll().stream().filter(p -> p.getRegion().contains(searchString)).collect(Collectors.toList());
			break;
		case "City":
			result = (ArrayList<Location>) getAll().stream().filter(p -> p.getCity().contains(searchString)).collect(Collectors.toList());
			break;
		case "ZIP":
			result = (ArrayList<Location>) getAll().stream().filter(p -> p.getZip() == Integer.parseInt(searchString)).collect(Collectors.toList());
			break;
		case "Street":
			result = (ArrayList<Location>) getAll().stream().filter(p -> p.getStreet().contains(searchString)).collect(Collectors.toList());
			break;
		case "Street Number":
			result = (ArrayList<Location>) getAll().stream().filter(p -> p.getStreetNumber() == Integer.parseInt(searchString)).collect(Collectors.toList());
			break;
		case "Details":
			result = (ArrayList<Location>) getAll().stream().filter(p -> p.getDetails().contains(searchString)).collect(Collectors.toList());
			break;
		}	
		return result;
	}

}
