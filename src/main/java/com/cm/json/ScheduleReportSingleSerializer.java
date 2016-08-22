package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cm.entity.ScheduleActivity;
import com.cm.entity.ScheduleReport;
import com.cm.json.ScheduleActivitySerializer.SimpleScheduleActivity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ScheduleReportSingleSerializer extends JsonSerializer<ScheduleReport>{

	@Override
	public void serialize(ScheduleReport arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		
	        final SimpleScheduleReportSingle list = new SimpleScheduleReportSingle(arg0.getId(), arg0.getDescription(), arg0.getDate());
	        arg1.writeObject(list);
	}
	
	static class SimpleScheduleReportSingle
 	{
 		private Long id;
		private String description;
 		private Date date;
 		
 		public SimpleScheduleReportSingle(Long id, String de, Date da)
 		{
 			this.id = id;
 			this.description = de;
 			this.date = da;
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
 	}
}
