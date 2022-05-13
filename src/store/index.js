import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

const getcurrData='http://localhost:8083/getCurrentData/'
const getHourlyData='http://localhost:8083/getDayData/'
const getWeeklyData='http://localhost:8083/getWeekData/'
const getDetailData='http://localhost:8083/getDayData/'
export default new Vuex.Store({
    state:{
        currentData:[],
        nextSixHours:[],
        nextSixDays:[],
        detailData:[],
        weatherStatus:""
    },
    getters:{
        getCurrentData(state){
            return state.currentData;
        },
        getNextSixHours(state){
            return state.nextSixHours;
        },
        getNextSixDays(state){
            return state.nextSixDays;
        },
        getDetailData(state){
            return state.detailData;
        },
        getWeatherStatus(state){
            return state.weatherStatus;
        }
    },
    mutations:{
        setCurrentData(state,resp){
            state.currentData=resp;
        },
        setNextSixHours(state,resp){
            state.nextSixHours=resp;
        },
        setNextSixDays(state,resp){
            state.nextSixDays=resp;
        },
        setDetailData(state,resp){
            state.detailData=resp;
        },
        setWeatherStatus(state,resp){
            state.weatherStatus=resp;
        }
    },
    actions:{
        setimage({commit},type){
            commit("setWeatherStatus",type)
        },
        currentData({commit,dispatch},city){
            console.log("heloooo")
            axios.get(getcurrData+city)
            .then(resp=>{console.log(resp.data.data);
                commit("setCurrentData",resp.data.data)
                // commit("setWeatherStatus",resp.data.data.weatherStatus)
                dispatch("setimage",resp.data.data.weatherStatus)
            });
        },
        dayData({commit},url){
            axios.get(getHourlyData+url)
            .then(resp=>{console.log(resp.data.data);
            commit("setNextSixHours",resp.data.data)
        })
        },
        weekData({commit},url){
            axios.get(getWeeklyData+url)
            .then(resp=>{console.log(resp.data.data);
            commit("setNextSixDays",resp.data.data)
        })
        },
        nextDetailData({commit},url){
            axios.get(getDetailData+url)
            .then(resp=>{console.log(resp.data.data)
            commit("setDetailData",resp.data.data)
        })
        }
    },

})