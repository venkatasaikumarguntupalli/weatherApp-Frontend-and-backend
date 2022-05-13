package com.example.weatherApp.service;

import com.example.weatherApp.dto.WeekWeatherDto;
import com.example.weatherApp.entity.WeatherEntity;

import java.text.ParseException;
import java.util.List;

public interface WeatherService {

    String addData(WeatherEntity weather);

    WeatherEntity updateData(WeatherEntity weather);

    List<WeatherEntity> getData();

    WeatherEntity getCurrentData(String city,Integer time,String date);

    List<WeatherEntity> getDayData(String city,String date) throws ParseException;

    List<WeekWeatherDto> getWeekData(String city, String date) throws ParseException;

    void addWeatherStatus(String weatherType, String weatherImage);

    String getWeatherImage(String weatherType);

}
