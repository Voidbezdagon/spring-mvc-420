package com.cm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.cm.entity.Position;

@Repository
public class PositionRepository extends BaseRepository<Position>{

	@Override
	public List<Position> getAllByString(String column, String searchString) throws Exception {
		ArrayList<Position> result = new ArrayList<Position>();
		switch (column)
		{
		case "Superior Position":
			result = (ArrayList<Position>) getAll().stream().filter(p -> p.getParentId().equals(Long.parseLong(searchString))).collect(Collectors.toList());
			break;
		case "Name":
			result = (ArrayList<Position>) getAll().stream().filter(p -> p.getName().contains(searchString)).collect(Collectors.toList());
			break;
		}	
		return result;
	}
}
