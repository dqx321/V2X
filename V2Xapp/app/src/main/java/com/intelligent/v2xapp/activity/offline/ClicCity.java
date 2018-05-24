package com.intelligent.v2xapp.activity.offline;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.intelligent.v2xapp.activity.offline.entity.City;

// Created by CIDI daiqinxue on 2018/5/17.
public interface ClicCity {

    void downCity(MKOLSearchRecord city);
    void downLocation();
}
