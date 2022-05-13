package com.example.weatherApp.dto;

public class WeatherDto {

    private String id;

    private String date;
    private Integer time;
    private Integer temprature;
    private Integer humidity;
    private Integer pressure;
    private String city;
    private String weatherStatus;
    private String weatherImage;

    public WeatherDto(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTime() { return time; }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTemprature() {
        return temprature;
    }

    public void setTemprature(Integer temprature) {
        this.temprature = temprature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
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

    public WeatherDto(String id, String date, Integer time, Integer temprature, Integer humidity, Integer pressure, String city, String weatherStatus, String weatherImage) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.temprature = temprature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.city = city;
        this.weatherStatus = weatherStatus;
        this.weatherImage = weatherImage;
    }
}
