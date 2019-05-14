package com.example.liao.isuke.bean;

public class MessageInfo {
    private int message_center_id;
    private  String message_title;
    private String message_detail;

    public MessageInfo(int message_center_id, String message_title, String message_detail) {
        this.message_center_id = message_center_id;
        this.message_title = message_title;
        this.message_detail = message_detail;
    }

    public MessageInfo(String message_title, String message_detail) {
        this.message_title = message_title;
        this.message_detail = message_detail;
    }

    public int getMessage_center_id() {
        return message_center_id;
    }

    public void setMessage_center_id(int message_center_id) {
        this.message_center_id = message_center_id;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getMessage_detail() {
        return message_detail;
    }

    public void setMessage_detail(String message_detail) {
        this.message_detail = message_detail;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "message_center_id=" + message_center_id +
                ", message_title='" + message_title + '\'' +
                ", message_detail='" + message_detail + '\'' +
                '}';
    }
}
