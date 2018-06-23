package com.ictwsn.rob.provider.utils;

/**
 * Created by Administrator on 2018-06-21.
 */
public class PhyBean {
    private int blood_high;
    private int blood_low;

    public int getBlood_high() {
        return blood_high;
    }

    public void setBlood_high(int blood_high) {
        this.blood_high = blood_high;
    }

    public int getBlood_low() {
        return blood_low;
    }

    public void setBlood_low(int blood_low) {
        this.blood_low = blood_low;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private int hr;
    private String time;
}
