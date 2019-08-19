package com.sameer.springboot.weatherdemo.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sameer.springboot.weatherdemo.exception.ResourceNotFoundException;
import com.sameer.springboot.weatherdemo.entity.LocationDataMapBox;
import com.sameer.springboot.weatherdemo.entity.WeatherDataDarksky;
import com.sameer.springboot.weatherdemo.repository.WeatherDataDarkskyRepository;
import com.sameer.springboot.weatherdemo.utils.DateUtils;

@Service
@Scope("prototype")
public class WebWeatherService {
	
	@Autowired
	WeatherDataDarkskyRepository weatherDataDarkskyRepository;
	
	private static final String darkSkyKey="208fdb2b4ac988a8e31eda167c50f6af";
	
	public WeatherDataDarksky add(@RequestBody WeatherDataDarksky weatherDataDarksky) {
        return weatherDataDarkskyRepository.save(weatherDataDarksky);
    }
	
	public WeatherDataDarksky update(String id, WeatherDataDarksky updateWeatherDataDarksky) {
		WeatherDataDarksky weatherDataDarksky = weatherDataDarkskyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException());
		weatherDataDarksky.setCloudCover(updateWeatherDataDarksky.getCloudCover());
		weatherDataDarksky.setHumidity(updateWeatherDataDarksky.getHumidity());
		weatherDataDarksky.setLatitude(updateWeatherDataDarksky.getLatitude());
		weatherDataDarksky.setLongitude(updateWeatherDataDarksky.getLongitude());
		weatherDataDarksky.setPrecipProbability(updateWeatherDataDarksky.getPrecipProbability());
		weatherDataDarksky.setSummary(updateWeatherDataDarksky.getSummary());
		weatherDataDarksky.setTemperature(updateWeatherDataDarksky.getTemperature());
		weatherDataDarksky.setRecordedTime(updateWeatherDataDarksky.getRecordedTime());
		weatherDataDarksky.setTimezone(updateWeatherDataDarksky.getTimezone());
		weatherDataDarksky.setUvIndex(updateWeatherDataDarksky.getUvIndex());
		weatherDataDarksky.setVisibility(updateWeatherDataDarksky.getVisibility());
		weatherDataDarksky.setWindSpeed(updateWeatherDataDarksky.getWindSpeed());
		return weatherDataDarkskyRepository.save(weatherDataDarksky);
	}
	
    public void delete(String id) {
    	WeatherDataDarksky weatherDataDarksky = weatherDataDarkskyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException());
    	weatherDataDarkskyRepository.delete(weatherDataDarksky);
    }
    
    public List<WeatherDataDarksky> searchByLatitudeAndLongitudeOrderByRecordedTimeDesc(float latitude, float longitude) {
        List<WeatherDataDarksky> result = new ArrayList<>();
        result = weatherDataDarkskyRepository.findAll();
        result = result.stream().filter(p -> latitude == p.getLongitude()).filter(p-> longitude == p.getLatitude()).sorted(Comparator.comparing(WeatherDataDarksky::getRecordedTime).reversed()).collect(Collectors.toList());
        return result;
    }
	
	public InputStream retrieve(LocationDataMapBox locationDataMapBox) throws Exception {
		String url = "https://api.darksky.net/forecast/" + darkSkyKey + "/" +
				locationDataMapBox.getLongitude() + "," + locationDataMapBox.getLatitude()
	                 + "?units=ca";
		
		//String url = "https://api.darksky.net/forecast/208fdb2b4ac988a8e31eda167c50f6af/37.8267,-122.4233";
	    URLConnection connection = new URL(url).openConnection();

	    return connection.getInputStream();
	  }
	
	public WeatherDataDarksky parse(InputStream weatherData) throws Exception {
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    JsonNode node = mapper.readTree(weatherData);
	    
	    WeatherDataDarksky weatherDataDarksky = new WeatherDataDarksky();
	    
	    weatherDataDarksky.setCloudCover(node.path("currently").path("cloudCover").floatValue());
		weatherDataDarksky.setHumidity(node.path("currently").path("humidity").floatValue());
		
		DecimalFormat form = new DecimalFormat();
		form.setMaximumFractionDigits(4);
		float latitude = node.path("latitude").floatValue();
		float longitude = node.path("longitude").floatValue();
		
		weatherDataDarksky.setLatitude(Float.valueOf(form.format(latitude)));
		weatherDataDarksky.setLongitude(Float.valueOf(form.format(longitude)));
		weatherDataDarksky.setPrecipProbability(node.path("currently").path("precipProbability").floatValue());
		weatherDataDarksky.setSummary(node.path("currently").path("summary").asText());
		weatherDataDarksky.setTemperature(node.path("currently").path("temperature").floatValue());
		weatherDataDarksky.setRecordedTime(new Date());
		weatherDataDarksky.setTimezone(node.path("timezone").asText());
		weatherDataDarksky.setUvIndex(node.path("currently").path("uvIndex").floatValue());
		weatherDataDarksky.setVisibility(node.path("currently").path("visibility").floatValue());
		weatherDataDarksky.setWindSpeed(node.path("currently").path("windSpeed").floatValue());

	    return weatherDataDarksky;
	  }
	
	public WeatherDataDarksky todayWeatherInformation(String ISOCountryCode, String placeName) throws IOException, Exception {
		LocationService locationService = new LocationService();
		LocationDataMapBox locationDataMapBox = locationService.getLocation(ISOCountryCode, placeName);
		return todayWeatherInformation(locationDataMapBox);
		
	}
	
	public WeatherDataDarksky todayWeatherInformation(LocationDataMapBox locationDataMapBox) throws Exception {
		
		List<WeatherDataDarksky> listWeatherDataDarksky = searchByLatitudeAndLongitudeOrderByRecordedTimeDesc (locationDataMapBox.getLatitude(), locationDataMapBox.getLongitude());
		if(!listWeatherDataDarksky.isEmpty()) {
			if( DateUtils.isToday(listWeatherDataDarksky.get(0).getRecordedTime())) {
				return listWeatherDataDarksky.get(0);
			} else {
				WeatherDataDarksky weatherDataDarksky = fetchTodayWeatherDetails(locationDataMapBox);
				return weatherDataDarksky;
			}
		} else {
			WeatherDataDarksky weatherDataDarksky = fetchTodayWeatherDetails(locationDataMapBox);
			return weatherDataDarksky;
		}
		
	}

	private WeatherDataDarksky fetchTodayWeatherDetails(LocationDataMapBox locationDataMapBox) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -3);
		Date expiryDate = cal.getTime();
		weatherDataDarkskyRepository.deleteByRecordedTimeBefore(expiryDate);
		WeatherDataDarksky weatherDataDarksky = parse(retrieve(locationDataMapBox));
		add(weatherDataDarksky);
		return weatherDataDarksky;
	}

}
