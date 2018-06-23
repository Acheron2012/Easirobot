package com.ictwsn.bean;

/**
 * Created by Administrator on 2018-03-24.
 */
public class UserBean {
    public int user_id;
    public String user_name;
    public String user_password;
    /**0位男性，1位女性*/
    public int user_sex;

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

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
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

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_city() {
        return user_city;
    }

    public String getLast_answer() {
        return last_answer;
    }

    public void setLast_answer(String last_answer) {
        this.last_answer = last_answer;
    }

    @Override
    public String toString() {
        return "UserSimpleBean{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_sex=" + user_sex +
                ", user_age=" + user_age +
                ", user_phone='" + user_phone + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_city='" + user_city + '\'' +
                ", last_answer='" + last_answer + '\'' +
                '}';
    }

    public void setUser_city(String user_city) {

        this.user_city = user_city;
    }

    public int user_age;
    public String user_phone;
    public String user_address;
    public String user_city;
    public String last_answer;
}
