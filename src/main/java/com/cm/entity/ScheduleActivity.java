package com.cm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ScheduleActivity extends BaseEntity {
	private static final long serialVersionUID = -6454954275786468970L;
	
	String description;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="schedule")
	Schedule schedule;
	
	@OneToMany(mappedBy="scheduleActivity", cascade = CascadeType.MERGE, orphanRemoval = true)
	List<ScheduleActivityReport> scheduleActivityReports;
	
	@OneToOne(mappedBy = "scheduleActivity", cascade = CascadeType.MERGE, orphanRemoval = true)
	ScheduleActivityReport scheduleActivityReport;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public List<ScheduleActivityReport> getActivityReports() {
		return scheduleActivityReports;
	}

	public void setActivityReports(List<ScheduleActivityReport> scheduleActivityReports) {
		this.scheduleActivityReports = scheduleActivityReports;
	}
	
}
