package com.example.liao.isuke.bean;

public class DeviceInfo {

    /**
     * device_user_id : 8
     * device_belong_type : 0
     * device_alias : MY4141-0-smart plug
     * device_name : 534543
     * firmware_version : 1.0.2
     * device_type : 3
     * software_version : 1.0.1
     * device_id : 3
     * device_sub_id : 4141
     * device_mac : B12345467899
     */

    private int device_user_id;
    private int device_belong_type;
    private String device_alias;
    private String device_name;
    private String firmware_version;
    private int device_type;
    private String software_version;
    private int device_id;
    private int device_sub_id;
    private String device_mac;

    public int getDevice_user_id() {
        return device_user_id;
    }

    public void setDevice_user_id(int device_user_id) {
        this.device_user_id = device_user_id;
    }

    public int getDevice_belong_type() {
        return device_belong_type;
    }

    public void setDevice_belong_type(int device_belong_type) {
        this.device_belong_type = device_belong_type;
    }

    public String getDevice_alias() {
        return device_alias;
    }

    public void setDevice_alias(String device_alias) {
        this.device_alias = device_alias;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getFirmware_version() {
        return firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        this.firmware_version = firmware_version;
    }

    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public String getSoftware_version() {
        return software_version;
    }

    public void setSoftware_version(String software_version) {
        this.software_version = software_version;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getDevice_sub_id() {
        return device_sub_id;
    }

    public void setDevice_sub_id(int device_sub_id) {
        this.device_sub_id = device_sub_id;
    }

    public String getDevice_mac() {
        return device_mac;
    }

    public void setDevice_mac(String device_mac) {
        this.device_mac = device_mac;
    }
}
