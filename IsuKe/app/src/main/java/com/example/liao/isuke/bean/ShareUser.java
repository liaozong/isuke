package com.example.liao.isuke.bean;

public class ShareUser {

    @Override
    public String toString() {
        return "ShareUser{" +
                "friend_alias='" + friend_alias + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", share_user_id=" + share_user_id +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    /**
     * friend_alias : 共享用户别名
     * phone : 15919738009
     * nickname :
     * share_user_id : 7
     * avatar :
     */

    private String friend_alias;
    private String phone;
    private String nickname;
    private int share_user_id;
    private String avatar;

    public String getFriend_alias() {
        return friend_alias;
    }

    public void setFriend_alias(String friend_alias) {
        this.friend_alias = friend_alias;
    }

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

    public int getShare_user_id() {
        return share_user_id;
    }

    public void setShare_user_id(int share_user_id) {
        this.share_user_id = share_user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
