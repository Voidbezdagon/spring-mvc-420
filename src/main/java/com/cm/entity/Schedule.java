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
public class Schedule extends BaseEntity{

	private static final long serialVersionUID = 2509332616418806377L;
	
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Long recurringTime;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "assignedTeamId")
	private Team assignedTeam;
	@OneToMany(mappedBy = "schedule", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ScheduleActivity> activities;
	@OneToMany(mappedBy = "schedule", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ScheduleReport> reports;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Long getRecurringTime() {
		return recurringTime;
	}
	public void setRecurringTime(Long recurringTime) {
		this.recurringTime = recurringTime;
	}
	public Team getAssignedTeam() {
		return assignedTeam;
	}
	public void setAssignedTeam(Team assignedTeam) {
		this.assignedTeam = assignedTeam;
	}
	public List<ScheduleActivity> getActivities() {
		return activities;
	}
	public void setActivities(List<ScheduleActivity> activities) {
		this.activities = activities;
	}
	public List<ScheduleReport> getReports() {
		return reports;
	}
	public void setReports(List<ScheduleReport> reports) {
		this.reports = reports;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
