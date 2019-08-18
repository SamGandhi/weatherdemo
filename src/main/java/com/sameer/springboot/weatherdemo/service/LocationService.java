package com.sameer.springboot.weatherdemo.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sameer.springboot.weatherdemo.entity.LocationDataMapBox;
import com.sameer.springboot.weatherdemo.entity.WeatherDataDarksky;
import com.sameer.springboot.weatherdemo.exception.ResourceNotFoundException;
import com.sameer.springboot.weatherdemo.repository.LocationRepository;

@Service
@Scope("prototype")
public class LocationService {
	
	@Autowired
	LocationRepository locationRepository;
	
	//@Value('{access_token}')
	private String access_token = "pk.eyJ1Ijoic2FtZWVyZ2FuZGhpIiwiYSI6ImNqejhpYnJ4YTAxN2IzZW1xN3NtZTE5NncifQ.Xs_u72GynRlI5nmyDUQbZQ";
	
	public LocationDataMapBox add(@RequestBody LocationDataMapBox locationDataMapBox) {
        return locationRepository.save(locationDataMapBox);
    }
	
	public LocationDataMapBox update(String id, LocationDataMapBox updateLocationDataMapBox) {
		LocationDataMapBox locationDataMapBox = locationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException());
		locationDataMapBox.setAddress(updateLocationDataMapBox.getAddress());
		locationDataMapBox.setLatitude(updateLocationDataMapBox.getLatitude());
		locationDataMapBox.setLongitude(updateLocationDataMapBox.getLongitude());
		locationDataMapBox.setPlaceName(updateLocationDataMapBox.getPlaceName());
		locationDataMapBox.setRelevance(updateLocationDataMapBox.getRelevance());
		return locationRepository.save(locationDataMapBox);
	}
	
	public void delete(String id) {
		LocationDataMapBox locationDataMapBox = locationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException());
    	locationRepository.delete(locationDataMapBox);
    }
	
	public List<LocationDataMapBox> searchByPlaceName(String placeName) {
        List<LocationDataMapBox> result = (List<LocationDataMapBox>) locationRepository.findByPlaceName(placeName).stream()
        		.filter(Optional::isPresent)
        		.map(Optional::get)
        		.collect(Collectors.toList());
        return result;
    }
	
	public List<LocationDataMapBox> searchByPlaceNameAndISOCountryCode(String placeName , String ISOCountryCode) {
        List<LocationDataMapBox> result = (List<LocationDataMapBox>) locationRepository.findByPlaceNameAndISOCountryCode(placeName, ISOCountryCode).stream()
        		.filter(Optional::isPresent)
        		.map(Optional::get)
        		.collect(Collectors.toList());
        return result;
    }
	
	public InputStream retrieve(String ISOCountryCode, String placeName) throws Exception {
	    String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + URLEncoder.encode(placeName, "UTF-8") + ".json?country="+ISOCountryCode+"&types=place&access_token=" + access_token;
	    URLConnection connection = new URL(url).openConnection();

	    return connection.getInputStream();
	  }
	
	public LocationDataMapBox getLocation(String ISOCountryCode, String placeName) throws IOException, Exception {
		List<LocationDataMapBox> locationDataMapBox = StringUtils.isEmpty(ISOCountryCode) ? searchByPlaceName(placeName) : searchByPlaceNameAndISOCountryCode(placeName, ISOCountryCode);
		if(!locationDataMapBox.isEmpty()) {
			return locationDataMapBox.get(0);
		} else {
			String countryName ="";
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			JsonNode node = mapper.readTree(retrieve(ISOCountryCode, placeName));
			LocationDataMapBox location = new LocationDataMapBox();
			location.setAddress(node.path("features").get(0).path("place_name").asText());
		    location.setLatitude(node.path("features").get(0).path("center").get(0).floatValue());
		    location.setLongitude(node.path("features").get(0).path("center").get(1).floatValue());
		    location.setPlaceName(node.path("features").get(0).path("place_name").asText());
		    for (JsonNode objNode : node.path("features").get(0).path("context")){
		    	if( objNode.path("id").asText().startsWith("country")) {
		    		countryName  = objNode.path("text").asText(); 
		    		break;
		    	}
		    }
		    
		    location.setCountryName(countryName);
		    location.setISOCountryCode(getISOCountry(countryName));
		    location.setRelevance(node.path("features").get(0).path("relevance").floatValue());
		    //add(location);
		    
			return location;
		}
		
	}
	
	public String getISOCountry(String countryName) {
		
		Map<String, String> countries = new HashMap<>(); 
		for (String iso : Locale.getISOCountries()) {
			Locale l = new Locale("", iso);
			countries.put(l.getDisplayCountry(), iso);
		}
		return countries.get(countryName);
	}

}
