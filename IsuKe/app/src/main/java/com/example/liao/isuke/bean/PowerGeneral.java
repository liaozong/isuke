package com.example.liao.isuke.bean;

public class PowerGeneral {

    /**
     * currentElectricity : 10.7
     * totalPower : 100.78
     * currentVoltage : 14
     * todayPower : 50.74
     * currentPower : 10
     */

    private String currentElectricity;
    private String totalPower;
    private String currentVoltage;
    private String todayPower;
    private String currentPower;

    public String getCurrentElectricity() {
        return currentElectricity;
    }

    public void setCurrentElectricity(String currentElectricity) {
        this.currentElectricity = currentElectricity;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }

    public String getCurrentVoltage() {
        return currentVoltage;
    }

    public void setCurrentVoltage(String currentVoltage) {
        this.currentVoltage = currentVoltage;
    }

    public String getTodayPower() {
        return todayPower;
    }

    public void setTodayPower(String todayPower) {
        this.todayPower = todayPower;
    }

    public String getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(String currentPower) {
        this.currentPower = currentPower;
    }
}
