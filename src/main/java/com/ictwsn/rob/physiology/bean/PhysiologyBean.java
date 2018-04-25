package com.ictwsn.rob.physiology.bean;

/**
 * Created by Administrator on 2018-04-20.
 */
public class PhysiologyBean {
    private String device_id;
    private int user_sex;

    @Override
    public String toString() {
        return "PhysiologyBean{" +
                "device_id='" + device_id + '\'' +
                ", user_sex=" + user_sex +
                ", user_age=" + user_age +
                ", user_weight=" + user_weight +
                ", user_height=" + user_height +
                '}';
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public int getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(int user_weight) {
        this.user_weight = user_weight;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_height() {
        return user_height;
    }

    public void setUser_height(int user_height) {
        this.user_height = user_height;
    }

    /** 0为男性，1位女性 */
    private int user_age;
    private int user_weight;
    private int user_height;
    private String user_name;
}
