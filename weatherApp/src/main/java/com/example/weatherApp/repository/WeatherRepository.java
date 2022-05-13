package com.example.weatherApp.repository;

import com.example.weatherApp.entity.WeatherEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends MongoRepository<WeatherEntity,String> {
    WeatherEntity findFirstByCityAndDateAndTime(String city, String date, int time);
    List<WeatherEntity> findByCityAndDate(String city, String date);

//    @Query(value="SELECT * FROM weatherEntity u WHERE u.date= :date AND u.city= :city AND u.time BETWEEN 1 and 7")
//    List<WeatherEntity> findWeatherByCityAndDateAndTime(@Param("date") String date,@Param("city") String city);

}
