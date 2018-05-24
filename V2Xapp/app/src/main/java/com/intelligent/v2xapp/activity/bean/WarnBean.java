package com.intelligent.v2xapp.activity.bean;

// Created by CIDI daiqinxue on 2018/4/23.
public class WarnBean {
    String warnid;
    long time;
    String describe;

    public WarnBean() {
    }

    @Override
    public String toString() {
        return "WarnBean{" +
                "warnid='" + warnid + '\'' +
                ", time=" + time +
                ", warnType='" + describe + '\'' +
                '}';
    }

    public WarnBean(String warnid, long time, String describe) {
        this.warnid = warnid;
        this.time = time;
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getWarnid() {
        return warnid;
    }

    public void setWarnid(String warnid) {
        this.warnid = warnid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public WarnBean(String warnid, long time) {
        this.warnid = warnid;
        this.time = time;
    }
}
