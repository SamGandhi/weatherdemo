package com.sameer.springboot.weatherdemo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.sameer.springboot.weatherdemo.entity.WeatherDataDarksky;

public interface WeatherDataDarkskyRepository extends MongoRepository<WeatherDataDarksky, String> {
	
	List<WeatherDataDarksky> findByLatitudeAndLongitudeOrderByRecordedTimeDesc(float latitude, float longitude);

    List<WeatherDataDarksky> findByRecordedTimeGreaterThanEqual(Date recordedTime);
    
    public void deleteByRecordedTimeBefore(Date recordedTime);

}
