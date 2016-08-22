package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleActivityReport;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ScheduleActivitySerializer extends JsonSerializer<List<ScheduleActivity>>{
	
	@Override
	public void serialize(List<ScheduleActivity> arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
	        final List<SimpleScheduleActivity> list = new ArrayList<SimpleScheduleActivity>();
	        for (final ScheduleActivity item : arg0) {
	            list.add(new SimpleScheduleActivity(item.getId(), item.getDescription(), item.getScheduleActivityReports()));                
	        }
	        arg1.writeObject(list);
	}
	
	static class SimpleScheduleActivity
	{
		private Long id;
		private String description;
		private List<ScheduleActivityReport> scheduleActivityReports;
		
		public SimpleScheduleActivity(Long id, String de, List<ScheduleActivityReport> sa)
		{
			this.id = id;
			this.description = de;
			this.setScheduleActivityReports(sa);
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

		public List<ScheduleActivityReport> getScheduleActivityReports() {
			return scheduleActivityReports;
		}

		public void setScheduleActivityReports(List<ScheduleActivityReport> scheduleActivityReports) {
			this.scheduleActivityReports = scheduleActivityReports;
		}
	}
}
