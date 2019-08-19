package com.sameer.springboot.weatherdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "location_data_mapbox")
@CompoundIndex(def = "{'placename':1, 'isocountrycode':-1}", name = "compound_index", unique = true)
public class LocationDataMapBox {
	
	@Id
	private String Id;
	
	private String placename;
	private String isocountrycode;
	private String countryName;
	private float relevance;
	private float latitude;
	private float longitude;
	private String address;

	public LocationDataMapBox() {}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPlacename() {
		return placename;
	}

	public void setPlacename(String placename) {
		this.placename = placename;
	}

	public float getRelevance() {
		return relevance;
	}

	public void setRelevance(float relevance) {
		this.relevance = relevance;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsocountrycode() {
		return isocountrycode;
	}

	public void setIsocountrycode(String isocountrycode) {
		this.isocountrycode = isocountrycode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
}
