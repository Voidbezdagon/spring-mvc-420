package com.cm.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.cm.json.UserSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@teamId")
public class Team extends BaseEntity{

	private static final long serialVersionUID = 3372842139548747831L;

	@Column(unique = true)
	private String teamname;
	@ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
		}
		, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "Users_Teams",
    joinColumns = {@JoinColumn(name = "teamId")},
    inverseJoinColumns = @JoinColumn(name = "userId"))
	//@JsonBackReference(value="user-team")
	@JsonSerialize(using = UserSerializer.class)
    private List<User> users;
	@OneToMany(mappedBy = "assignedTeam", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	//@JsonBackReference(value="schedule-team")
	private List<Schedule> schedules;

	public String getTeamname() {
		return teamname;
	}
	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Schedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
}
