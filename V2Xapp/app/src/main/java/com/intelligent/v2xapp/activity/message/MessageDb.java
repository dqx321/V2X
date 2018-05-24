package com.intelligent.v2xapp.activity.message;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;

import org.litepal.crud.DataSupport;

// Created by CIDI daiqinxue on 2018/5/17.
public class MessageDb extends DataSupport {
    private long time;
    private String type;
    private String describ;

    public MessageDb() {
    }

    @Override
    public String toString() {
        return "MessageDb{" +
                "time=" + time +
                ", type='" + type + '\'' +
                ", describ='" + describ + '\'' +
                '}';
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public MessageDb(long time, String type, String describ) {
        this.time = time;
        this.type = type;
        this.describ = describ;
    }
}
