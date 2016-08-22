package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cm.entity.ScheduleActivity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ScheduleActivitySingleSerializer extends JsonSerializer<ScheduleActivity>{
	
	@Override
	public void serialize(ScheduleActivity arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
	        final SimpleScheduleActivitySingle list = new SimpleScheduleActivitySingle(arg0.getId(), arg0.getDescription());
	        arg1.writeObject(list);
	}
	
	static class SimpleScheduleActivitySingle
	{
		private Long id;
		private String description;
		
		public SimpleScheduleActivitySingle(Long id, String de)
		{
			this.id = id;
			this.description = de;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}
	
	
	
}
