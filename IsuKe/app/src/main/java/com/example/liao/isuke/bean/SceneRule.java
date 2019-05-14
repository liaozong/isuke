package com.example.liao.isuke.bean;

import java.util.List;

public class SceneRule {
    private List<HumidityListBean> humidityList;
    private List<IlluminationListBean> illuminationList;
    private List<SencenConditionListBean> sencenConditionList;
    private List<SunRiseSunsetListBean> sunRiseSunsetList;
    private List<TemperatureListBean> temperatureList;
    private List<AirQualityListBean> airQualityList;
    private List<WeatherListBean> weatherList;

    public List<HumidityListBean> getHumidityList() {
        return humidityList;
    }

    public void setHumidityList(List<HumidityListBean> humidityList) {
        this.humidityList = humidityList;
    }

    public List<IlluminationListBean> getIlluminationList() {
        return illuminationList;
    }

    public void setIlluminationList(List<IlluminationListBean> illuminationList) {
        this.illuminationList = illuminationList;
    }

    public List<SencenConditionListBean> getSencenConditionList() {
        return sencenConditionList;
    }

    public void setSencenConditionList(List<SencenConditionListBean> sencenConditionList) {
        this.sencenConditionList = sencenConditionList;
    }

    public List<SunRiseSunsetListBean> getSunRiseSunsetList() {
        return sunRiseSunsetList;
    }

    public void setSunRiseSunsetList(List<SunRiseSunsetListBean> sunRiseSunsetList) {
        this.sunRiseSunsetList = sunRiseSunsetList;
    }

    public List<TemperatureListBean> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(List<TemperatureListBean> temperatureList) {
        this.temperatureList = temperatureList;
    }

    public List<AirQualityListBean> getAirQualityList() {
        return airQualityList;
    }

    public void setAirQualityList(List<AirQualityListBean> airQualityList) {
        this.airQualityList = airQualityList;
    }

    public List<WeatherListBean> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherListBean> weatherList) {
        this.weatherList = weatherList;
    }

    public static class HumidityListBean {
        /**
         * humidity_id : 36
         * value : 干燥.潮湿
         */

        private int humidity_id;
        private String value;

        public int getHumidity_id() {
            return humidity_id;
        }

        public void setHumidity_id(int humidity_id) {
            this.humidity_id = humidity_id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class IlluminationListBean {
        /**
         * illumination_id : 37
         * value : 一级亮.二级亮
         */

        private int illumination_id;
        private String value;

        public int getIllumination_id() {
            return illumination_id;
        }

        public void setIllumination_id(int illumination_id) {
            this.illumination_id = illumination_id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SencenConditionListBean {
        /**
         * dicts_id : 30
         * value : 日出日落
         */

        private int dicts_id;
        private String value;

        public int getDicts_id() {
            return dicts_id;
        }

        public void setDicts_id(int dicts_id) {
            this.dicts_id = dicts_id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SunRiseSunsetListBean {
        /**
         * riseOrset_id : 41
         * value : 日出.日落
         */

        private int riseOrset_id;
        private String value;

        public int getRiseOrset_id() {
            return riseOrset_id;
        }

        public void setRiseOrset_id(int riseOrset_id) {
            this.riseOrset_id = riseOrset_id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class TemperatureListBean {
        /**
         * value : -1.30
         * temperature_id : 42
         */

        private String value;
        private int temperature_id;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getTemperature_id() {
            return temperature_id;
        }

        public void setTemperature_id(int temperature_id) {
            this.temperature_id = temperature_id;
        }
    }

    public static class AirQualityListBean {
        /**
         * airQuality_id : 39
         * value : 优.良.差.较差
         */

        private int airQuality_id;
        private String value;

        public int getAirQuality_id() {
            return airQuality_id;
        }

        public void setAirQuality_id(int airQuality_id) {
            this.airQuality_id = airQuality_id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class WeatherListBean {
        /**
         * value : 晴.多云.小雨.大雨.小雪.雾天
         * weather_id : 40
         */

        private String value;
        private int weather_id;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getWeather_id() {
            return weather_id;
        }

        public void setWeather_id(int weather_id) {
            this.weather_id = weather_id;
        }
    }
}
