package com.cm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ScheduleActivity extends BaseEntity {
	private static final long serialVersionUID = -6454954275786468970L;
	
	String description;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="schedule")
	Schedule schedule;
	
	@OneToMany(mappedBy="scheduleActivity", cascade = CascadeType.MERGE, orphanRemoval = true)
	List<ActivityReport> activityReports;
	
}
