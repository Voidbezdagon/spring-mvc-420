package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cm.entity.Position;
import com.cm.entity.Team;
import com.cm.entity.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TeamSerializer extends JsonSerializer<Team>{

	@Override
	public void serialize(Team arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
			ArrayList<User> users = new ArrayList<User>();
			
			if (arg0.getUsers() != null)
			{
				for (User u: arg0.getUsers())
				{
					User user = new User();
					user.setAccesskey(u.getAccesskey());
					user.setAdmin(u.getAdmin());
					user.setAvatar(u.getAvatar());
					user.setFirstname(u.getFirstname());
					user.setId(u.getId());
					user.setLastname(u.getLastname());
					user.setPassword(u.getPassword());
					user.setUsername(u.getUsername());
					//Setting Position
						Position p = new Position();
						p.setId(u.getPosition().getId());
						p.setLevel(u.getPosition().getLevel());
						p.setName(u.getPosition().getName());
						p.setParentId(u.getPosition().getParentId());
					user.setPosition(p);
					users.add(user);
				}
			}
	        final SimpleTeam list = new SimpleTeam(arg0.getId(), arg0.getTeamname(), users);
	        arg1.writeObject(list);
	}
	
	static class SimpleTeam
	{
		private Long id;
		private String teamname;
		private List<User> users;
		
		public SimpleTeam(Long id, String tn, List<User> users)
		{
			this.id = id;
			this.teamname = tn;
			this.users = users;
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

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}
	}

}
