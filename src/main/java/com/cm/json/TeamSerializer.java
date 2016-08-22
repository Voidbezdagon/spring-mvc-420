package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cm.entity.Team;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TeamSerializer extends JsonSerializer<Team>{

	@Override
	public void serialize(Team arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
	        final SimpleTeam list = new SimpleTeam(arg0.getId(), arg0.getTeamname());
	        arg1.writeObject(list);
	}
	
	static class SimpleTeam
	{
		private Long id;
		private String teamname;

		public SimpleTeam(Long id, String tn)
		{
			this.id = id;
			this.teamname = tn;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTeamname() {
			return teamname;
		}

		public void setTeamname(String teamname) {
			this.teamname = teamname;
		}
	}

}
