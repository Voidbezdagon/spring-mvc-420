package com.cm.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cm.json.ScheduleReportSingleSerializer;
import com.cm.json.ScheduleActivitySingleSerializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@scheduleActivityReportId")
public class ScheduleActivityReport extends BaseEntity {
	
	private static final long serialVersionUID = -6454954275786468970L;
	
	private boolean isFinished;
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="scheduleActivityId")
	@JsonSerialize(using = ScheduleActivitySingleSerializer.class)
	//@JsonBackReference(value="scheduleactivity-scheduleactivityreport")
	private ScheduleActivity scheduleActivity;
	@ManyToOne
	@JoinColumn(name="scheduleReportId")
	@JsonSerialize(using = ScheduleReportSingleSerializer.class)
	//@JsonBackReference(value="schedulereport-scheduleactivityreport")
	private ScheduleReport scheduleReport;

	public boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public ScheduleActivity getScheduleActivity() {
		return scheduleActivity;
	}

	public void setScheduleActivity(ScheduleActivity scheduleActivity) {
		this.scheduleActivity = scheduleActivity;
	}
	public ScheduleReport getScheduleReport() {
		return scheduleReport;
	}
	public void setScheduleReport(ScheduleReport scheduleReport) {
		this.scheduleReport = scheduleReport;
	}
}
