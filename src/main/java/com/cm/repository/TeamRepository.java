package com.cm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.cm.entity.Team;

@Repository
public class TeamRepository extends BaseRepository<Team>{

	@Override
	public List<Team> getAllByString(String column, String searchString) throws Exception {
		ArrayList<Team> result = new ArrayList<Team>();
		switch (column)
		{
		case "Team Name":
			result = (ArrayList<Team>) getAll().stream().filter(p -> p.getTeamname().contains(searchString)).collect(Collectors.toList());
			break;
		}	
		return result;
	}
}
