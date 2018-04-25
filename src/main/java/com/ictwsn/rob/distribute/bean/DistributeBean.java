package com.ictwsn.rob.distribute.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018-04-21.
 */
public class DistributeBean {
    private int number;
    private String mac_address;

    @Override
    public String toString() {
        return "DistributeBean{" +
                "number=" + number +
                ", mac_address='" + mac_address + '\'' +
                ", device_id='" + device_id + '\'' +
                ", is_allocated=" + is_allocated +
                ", application_time=" + application_time +
                '}';
    }

    private String device_id;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getIs_allocated() {
        return is_allocated;
    }

    public void setIs_allocated(int is_allocated) {
        this.is_allocated = is_allocated;
    }

    public Date getApplication_time() {
        return application_time;
    }

    public void setApplication_time(Date application_time) {
        this.application_time = application_time;
    }

    private int is_allocated;
    private Date application_time;
}
