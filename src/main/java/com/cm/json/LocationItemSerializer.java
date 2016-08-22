package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cm.entity.LocationItem;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocationItemSerializer extends JsonSerializer<List<LocationItem>>{
	
	@Override
	public void serialize(List<LocationItem> arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
	        final List<SimpleLocationItem> list = new ArrayList<SimpleLocationItem>();
	        for (final LocationItem item : arg0) {
	            list.add(new SimpleLocationItem(item.getId(), item.getName(), item.getFloor(), item.getNumber(), item.getDetails()));                
	        }
	        arg1.writeObject(list);
	}
	
	static class SimpleLocationItem
	{
		private Long id;
		private String name;
		private Integer floor;
		private Integer number;
		private String details;

		public SimpleLocationItem(Long id, String na, Integer fl, Integer nu, String de)
		{
			this.id = id;
			this.name = na;
			this.floor = fl;
			this.number = nu;
			this.details = de;
		}
		
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getFloor() {
			return floor;
		}

		public void setFloor(Integer floor) {
			this.floor = floor;
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}
	}
}
