package com.cm.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.cm.json.ScheduleActivityReportSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@scheduleReportId")
public class ScheduleReport extends BaseEntity{

	private static final long serialVersionUID = -3425599246883252616L;
	
	private String description;
	private Date date;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "scheduleId")
	//@JsonBackReference(value="schedule-schedulereport")
	private Schedule schedule;
	@OneToMany(mappedBy = "scheduleReport", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	//@JsonSerialize(using = ScheduleActivityReportSerializer.class)
	//@JsonManagedReference(value="schedulereport-scheduleactivityreport")
	private List<ScheduleActivityReport> activityReports;
	
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
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public List<ScheduleActivityReport> getActivityReports() {
		return activityReports;
	}
	public void setActivityReports(List<ScheduleActivityReport> activityReports) {
		this.activityReports = activityReports;
	}
}
