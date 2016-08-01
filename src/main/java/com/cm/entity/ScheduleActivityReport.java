package com.cm.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ScheduleActivityReport extends BaseEntity {
	private static final long serialVersionUID = -6454954275786468970L;
	boolean isFinished;
	
	@OneToOne
	@JoinColumn(name="scheduleActivity")
	ScheduleActivity scheduleActivity;
	
	@ManyToOne
	@JoinColumn(name="user")
	User user;

	public boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public ScheduleActivity getScheduleActivity() {
		return scheduleActivity;
	}

	public void setScheduleActivity(ScheduleActivity scheduleActivity) {
		this.scheduleActivity = scheduleActivity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
