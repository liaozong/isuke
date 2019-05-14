package com.example.liao.isuke.bean;

/**
 * 修改用户信息的bean，用来将数据与试图绑定
 */
public class UserInfo {

    public boolean isNullString(Object o) {
        if (o.toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private int userId;//用户ID
    private String phone;//电话号码
    private String nickname;//用户昵称
    private String headPath;//头像路径

    public UserInfo(int userId, String phone, String nickname, String headPath) {
        this.userId = userId;
        this.phone = phone;
        this.nickname = nickname;
        this.headPath = headPath;
    }

    public UserInfo(String phone, String nickname) {
        this.phone = phone;
        this.nickname = nickname;
    }

    public UserInfo(int userId, String phone, String nickname) {
        this.userId = userId;
        this.phone = phone;
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }
}
