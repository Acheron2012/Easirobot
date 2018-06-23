package com.ictwsn.rob.user.bean;

/**
 * Created by Administrator on 2018-06-20.
 */
public class UserBean {
    private int user_id;
    private String user_name;
    private int user_sex;
    private int user_weight;

    @Override
    public String toString() {
        return "UserSimpleBean{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_sex=" + user_sex +
                ", user_weight=" + user_weight +
                ", user_height=" + user_height +
                ", user_age=" + user_age +
                ", user_phone='" + user_phone + '\'' +
                ", identity_card='" + identity_card + '\'' +
                ", user_province='" + user_province + '\'' +
                ", user_city='" + user_city + '\'' +
                ", user_area='" + user_area + '\'' +
                ", user_street='" + user_street + '\'' +
                ", user_address='" + user_address + '\'' +
                '}';
    }

    private int user_height;
    private int user_age;
    private String user_phone;
    private String identity_card;
    private String user_province;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public int getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(int user_weight) {
        this.user_weight = user_weight;
    }

    public int getUser_height() {
        return user_height;
    }

    public void setUser_height(int user_height) {
        this.user_height = user_height;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public String getUser_province() {
        return user_province;
    }

    public void setUser_province(String user_province) {
        this.user_province = user_province;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_area() {
        return user_area;
    }

    public void setUser_area(String user_area) {
        this.user_area = user_area;
    }

    public String getUser_street() {
        return user_street;
    }

    public void setUser_street(String user_street) {
        this.user_street = user_street;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    private String user_city;
    private String user_area;
    private String user_street;
    private String user_address;

}
