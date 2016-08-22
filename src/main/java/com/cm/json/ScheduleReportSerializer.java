package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleActivityReport;
import com.cm.entity.ScheduleReport;
import com.cm.json.ScheduleActivitySerializer.SimpleScheduleActivity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ScheduleReportSerializer extends JsonSerializer<List<ScheduleReport>>{

	@Override
	public void serialize(List<ScheduleReport> arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		
	        final List<SimpleScheduleReport> list = new ArrayList<SimpleScheduleReport>();
	        for (final ScheduleReport item : arg0) {
	            list.add(new SimpleScheduleReport(item.getId(), item.getDescription(), item.getDate(), item.getActivityReports()));                
	        }
	        arg1.writeObject(list);
	}
	
	static class SimpleScheduleReport
 	{
 		private Long id;
		private String description;
 		private Date date;
 		private List<ScheduleActivityReport> scheduleActivityReports;
 		
 		public SimpleScheduleReport(Long id, String de, Date da, List<ScheduleActivityReport> sa)
 		{
 			this.id = id;
 			this.description = de;
 			this.date = da;
 			this.scheduleActivityReports = sa;
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

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
		
		public List<ScheduleActivityReport> getScheduleActivityReports() {
			return scheduleActivityReports;
		}

		public void setScheduleActivityReports(List<ScheduleActivityReport> scheduleActivityReports) {
			this.scheduleActivityReports = scheduleActivityReports;
		}
 	}
}
