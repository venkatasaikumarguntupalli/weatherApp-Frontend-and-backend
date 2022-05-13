package com.example.weatherApp.dto;

public class WeekWeatherDto {

    private String date;
    private Integer minTemperature;
    private Integer maxTemperature;
    private Integer avgTemprature;
    private Integer avgHumidity;
    private Integer avgPressure;
    private String city;
    private String weatherStatus;
    private String weatherImage;

    public WeekWeatherDto(){

    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getAvgTemprature() {
        return avgTemprature;
    }

    public void setAvgTemprature(Integer avgTemprature) {
        this.avgTemprature = avgTemprature;
    }

    public Integer getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(Integer avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public Integer getAvgPressure() {
        return avgPressure;
    }

    public void setAvgPressure(Integer avgPressure) {
        this.avgPressure = avgPressure;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(String weatherImage) {
        this.weatherImage = weatherImage;
    }

    public WeekWeatherDto(String date, Integer minTemperature, Integer maxTemperature, Integer avgTemprature, Integer avgHumidity, Integer avgPressure, String city, String weatherStatus, String weatherImage) {
        this.date = date;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.avgTemprature = avgTemprature;
        this.avgHumidity = avgHumidity;
        this.avgPressure = avgPressure;
        this.city = city;
        this.weatherStatus = weatherStatus;
        this.weatherImage = weatherImage;
    }
}
