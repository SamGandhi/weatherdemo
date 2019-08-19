package com.sameer.springboot.weatherdemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sameer.springboot.weatherdemo.entity.LocationDataMapBox;

public interface LocationRepository extends MongoRepository<LocationDataMapBox, String>{
	
	List<LocationDataMapBox> findByPlacename(String placename);
	
}
