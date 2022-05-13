package com.example.weatherApp.service;

import com.example.weatherApp.customExceptions.CustomException;
import com.example.weatherApp.dto.WeekWeatherDto;
import com.example.weatherApp.entity.WeatherEntity;
import com.example.weatherApp.entity.WeatherStatusEntity;
import com.example.weatherApp.repository.WeatherRepository;
import com.example.weatherApp.repository.WeatherStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import static org.apache.commons.validator.GenericValidator.isDate;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeatherServiceImpl implements WeatherService {

    Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    WeatherStatusRepository weatherStatusRepository;

    @Override
    //  @CachePut(value="dayWeather",key="#weather.city+#weather.date")
    // Functionality for adding the data.
    // It checks wheather the data is present if present throws an error message else insert the new data
    public String addData(WeatherEntity weather) {
        System.out.println(isDate(weather.getDate(),"yyyy-mm-dd",true));

        WeatherEntity weatherEntities=weatherRepository.findFirstByCityAndDateAndTime(weather.getCity(),weather.getDate(),weather.getTime());
        if(weather.getTime() != null && weather.getDate()!=null && weather.getDate() != "" && weather.getTemprature()!=null && weather.getHumidity()!=null && weather.getPressure()!=null && weather.getCity()!=null) {
            if(weather.getTime()<1 || weather.getTime()>24){
                CustomException time=new CustomException("Time must be with in the range 1-24 ","Error while adding","Failed");
                logger.error("exception occured: "+": "+time.getMessage()+". "+time.getDetails()+". "+time.getHint());
                return "Incorrect format";
            }
            else if(weather.getHumidity()<1 || weather.getHumidity()>100){
//                throw new CustomException("Humidity must be with in 1-100","Error in adding","Failed");
                CustomException humidity= new CustomException("Humidity must be with in 1-100","Error in adding","Failed");
                logger.error("Exception occured"+": "+humidity.getMessage()+". "+humidity.getDetails()+". "+humidity.getHint());
                return "Incorrect format";
            }
            else if(weather.getPressure()<1 || weather.getPressure()>100){
                CustomException Pressure= new CustomException("Pressure must be with in 1-100","Error in adding","Failed");
                logger.error("Exception occured"+": "+Pressure.getMessage()+". "+Pressure.getDetails()+". "+Pressure.getHint());
                return "Incorrect format";
            }
            else if (isDate(weather.getDate(), "yyyy-MM-dd", true) == false){
                CustomException date= new CustomException("Please check the date the formate must be 'yyyy-mm-dd'","Error in adding","Failed");
                logger.error("Exception occured"+": "+date.getMessage()+". "+date.getDetails()+". "+date.getHint());
                return "Incorrect format";
            }
            else{
            if (weatherEntities==null) {
                weather.setWeatherStatus(getWeatherCondition(weather.getTemprature(), weather.getHumidity()));
                weather.setWeatherImage(getWeatherImage(weather.getWeatherStatus()));
                weatherRepository.save(weather);
                return "true";
            }
            else {
                return "Data present";
            }}
        }
        else{
            return "Incorrect";
        }
    }

    //Functionality for updating the existing data
    //If data is present update will happen successfully
    //Else return null
    @CachePut(value="weather", key="#weather.city+#weather.time+#weather.date")
    public WeatherEntity updateData(WeatherEntity weather){
        WeatherEntity weatherEntities=weatherRepository.findFirstByCityAndDateAndTime(weather.getCity(),weather.getDate(),weather.getTime());
            if(weatherEntities!=null) {
                weatherEntities.setTemprature(weather.getTemprature());
                weatherEntities.setHumidity(weather.getHumidity());
                weatherEntities.setPressure(weather.getPressure());
                weatherEntities.setWeatherStatus(getWeatherCondition(weather.getTemprature(), weather.getHumidity()));
                weatherEntities.setWeatherImage(getWeatherImage(getWeatherCondition(weather.getTemprature(), weather.getHumidity())));
                weatherRepository.save(weatherEntities);
                return weatherEntities;
            }
            else{
                return null;
            }
    }

    @Override
    public List<WeatherEntity> getData(){
        return weatherRepository.findAll();
    }


    //Functionality to get the current weatherData.
    //Get data by City,Date,time and return it.
    //It also store in redis cache for the first time.
    @Override
    @Cacheable(value="weather",key="#city+#time+#date")
    public WeatherEntity getCurrentData(String city,Integer time,String date) {
        WeatherEntity weatherEntities=weatherRepository.findFirstByCityAndDateAndTime(city,getDate(),getTime());
        if(weatherEntities!=null){
            logger.info(String.valueOf(weatherEntities));
            return weatherEntities;
        }
        else{
            return null;
        }
    }


    //Functionality to get the next six hours data
    //If current time is 23or24 It update to the next date else continues with the current date and time will be updated for every loop
    @Override
//    @Cacheable(value="dayWeather",key="#city+#date")
    public List<WeatherEntity> getDayData(String city, String date) throws ParseException {
        String nextDate = getNextDate(date);
        Integer timeIn24Hours = getTime();
        logger.info(String.valueOf(timeIn24Hours));
        List<WeatherEntity> weatherEntity = new ArrayList<>();
        for(int i=1;i<7;i++)
        {
            switch(timeIn24Hours){
                case 24:
                    timeIn24Hours=2;
                    date=nextDate;
                    break;
                case 23:
                    timeIn24Hours=1;
                    date=nextDate;
                    break;
                default:
                    timeIn24Hours+=2;
                    break;

            }
            WeatherEntity weatherEntities=weatherRepository.findFirstByCityAndDateAndTime(city,date,timeIn24Hours);
            if(weatherEntities!=null){
                weatherEntity.add(weatherEntities);
            }
            else{
                WeatherEntity weatherEntity1=new WeatherEntity();
                weatherEntity1.setId("N/A");
                weatherEntity1.setCity("N/A");
                weatherEntity1.setDate(date);
                weatherEntity1.setTime(timeIn24Hours);
                weatherEntity1.setPressure(0);
                weatherEntity1.setHumidity(0);
                weatherEntity1.setTemprature(0);
                weatherEntity1.setWeatherImage("https://cdn-icons-png.flaticon.com/512/16/16096.png");
                weatherEntity.add(weatherEntity1);
            }

        }
        return weatherEntity;
    }

    //Functionality to get next Six days weatherData
    //for every loop it calculates the avgtemp,avgPre,avgHumid,minTemp,maxTemp for that particular date
    @Override
    public List<WeekWeatherDto> getWeekData(String city, String date) throws ParseException {
        List<WeekWeatherDto> weekWeatherDto=new ArrayList<>();
        for(int i=1;i<7;i++){
            Integer avgTemperature=0,avgHumidity=0,avgPressure=0,minTemperature=0,maxTemperature=0;
            date = getNextDate(date);
            logger.info(date);
            List<WeatherEntity> weatherEntities=weatherRepository.findByCityAndDate(city,date);
            WeekWeatherDto weekWeatherDto1=new WeekWeatherDto();
            if(weatherEntities.size()!=0) {
                for (WeatherEntity weatherEntity:weatherEntities) {
                    avgTemperature = avgTemperature + weatherEntity.getTemprature();
                    avgPressure = avgPressure + weatherEntity.getPressure();
                    avgHumidity = avgHumidity + weatherEntity.getHumidity();
                    if (weatherEntity.getTemprature() <= minTemperature) {
                        minTemperature = weatherEntity.getTemprature();
                    }
                    if (weatherEntity.getTemprature() >= maxTemperature) {
                        maxTemperature = weatherEntity.getTemprature();
                    }
                }
                String weatherStatus=getWeatherCondition(avgTemperature/weatherEntities.size(),avgHumidity/weatherEntities.size());
                weekWeatherDto1.setDate(date);
                weekWeatherDto1.setCity(city);
                weekWeatherDto1.setAvgHumidity(avgHumidity/weatherEntities.size());
                weekWeatherDto1.setAvgPressure(avgPressure/weatherEntities.size());
                weekWeatherDto1.setAvgTemprature(avgTemperature/weatherEntities.size());
                weekWeatherDto1.setMaxTemperature(maxTemperature);
                weekWeatherDto1.setMinTemperature(minTemperature);
                weekWeatherDto1.setWeatherStatus(weatherStatus);
                weekWeatherDto1.setWeatherImage(getWeatherImage(weatherStatus));
                weekWeatherDto.add(weekWeatherDto1);
            }
            else {
                weekWeatherDto1.setCity(city);
                weekWeatherDto1.setDate(date);
                weekWeatherDto1.setAvgHumidity(0);
                weekWeatherDto1.setAvgPressure(0);
                weekWeatherDto1.setAvgTemprature(0);
                weekWeatherDto1.setMaxTemperature(0);
                weekWeatherDto1.setMinTemperature(0);
                weekWeatherDto1.setWeatherStatus("N/A");
                weekWeatherDto1.setWeatherImage("https://cdn-icons-png.flaticon.com/512/16/16096.png");
                weekWeatherDto.add(weekWeatherDto1);

            }

        }
        return weekWeatherDto;
    }

    @Override
    public void addWeatherStatus(String weatherType, String weatherImage) {
        List<WeatherStatusEntity>weatherStatusEntities = weatherStatusRepository.findByWeatherType(weatherType);
        if(weatherStatusEntities.size()==0){
            WeatherStatusEntity weatherStatusEntity=new WeatherStatusEntity();
            weatherStatusEntity.setWeatherType(weatherType);
            weatherStatusEntity.setWeatherImage(weatherImage);
            weatherStatusRepository.save(weatherStatusEntity);
        }
        else{
            weatherStatusEntities.get(0).setWeatherImage(weatherImage);
            weatherStatusRepository.save(weatherStatusEntities.get(0));
        }
    }

    @Override
    public String getWeatherImage(String weatherType) {
        return weatherStatusRepository.findByWeatherType(weatherType).get(0).getWeatherImage();
    }

    //This method is used to find the weather status like clouds,rainy,snow etc using temperature,humidity
        public String getWeatherCondition(int temperature, int relativeHumidity) {
            logger.info(String.valueOf(temperature));
            logger.info(String.valueOf(relativeHumidity));
        String weatherCondition ="";
        if(temperature < 0 && relativeHumidity<=10)
            weatherCondition = "snow";
        else if(temperature<=30 && relativeHumidity>10 && relativeHumidity<=40)
            weatherCondition="clear";
        else if(temperature<0 || temperature>0 && relativeHumidity>90 && relativeHumidity<100)
            weatherCondition="thunder";
        else if (temperature<0 || temperature>0 && relativeHumidity >80 && relativeHumidity<=90)
            weatherCondition = "rainy";
        else if(temperature>0 && relativeHumidity>=50 && relativeHumidity<80)
            weatherCondition="clouds";
        else if(temperature>30 && relativeHumidity>40 && relativeHumidity<50)
            weatherCondition = "sunny";
        else
            weatherCondition="clear";
        return weatherCondition;
    }

    public Integer getTime(){
        Date date=new Date();
        SimpleDateFormat timeFormatter=new SimpleDateFormat("kk");
        Integer timeIn24Hours = Integer.parseInt(timeFormatter.format(date));
        logger.info(String.valueOf(timeIn24Hours));
        return timeIn24Hours;
    }

    public String getDate(){
        Date date=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public String getNextDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = formatter.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dates);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String nextDate = formatter.format(calendar.getTime());
        return nextDate;
    }
}
