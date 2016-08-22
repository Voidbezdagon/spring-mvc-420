package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cm.entity.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class UserSerializer extends JsonSerializer<List<User>>{

	@Override
	public void serialize(List<User> arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
	        final List<SimpleUser> list = new ArrayList<SimpleUser>();
	        for (final User user : arg0) {
	            list.add(new SimpleUser(user.getId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), user.getAvatar(), user.getAdmin()));                
	        }
	        arg1.writeObject(list);
	}
	
	static class SimpleUser
	{
		private Long id;
		private String firstname;
		private String lastname;
		private String username;
		private String password;
		private String avatar;
		private Boolean admin;
		
		public SimpleUser(Long id, String fn, String ln, String un, String pw, String av, Boolean ad)
		{
			this.id = id;
			this.firstname = fn;
			this.lastname = ln;
			this.username = un;
			this.password = pw;
			this.avatar = av;
			this.admin = ad;
		}
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public Boolean getAdmin() {
			return admin;
		}
		public void setAdmin(Boolean admin) {
			this.admin = admin;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
	}

}
