package com.cm.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@MappedSuperclass
public class BaseEntity implements Serializable{	
	
		private static final long serialVersionUID = -6454954275786468970L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		public Long getId()
		{
			return id;
		}
		
		public void setId(Long id)
		{
			this.id = id;
		}
}
