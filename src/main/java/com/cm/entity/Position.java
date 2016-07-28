package com.cm.entity;

import javax.persistence.Entity;

@Entity
public class Position extends BaseEntity{

	private static final long serialVersionUID = -4597852384321501319L;

	private Long parentId;
	private String name;
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
