package com.example.liao.isuke.bean;

public class TimeTask {

    @Override
    public String toString() {
        return "TimeTask{" +
                "timedtask_status='" + timedtask_status + '\'' +
                ", timedtask_days='" + timedtask_days + '\'' +
                ", timedtask_id='" + timedtask_id + '\'' +
                ", timedtask_action='" + timedtask_action + '\'' +
                ", timedtask_time='" + timedtask_time + '\'' +
                '}';
    }

    /**
     * timedtask_status : 1
     * timedtask_days : 1,2,5,6
     * timedtask_id : 11
     * timedtask_action : 1
     * timedtask_time : 0945
     */

    private String timedtask_status;
    private String timedtask_days;
    private String timedtask_id;
    private String timedtask_action;
    private String timedtask_time;

    public String getTimedtask_status() {
        return timedtask_status;
    }

    public void setTimedtask_status(String timedtask_status) {
        this.timedtask_status = timedtask_status;
    }

    public String getTimedtask_days() {
        return timedtask_days;
    }

    public void setTimedtask_days(String timedtask_days) {
        this.timedtask_days = timedtask_days;
    }

    public String getTimedtask_id() {
        return timedtask_id;
    }

    public void setTimedtask_id(String timedtask_id) {
        this.timedtask_id = timedtask_id;
    }

    public String getTimedtask_action() {
        return timedtask_action;
    }

    public void setTimedtask_action(String timedtask_action) {
        this.timedtask_action = timedtask_action;
    }

    public String getTimedtask_time() {
        return timedtask_time;
    }

    public void setTimedtask_time(String timedtask_time) {
        this.timedtask_time = timedtask_time;
    }
}
