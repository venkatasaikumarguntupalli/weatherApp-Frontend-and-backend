import Vue from 'vue'
import VueRouter from 'vue-router'
import WeatherApp from '../components/WeatherApp'
// import AddData from '../components/AddData'

Vue.use(VueRouter)

const routes = [

    {
      path: '/',
      name: 'WeatherApp',
      component: WeatherApp
    },
    // {
    //     path:'/AddData',
    //     name:'AddData',
    //     component:AddData
    // }
    
  ]
  
  const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
  
  export default router