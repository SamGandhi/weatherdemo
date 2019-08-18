package com.sameer.springboot.weatherdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "location_data_mapbox")
@CompoundIndex(def = "{'latitude':1, 'longitude':-1}", name = "compound_index", unique = true)
public class LocationDataMapBox {
	
	@Id
	private String Id;
	
	private String placeName;
	
	@Field("ISOCountryCode")
	private String iSOCountryCode;
	
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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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

	public String getISOCountryCode() {
		return iSOCountryCode;
	}

	public void setISOCountryCode(String iSOCountryCode) {
		this.iSOCountryCode = iSOCountryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
}
