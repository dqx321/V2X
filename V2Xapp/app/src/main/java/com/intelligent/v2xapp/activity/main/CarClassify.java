package com.intelligent.v2xapp.activity.main;

import com.baidu.mapapi.map.Marker;

// Created by CIDI daiqinxue on 2018/4/23.
public class CarClassify {

    private String Id;
    private Marker marker;

    public CarClassify(String Id, Marker marker) {
        this.Id = Id;
        this.marker = marker;
    }

    public String getId() {
        return Id;
    }

    public Marker getMarker() {
        return marker;
    }
}
