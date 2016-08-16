package com.cm.entity;

import javax.persistence.Entity;

@Entity
public class Guz extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
