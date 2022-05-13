import {
    mapActions,
    mapGetters
} from 'vuex';
export default {
    name: "WeatherApp",
    data() {
        return {
            time: "",
            date: "",
            wishMessage: "",
            city: "",
            day: "",
            CalenderDate: "",
            showDetailWeather: false,
            showError: false,
            hour:0
        }
    },
    computed: {
        ...mapGetters(['getCurrentData', 'getNextSixHours', 'getNextSixDays', 'getDetailData','getWeatherStatus'])
    },
    created() {
        var today = new Date();
        const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']
        this.date = days[today.getDay()] + ',' + today.getDate() + ' ' + monthNames[today.getMonth()] + ',' + today.getFullYear();
        this.day = today.getDate() + " " + monthNames[today.getMonth()]
        this.time = today.getHours() + ":" + today.getMinutes();
        this.CalenderDate = today.getFullYear() + "-" + String(today.getMonth() + 1).padStart(2, '0') + "-" + String(today.getDate()).padStart(2, '0');
        console.log(today.getHours())
        this.hour=today.getHours()

    },
    methods: {

        ...mapActions(['currentData', 'dayData', 'weekData', 'nextDetailData']),
        calling(){
            this.getCurrentReport();
        },
        async getCurrentReport() {
            if (this.city == "") {
                this.showError = true
            } else {
                this.currentData(this.city.toLowerCase())
              
                this.getHourlyReport()
                this.getWeeklyData()
            }
        },
        async getHourlyReport() {
        
            const url = this.city.toLowerCase() + "/" + this.CalenderDate
            this.dayData(url)

        },
        async getWeeklyData() {
            console.log(this.CalenderDate)
            const url = this.city.toLowerCase() + "/" + this.CalenderDate
            this.weekData(url)
        },
        getDataForDate(date, city) {
            this.showDetailWeather = true;
            const detailUrl = city.toLowerCase() + "/" + date;
            console.log("detail", detailUrl)
            this.nextDetailData(detailUrl)
        },
        disappear() {
            console.log('hi')
            this.showDetailWeather = false;
        },
    }
}