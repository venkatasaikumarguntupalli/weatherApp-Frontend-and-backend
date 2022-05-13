<template>
<div class="main" :key="this.getWeatherStatus ? this.getWeatherStatus : 'key2'" v-bind:style="(this.getWeatherStatus==='sunny') ? { 'background-image': 'url(' + '/assets/sunnygif.gif'  + ')'} :
 (this.getWeatherStatus==='clouds') ? { 'background-image': 'url('+  '/assets/cloudy.gif' + ')' }  : 
 (this.getWeatherStatus==='snow') ? { 'background-image': 'url('+  '/assets/snow.gif' + ')' } : 
 (this.getWeatherStatus==='clear') ? { 'background-image': 'url('+  '/assets/clear.gif' + ')' } :
 (this.getWeatherStatus==='thunder') ? { 'background-image': 'url('+  '/assets/thunder.gif' + ')' } :
 (this.getWeatherStatus==='rainy') ? { 'background-image': 'url('+  '/assets/rain.gif' + ')' } :


 { 'background-image': 'url('+  '/assets/background.jpeg' + ')' }">
    <div class="left">
       {{getWeatherImage}}
        <div class="Intro">
            <div class="heading"><b>{{this.time}}</b></div>
            <div class="date"><b>{{this.date}}</b></div>
            <div v-if="hour>=1 && hour<12" class="wishMessage">
                <img src="@/assets/Goodmorining.jpeg" alt="goodmorning" class="wishImage">
                <div class="heading"><b>Good Morning, Venkat!</b></div>
            </div>
            <div v-else-if="hour>=12 && hour<17" class="wishMessage">
                <img src="@/assets/Goodafternoon.jpeg" alt="goodafternoon" class="wishImage">
                <div class="heading"><b>Good Afternoon, Venkat!</b></div>
            </div>
            <div v-else class="wishMessage">
                <img src="@/assets/Goodevening.jpeg" alt="goodafternoon" class="wishImage">
                <div class="heading"><b>Good Evening, Venkat!</b></div>
            </div>
        </div>
        <div class="error" v-show="showError">
            <img src="@/assets/error.jpeg" alt="error">
        </div>
        <div class="hourlyWeather" v-show="!showError">
            <div class="hour" v-for="weather in getNextSixHours" :key="weather">
                <div>{{weather.time}}:00</div>
                <div class="weatherImages"><img :src="weather.weatherImage"></div>
                <div><b>{{weather.temprature}}</b>&#8451;</div>
                <div>{{weather.weatherStatus}}</div>
                <div>Humididty:{{weather.humidity}}</div>
                <div>Pressure: {{weather.pressure}}</div>
            </div>
        </div>
        <div class="weeklyWeather" v-show="!showError">
            <div class="hour" v-for="weather in getNextSixDays" :key="weather" @click="getDataForDate(weather.date,weather.city)">
                <div class="dates">{{weather.date}}</div>
                <div class="weatherImages"><img :src="weather.weatherImage"></div>
                <div><b>{{weather.avgTemprature}}</b>&#8451;</div>
                <div>{{weather.weatherStatus}}</div>
                <div>Min temp: {{weather.minTemperature}}&#8451;</div>
                <div>Max temp: {{weather.maxTemperature}}&#8451;</div>
                <div>pressure: {{weather.avgPressure}}</div>
                <div>Humidity: {{weather.avgHumidity}}</div>

            </div>
        </div>
        <div v-show="showDetailWeather" class="detailWeather">
            <div><img src="@/assets/close.png" class="wishImage" @click="disappear()"></div>
            <div class="hours" v-for="weather in getDetailData" :key="weather">
                <div class="dates">{{weather.date}}</div>
                <div class="weatherImages"><img :src="weather.weatherImage"></div>
                <div>{{weather.temprature}}&#8451;</div>
                <div>{{weather.time}}:00</div>
                <div>{{weather.weatherStatus}}</div>
            </div>
        </div>

    </div>
    <div class="right">
        <div class="search">
            <img class="search-btn" src="@/assets/search.png" autocomplete="off" @click="calling()">
            <input type="text" class="search-bar" placeholder="Enter the city" v-model="city" @keyup.enter="calling()">
        </div>
        <div  class="current">
            <div>{{getCurrentData.city}}</div>
            <div class="date"><b>Today, {{this.day}}</b></div>
            <div class="weatherImage"><img :src="getCurrentData.weatherImage"></div>
            <div class="temperature"><b>{{getCurrentData.temprature}}&#8451;</b></div>
            <div>{{getCurrentData.weatherStatus}}</div>
            <div class="wind">
                <img src="@/assets/wind.png" height="30px" width="30px">
                <div class="waste"> wind | {{getCurrentData.pressure}} km/h</div>
            </div>
            <div class="wind">
                <img src="@/assets/humidity.png" height="30px" width="30px">
                <div class="waste"> Hum | {{getCurrentData.humidity}} %</div>
            </div>
        </div>
    </div>
</div>
</template>

<script src="./js/WeatherApp.js"></script>

<style scoped>

.main {
    display: flex;
    flex-direction: row;
    justify-content: center;
    padding: 10px;
    background-repeat: no-repeat;
    background-size: 100%;
    height: 785px;
}
.btn{
    width:100px;
    height:50px;
}
.left {
    display: flex;
    flex-direction: column;
    padding: 20px;
    gap: 30px;
    margin-top: 50px;
    height: 410px;
    overflow: scroll;
    overflow-y: scroll;
}

.wishMessage {
    display: flex;
    align-items: center;
}

.wishImage {
    height: 40px;
    width: 40px;
    border-radius: 50%;
}

.Intro {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.heading {
    color: #5c92ff;
    font-size: 20px;
}

.date {
    color: #363E64;
    font-size: 12px;
}

.dates {
    color: #363E64;
    font-size: 11px;
}

.search {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-left: 10px;
    background-color: white;
    border-radius: 24px;
    padding: 0.4em 1em;

}

button {
    margin: 0.5em;
    border-radius: 50%;
    border: none;
    height: 35x;
    width: 35px;
    outline: none;
    cursor: pointer;
    transition: 0.2s ease-in-out;
}

.search-bar {
    border: none;
    outline: none;
    padding: 0.4em 1em;
    color: black;
    font-family: inherit;
    font-size: 105%;
    height: 10px;
    width: 180px;
    border-radius: 24px;

}

::placeholder {
    color: black;
}

.hourlyWeather {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 10px;
}

.hour {
    background: white;
    padding: 10px;
    border-radius: 10px;
    overflow: hidden;
    height: 87px;
    width: 60px;
    transition: 2s;
}

.hour:hover {
    background: #9CBDFF;
    cursor: pointer;
    color: white;
    height: 200px;
    width: 150px;
}

.hours {
    background: white;
    padding: 10px;
    border-radius: 10px;
    overflow: hidden;
    height: 100px;
    width: 60px;
    transition: 2s;
}

.wind {
    display: flex;
    flex: row;
    flex-wrap: wrap;
    align-items: center;
}

.current {
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    align-items: center;
    background: #5c92ff;
    border-radius: 10px;
    color: white;
    margin: 10px;
    padding: 10px;
    gap: 10px;
}

.right {
    display: flex;
    flex-direction: column;
    margin-left: 10px;
    margin-top: 50px;
}

.temperature {
    font-size: 40px;
    color: white;
}

.other {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-between;
    margin: 10px;
    padding: 10px;
    background: #FD9AC0;
    border-radius: 10px;
    color: white;

}

.other-city .othercity_name {
    display: flex;
    flex-direction: column;
    gap: 10px;
    color: white;
}

.waste {
    margin-left: 5px;
}

.weeklyWeather {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 10px;
}

.detailWeather {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 10px;
    background: #333232;
    opacity: 0.8; 
    padding: 20px;
}

.weatherImage img {
    height: 70px;
    width: 70px;
}

.weatherImages img {
    height: 50px;
    width: 50px;
}
</style>
