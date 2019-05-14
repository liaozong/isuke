package com.example.liao.isuke.bean;

public class OwnDeviceList {

    /**
     * device_name : 333
     * app_user_id : 7
     * alias : MY4127-0-smart plug
     * device_no : A12345678952
     * device_type : 2
     * total_switch_exist : 1
     * device_id : 2
     * type : own
     * device_sub_id : 4127
     */

    private String device_name;

    @Override
    public String toString() {
        return "OwnDeviceList{" +
                "device_name='" + device_name + '\'' +
                ", app_user_id=" + app_user_id +
                ", alias='" + alias + '\'' +
                ", device_no='" + device_no + '\'' +
                ", device_type=" + device_type +
                ", total_switch_exist=" + total_switch_exist +
                ", device_id=" + device_id +
                ", type='" + type + '\'' +
                ", device_sub_id=" + device_sub_id +
                '}';
    }

    private int app_user_id;
    private String alias;
    private String device_no;
    private int device_type;
    private int total_switch_exist;
    private int device_id;
    private String type;
    private int device_sub_id;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public int getApp_user_id() {
        return app_user_id;
    }

    public void setApp_user_id(int app_user_id) {
        this.app_user_id = app_user_id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public int getTotal_switch_exist() {
        return total_switch_exist;
    }

    public void setTotal_switch_exist(int total_switch_exist) {
        this.total_switch_exist = total_switch_exist;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDevice_sub_id() {
        return device_sub_id;
    }

    public void setDevice_sub_id(int device_sub_id) {
        this.device_sub_id = device_sub_id;
    }
}
