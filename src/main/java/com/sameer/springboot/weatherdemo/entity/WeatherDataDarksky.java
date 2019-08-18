package com.sameer.springboot.weatherdemo.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "weather_data_darksky")
@CompoundIndex(def = "{'latitude':1, 'longitude':-1}", name = "weatherdata_compound_index", unique = true)
public class WeatherDataDarksky {
	
	@Id
	private String id;
	private float latitude;
	private float longitude;
	private String timezone;
	private Date recordedTime;
	private String summary;
	private float precipProbability;
	private float temperature;
	private float humidity;
	private float uvIndex;
	private float cloudCover;
	private float windSpeed;
	private float visibility;
	
	public WeatherDataDarksky() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Date getRecordedTime() {
		return recordedTime;
	}

	public void setRecordedTime(Date recordedTime) {
		this.recordedTime = recordedTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public float getPrecipProbability() {
		return precipProbability;
	}

	public void setPrecipProbability(float precipProbability) {
		this.precipProbability = precipProbability;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getUvIndex() {
		return uvIndex;
	}

	public void setUvIndex(float uvIndex) {
		this.uvIndex = uvIndex;
	}

	public float getCloudCover() {
		return cloudCover;
	}

	public void setCloudCover(float cloudCover) {
		this.cloudCover = cloudCover;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public float getVisibility() {
		return visibility;
	}

	public void setVisibility(float visibility) {
		this.visibility = visibility;
	}
	
	

}
