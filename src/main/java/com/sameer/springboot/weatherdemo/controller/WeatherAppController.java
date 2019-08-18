package com.sameer.springboot.weatherdemo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sameer.springboot.weatherdemo.entity.LocationDataMapBox;
import com.sameer.springboot.weatherdemo.entity.WeatherDataDarksky;
import com.sameer.springboot.weatherdemo.service.LocationService;
import com.sameer.springboot.weatherdemo.service.WebWeatherService;

@Controller
public class WeatherAppController {

	@Autowired
	LocationService locationService;
	
	@Autowired
	WebWeatherService webWeatherService;
	
	@GetMapping("/WeatherApp")
	public String getWeatherDetails(@RequestParam String ISOCountryCode, @RequestParam String placeName, Model theModel) throws Exception {
		
		List<Map<LocationDataMapBox, WeatherDataDarksky>> list = new ArrayList<Map<LocationDataMapBox, WeatherDataDarksky>>();
		
		LocationDataMapBox locationDataMapBox = locationService.getLocation(ISOCountryCode, placeName);
		
		WeatherDataDarksky weatherDataDarksky = webWeatherService.todayWeatherInformation(locationDataMapBox);
		
	    Map<LocationDataMapBox, WeatherDataDarksky> map = new HashMap<LocationDataMapBox, WeatherDataDarksky>();
		map.put(locationDataMapBox, weatherDataDarksky);
		System.out.println(map);
		list.add(map);
		
		theModel.addAttribute("theDate", new java.util.Date());
		theModel.addAttribute("weatherData", list);
		
		return "HelloWorld"; 
		
	}
}
