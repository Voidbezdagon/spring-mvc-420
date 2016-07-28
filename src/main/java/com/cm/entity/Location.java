package com.cm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Location extends BaseEntity{

	private static final long serialVersionUID = -1341116581813648958L;
	
	private String name;
	private String region;
	private String city;
	private Integer zip;
	private String street;
	private Integer streetNumber;
	private String details;
	private Float lat;
	private Float lng;
	@OneToMany(mappedBy = "location", cascade = CascadeType.MERGE)
	private List<LocationItem> locationItems;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(Integer streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public List<LocationItem> getLocationItems() {
		return locationItems;
	}
	public void setLocationItems(List<LocationItem> locationItems) {
		this.locationItems = locationItems;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLng() {
		return lng;
	}
	public void setLng(Float lng) {
		this.lng = lng;
	}
}
