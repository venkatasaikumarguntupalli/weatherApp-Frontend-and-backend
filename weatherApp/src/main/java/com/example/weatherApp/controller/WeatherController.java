package com.example.weatherApp.controller;


import com.example.weatherApp.customExceptions.CustomException;
import com.example.weatherApp.dto.WeatherDto;
import com.example.weatherApp.dto.WeatherStatusDto;
import com.example.weatherApp.dto.WeekWeatherDto;
import com.example.weatherApp.entity.WeatherEntity;
import com.example.weatherApp.errorHandling.Response;
import com.example.weatherApp.service.WeatherService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class WeatherController {

    @Autowired
    WeatherService weatherService;


    // This API is used to insert data through postman or from frontend using form we can't insert same data again
    @PostMapping("/addData")
    public Response<String> addNewData(@RequestBody WeatherDto weather){
        String isAdded=weatherService.addData(createEntityFromDto(weather));
        if(isAdded.equals("true")){
            return new Response<>("Data inserted successfully");
        }
        else if(isAdded.equals("Data present")){
            return new Response<>(1001,"Data already Present");
        }
        else if(isAdded.equals("Incorrect format")){
            return new Response<>(1002,"Enter data in correct formate");
        }
        else{
            return new Response<>(1003,"Please enter all the fields error inserting data");
//            throw new CustomException("Incorrect fields", "no of fields entered are not matched, try again with correct one", "Required fields: date, time, temprature, humidity, pressure, city");
        }
    }

    //This API is used to updated the data
    @PutMapping("/updateData")
    public Response<Boolean> updateData(@RequestBody WeatherEntity weather){
        WeatherEntity isUpdated=weatherService.updateData(weather);
        if(isUpdated!=null){
            return new Response<>(200,"Data updated successfully");
        }
        else{
            return new Response<>(1001,"error in updating data");
        }
    }

    //API to check wheather the data is inserted into db or not not used in the application just for verification purpose
    @GetMapping("/getData")
    public List<WeatherEntity> getData(){
        return weatherService.getData();
    }

    //API to return the current weather report or data
    @GetMapping(value="/getCurrentData/{city}")
    public Response<WeatherEntity> getCurrentData(@PathVariable(value="city") String city){
        Date date=new Date();
        SimpleDateFormat timeFormatter=new SimpleDateFormat("kk");
        Integer timeIn24Hours = Integer.parseInt(timeFormatter.format(date));
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        WeatherEntity weatherEntities=weatherService.getCurrentData(city,timeIn24Hours,formatter.format(date));
        if(weatherEntities!=null){
            return new Response<>(weatherEntities);
        }
        else{
            return new Response<>(1001,"Data Not available");
        }

    }

    //API to return the next six hours weather data
    @GetMapping(value="/getDayData/{city}/{date}")
    public Response<List<WeatherEntity>> getDayData(@PathVariable(value="city") String city, @PathVariable(value="date") String wdate) throws ParseException {
        List<WeatherEntity> weatherEntities=weatherService.getDayData(city,wdate);
        if(weatherEntities.size()!=0){
            return new Response<>(weatherEntities);
        }
        else{
            return new Response<>(1001,"Data Not available");
        }
    }

    //API to return the next six days weather data
    @GetMapping(value="/getWeekData/{city}/{date}")
    public Response<List<WeekWeatherDto>> getWeekData(@PathVariable(value="city") String city, @PathVariable(value="date") String date)throws ParseException{
        List<WeekWeatherDto> weekWeatherDtos=weatherService.getWeekData(city,date);
        if(weekWeatherDtos.size()!=0){
            return new Response<>(weekWeatherDtos);
        }
        else{
            return new Response<>(1001,"Data Not Available");
        }
    }

    @PostMapping("/setWeatherStatus")
    public void setWeatherStatus(@RequestBody WeatherStatusDto weatherStatusDto){
        weatherService.addWeatherStatus(weatherStatusDto.getWeatherType(),weatherStatusDto.getWeatherImage());
    }

    @GetMapping(value="/getWeatherImage/{weatherType}")
    String getWeatherImage(@PathVariable(value="weatherType") String weatherType){
        return weatherService.getWeatherImage(weatherType);
    }

    public WeatherEntity createEntityFromDto(WeatherDto dto){

        WeatherEntity weatherEntity=new WeatherEntity();
        BeanUtils.copyProperties(dto,weatherEntity);

        return weatherEntity;
    }
}
