package com.example.weatherApp.repository;

import com.example.weatherApp.entity.WeatherStatusEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherStatusRepository extends MongoRepository<WeatherStatusEntity,String> {
    List<WeatherStatusEntity> findByWeatherType(String weatherType);
}
