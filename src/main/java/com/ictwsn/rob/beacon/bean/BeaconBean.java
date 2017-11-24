package com.ictwsn.rob.beacon.bean;

import java.util.Date;

/**
 * Created by Administrator on 2017-04-16.
 */
public class BeaconBean {
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getScenario() {
        return scenario;
    }

    public Date getLast_time() {
        return last_time;
    }

    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    private String keywords;

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    private String scenario;
    private Date last_time;
    private int history;

}
