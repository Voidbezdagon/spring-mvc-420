package com.cm.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.cm.json.LocationSerializer;
import com.cm.json.TeamSerializer;
import com.cm.json.ScheduleActivitySerializer;
import com.cm.json.ScheduleReportSerializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@scheduleId")
public class Schedule extends BaseEntity{

	private static final long serialVersionUID = 2509332616418806377L;
	
	@Column(unique = true)
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Long recurringTime;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "assignedTeamId")
	@JsonSerialize(using = TeamSerializer.class)
	//@JsonManagedReference(value="schedule-team")
	private Team assignedTeam;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "locationId")
	@JsonSerialize(using = LocationSerializer.class)
	//@JsonBackReference(value="location-schedule")
	private Location location;
	@OneToMany(mappedBy = "schedule", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonSerialize(using = ScheduleActivitySerializer.class)
	//@JsonManagedReference(value="schedule-scheduleactivity")
	private List<ScheduleActivity> activities;
	@OneToMany(mappedBy = "schedule", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonSerialize(using = ScheduleReportSerializer.class)
	//@JsonManagedReference(value="schedule-schedulereport")
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
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
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
