package com.example.weatherApp.dto;

public class WeatherStatusDto {

    private String id;
    private String weatherType;
    private String weatherImage;

    public WeatherStatusDto(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(String weatherImage) {
        this.weatherImage = weatherImage;
    }

    public WeatherStatusDto(String id, String weatherType, String weatherImage) {
        this.id = id;
        this.weatherType = weatherType;
        this.weatherImage = weatherImage;
    }
}
