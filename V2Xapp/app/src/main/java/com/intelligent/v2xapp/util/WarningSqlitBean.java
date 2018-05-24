package com.intelligent.v2xapp.util;

import org.litepal.crud.DataSupport;

// Created by CIDI daiqinxue on 2018/5/18.
public class WarningSqlitBean extends DataSupport {
    private String carId;
    private String warningtype;
    private String describe ;
    private long time ;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getWarningtype() {
        return warningtype;
    }

    public void setWarningtype(String warningtype) {
        this.warningtype = warningtype;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WarningSqlitBean{" +
                "carId='" + carId + '\'' +
                ", warningtype='" + warningtype + '\'' +
                ", describe='" + describe + '\'' +
                ", time=" + time +
                '}';
    }

    public WarningSqlitBean(String carId, String warningtype, String describe, long time) {
        this.carId = carId;
        this.warningtype = warningtype;
        this.describe = describe;
        this.time = time;
    }

    public WarningSqlitBean() {
    }
}
