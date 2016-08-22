package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cm.entity.ScheduleActivityReport;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ScheduleActivityReportSerializer extends JsonSerializer<List<ScheduleActivityReport>>{

	@Override
	public void serialize(List<ScheduleActivityReport> arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
	        final List<SimpleScheduleActivityReport> list = new ArrayList<SimpleScheduleActivityReport>();
	        for (final ScheduleActivityReport item : arg0) {
	            list.add(new SimpleScheduleActivityReport(item.getId(), item.getIsFinished(), item.getDate()));                
	        }
	        arg1.writeObject(list);
	}
	
	static class SimpleScheduleActivityReport
	{
		private Long id;
		private boolean isFinished;
		private Date date;
		
		public SimpleScheduleActivityReport(Long id, boolean is, Date da)
		{
			this.id = id;
			this.isFinished = is;
			this.date = da;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public boolean isFinished() {
			return isFinished;
		}

		public void setFinished(boolean isFinished) {
			this.isFinished = isFinished;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	}
}
