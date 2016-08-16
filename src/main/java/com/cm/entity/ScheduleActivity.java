package com.cm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ScheduleActivity extends BaseEntity {
	private static final long serialVersionUID = -6454954275786468970L;
	
	private String description;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="scheduleId")
	@JsonBackReference
	private Schedule schedule;
	
	@OneToMany(mappedBy="scheduleActivity", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<ScheduleActivityReport> scheduleActivityReports;

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

	public List<ScheduleActivityReport> getScheduleActivityReports() {
		return scheduleActivityReports;
	}

	public void setScheduleActivityReports(List<ScheduleActivityReport> scheduleActivityReports) {
		this.scheduleActivityReports = scheduleActivityReports;
	}
	
}
