package com.example.liao.isuke.bean;

public class DeviceDetail {

    /**
     * device_sub_num : 0
     * device_sub_alias : MY4141-0-smart plug
     * total_switch_exist : 1
     * switch_type : 0
     * device_sub_status : 1
     * device_sub_id : 4141
     */

    private int device_sub_num;
    private String device_sub_alias;
    private int total_switch_exist;
    private int switch_type;
    private int device_sub_status;
    private int device_sub_id;

    public int getDevice_sub_num() {
        return device_sub_num;
    }

    public void setDevice_sub_num(int device_sub_num) {
        this.device_sub_num = device_sub_num;
    }

    public String getDevice_sub_alias() {
        return device_sub_alias;
    }

    public void setDevice_sub_alias(String device_sub_alias) {
        this.device_sub_alias = device_sub_alias;
    }

    public int getTotal_switch_exist() {
        return total_switch_exist;
    }

    public void setTotal_switch_exist(int total_switch_exist) {
        this.total_switch_exist = total_switch_exist;
    }

    public int getSwitch_type() {
        return switch_type;
    }

    public void setSwitch_type(int switch_type) {
        this.switch_type = switch_type;
    }

    public int getDevice_sub_status() {
        return device_sub_status;
    }

    public void setDevice_sub_status(int device_sub_status) {
        this.device_sub_status = device_sub_status;
    }

    public int getDevice_sub_id() {
        return device_sub_id;
    }

    public void setDevice_sub_id(int device_sub_id) {
        this.device_sub_id = device_sub_id;
    }
}
