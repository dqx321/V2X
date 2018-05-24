package com.intelligent.v2xapp.activity.offline.entity;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;

import java.io.Serializable;

/**
 * Created by next on 2016/3/24.
 */
public class City implements Serializable{

    private String name;
    private String pinyin;
    private MKOLSearchRecord code;

    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }

    public City(String name, String pinyin, MKOLSearchRecord code) {
        this.name = name;
        this.pinyin = pinyin;
        this.code = code;
    }

    public MKOLSearchRecord getCode() {
        return code;
    }

    public void setCode(MKOLSearchRecord code) {
        this.code = code;
    }

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
