package com.example.liao.isuke.bean;

/**
 * Created by liao on 2018/3/28.
 */

public class AppUserInfo {

    /**
     * phone : 15919738009
     * nickname :
     * app_user_id : 7
     * pushAlias : 7_20180409161026
     */

    private String phone;

    @Override
    public String toString() {
        return "AppUserInfo{" +
                "phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", app_user_id=" + app_user_id +
                ", pushAlias='" + pushAlias + '\'' +
                '}';
    }

    private String nickname;
    private int app_user_id;
    private String pushAlias;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getApp_user_id() {
        return app_user_id;
    }

    public void setApp_user_id(int app_user_id) {
        this.app_user_id = app_user_id;
    }

    public String getPushAlias() {
        return pushAlias;
    }

    public void setPushAlias(String pushAlias) {
        this.pushAlias = pushAlias;
    }
}
