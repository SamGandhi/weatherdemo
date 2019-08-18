package com.sameer.springboot.weatherdemo.service;

import java.io.InputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sameer.springboot.weatherdemo.entity.LocationDataMapBox;

public class LocationParser {
	
	public LocationDataMapBox parse(InputStream locationData) throws Exception {
	   
		ObjectMapper mapper = new ObjectMapper();
		
		LocationDataMapBox location = new LocationDataMapBox();
		
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	    JsonNode node = mapper.readTree(locationData);
	    
	    System.out.println(node.path("features").get(0).path("place_name").asText());
	    location.setAddress(node.path("features").get(0).path("place_name").asText());
	    location.setLatitude(node.path("features").get(0).path("center").get(0).floatValue());
	    location.setLongitude(node.path("features").get(0).path("center").get(1).floatValue());

	    System.out.println("Latitude = " +location.getLatitude());
	    
	   System.out.println(node);

		return location ;
	  }

}
