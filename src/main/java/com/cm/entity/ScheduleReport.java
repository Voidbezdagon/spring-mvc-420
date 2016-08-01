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

@Entity
public class ScheduleReport extends BaseEntity{

	private static final long serialVersionUID = -3425599246883252616L;
	
	private String description;
	private Date date;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "creatorId")
	private User creator;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "scheduleId")
	private Schedule schedule;
	@OneToMany(mappedBy = "scheduleReport", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
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
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
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
