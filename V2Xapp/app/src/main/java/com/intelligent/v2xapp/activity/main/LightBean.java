package com.intelligent.v2xapp.activity.main;

import java.io.Serializable;

// Created by CIDI daiqinxue on 2018/4/21.
public class LightBean implements Serializable{
    private String carid;
    private String style;
    private String time;

    public LightBean() {
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LightBean(String carid, String style, String time) {
        this.carid = carid;
        this.style = style;
        this.time = time;
    }
}
