package com.sameer.springboot.weatherdemo.service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LocationRetriever {

	private static final String geocodeURL = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
	
	public InputStream retrieve(String address) throws Exception {
	    String url = geocodeURL + URLEncoder.encode(address, "UTF-8") + ".json?types=address&proximity=-122.39738575285674,37.7925147111369453&access_token=pk.eyJ1Ijoic2FtZWVyZ2FuZGhpIiwiYSI6ImNqejhpYnJ4YTAxN2IzZW1xN3NtZTE5NncifQ.Xs_u72GynRlI5nmyDUQbZQ";
	    URLConnection connection = new URL(url).openConnection();

	    return connection.getInputStream();
	  }
	
}

