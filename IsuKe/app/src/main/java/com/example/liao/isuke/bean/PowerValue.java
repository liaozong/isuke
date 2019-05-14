package com.example.liao.isuke.bean;

public class PowerValue {

    /**
     * date : 2017/04/21
     * powerValue : 27.41
     */

    private String date;

    @Override
    public String toString() {
        return "PowerValue{" +
                "date='" + date + '\'' +
                ", powerValue=" + powerValue +
                '}';
    }

    private double powerValue;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPowerValue() {
        return powerValue;
    }

    public void setPowerValue(double powerValue) {
        this.powerValue = powerValue;
    }
}
