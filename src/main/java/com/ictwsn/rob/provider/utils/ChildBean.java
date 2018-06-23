package com.ictwsn.rob.provider.utils;

/**
 * Created by Administrator on 2018-06-11.
 */
public class ChildBean {
    private int child_id;
    private String open_id;

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public long getChild_phone() {
        return child_phone;
    }

    public void setChild_phone(long child_phone) {
        this.child_phone = child_phone;
    }

    public String getChild_address() {
        return child_address;
    }

    public void setChild_address(String child_address) {
        this.child_address = child_address;
    }

    public String getChild_email() {
        return child_email;
    }

    public void setChild_email(String child_email) {
        this.child_email = child_email;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    private String device_id;
    private String child_name;
    private long child_phone = 0;
    private String child_address;
    private String child_email;
    private String relation;

}
