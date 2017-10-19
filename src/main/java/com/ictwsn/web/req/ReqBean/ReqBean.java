package com.ictwsn.web.req.ReqBean;

/**
 * Created by Administrator on 2017-10-12.
 */
public class ReqBean {


    private int company_id;
    private long day_flow;
    private long month_flow;



    private long year_flow;
    private long all_flow;

    public long getDay_req() {
        return day_req;
    }

    @Override
    public String toString() {
        return "ReqBean{" +
                "company_id=" + company_id +
                ", day_flow=" + day_flow +
                ", month_flow=" + month_flow +
                ", year_flow=" + year_flow +
                ", all_flow=" + all_flow +
                ", day_req=" + day_req +
                ", month_req=" + month_req +
                ", year_req=" + year_req +
                ", all_req=" + all_req +
                '}';
    }

    public void setDay_req(long day_req) {
        this.day_req = day_req;
    }

    public long getMonth_req() {
        return month_req;
    }

    public void setMonth_req(long month_req) {
        this.month_req = month_req;
    }

    public long getYear_req() {
        return year_req;
    }

    public void setYear_req(long year_req) {
        this.year_req = year_req;
    }

    public long getAll_req() {
        return all_req;
    }

    public void setAll_req(long all_req) {
        this.all_req = all_req;
    }

    private long day_req;
    private long month_req;
    private long year_req;
    private long all_req;


    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public long getDay_flow() {
        return day_flow;
    }

    public void setDay_flow(long day_flow) {
        this.day_flow = day_flow;
    }

    public long getMonth_flow() {
        return month_flow;
    }

    public void setMonth_flow(long month_flow) {
        this.month_flow = month_flow;
    }

    public long getYear_flow() {
        return year_flow;
    }

    public void setYear_flow(long year_flow) {
        this.year_flow = year_flow;
    }

    public long getAll_flow() {
        return all_flow;
    }

    public void setAll_flow(long all_flow) {
        this.all_flow = all_flow;
    }
}
