package com.example.liao.isuke.bean;

import java.util.List;

public class PowerData {

    /**
     * powerMonthList : [{"powerMonth":"04","powerValue":27.41},{"powerMonth":"03","powerValue":34.14}]
     * year : 2017
     */

    private String year;
    private List<PowerMonthListBean> powerMonthList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<PowerMonthListBean> getPowerMonthList() {
        return powerMonthList;
    }

    public void setPowerMonthList(List<PowerMonthListBean> powerMonthList) {
        this.powerMonthList = powerMonthList;
    }

    public static class PowerMonthListBean {
        /**
         * powerMonth : 04
         * powerValue : 27.41
         */

        public  PowerMonthListBean(String powerMonth){
            this.powerMonth = powerMonth;
        }

        public PowerMonthListBean(String year,String powerMonth,double powerValue){
            this.powerMonth = powerMonth;
            this.year = year;
            this.powerValue = powerValue;
        }

        private String year;
        private String powerMonth;
        private double powerValue;
        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
        public String getPowerMonth() {
            return powerMonth;
        }

        public void setPowerMonth(String powerMonth) {
            this.powerMonth = powerMonth;
        }

        public double getPowerValue() {
            return powerValue;
        }

        public void setPowerValue(double powerValue) {
            this.powerValue = powerValue;
        }
    }
}
