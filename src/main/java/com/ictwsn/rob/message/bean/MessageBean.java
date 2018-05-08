package com.ictwsn.rob.message.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018-05-03.
 */
public class MessageBean {
    private String device_id;
    private String open_id;
    private String audio_name;

    @Override
    public String toString() {
        return "MessageBean{" +
                "device_id='" + device_id + '\'' +
                ", open_id='" + open_id + '\'' +
                ", audio_name='" + audio_name + '\'' +
                ", audio_status=" + audio_status +
                ", audio_time=" + audio_time +
                ", is_admin=" + is_admin +
                '}';
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getAudio_name() {
        return audio_name;
    }

    public void setAudio_name(String audio_name) {
        this.audio_name = audio_name;
    }

    public int getAudio_status() {
        return audio_status;
    }

    public void setAudio_status(int audio_status) {
        this.audio_status = audio_status;
    }

    public Date getAudio_time() {
        return audio_time;
    }

    public void setAudio_time(Date audio_time) {
        this.audio_time = audio_time;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    private int audio_status;
    private Date audio_time;
    /** 1为支部，0位子女 */
    private int is_admin;
}
