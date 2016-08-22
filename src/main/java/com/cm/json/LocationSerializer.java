package com.cm.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cm.entity.Location;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocationSerializer extends JsonSerializer<Location>{

	@Override
	public void serialize(Location arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
	        final SimpleLocation list = new SimpleLocation(arg0.getId(), arg0.getName(), arg0.getRegion(), arg0.getCity(), arg0.getZip(),
            		arg0.getStreet(), arg0.getStreetNumber(), arg0.getDetails(), arg0.getLat(), arg0.getLng());
	        arg1.writeObject(list);
	}
	
	static class SimpleLocation
	{
		private Long id;
		private String name;
		private String region;
		private String city;
		private Integer zip;
		private String street;
		private Integer streetNumber;
		private String details;
		private Float lat;
		private Float lng;

		public SimpleLocation(Long id, String na, String re, String ci, Integer zi, String st, Integer sn, String de, Float la, Float ln)
		{
			this.id = id;
			this.name = na;
			this.region = re;
			this.city = ci;
			this.zip = zi;
			this.street = st;
			this.streetNumber = sn;
			this.details = de;
			this.lat = la;
			this.lng = ln;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

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
	
	
}
