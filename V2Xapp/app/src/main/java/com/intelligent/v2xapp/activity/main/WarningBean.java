package com.intelligent.v2xapp.activity.main;

import java.io.Serializable;

/**
 * Created by 长沙智能驾驶研究院cidi daiqinxue on 2018/3/29.
 */

public class WarningBean implements Serializable{
    private String carId;
    private String warningtype;
    private String describe ;
    private long time ;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public WarningBean(String carId, String warningtype, String describe) {
        this.carId = carId;
        this.warningtype = warningtype;
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "WarningBean{" +
                "carId='" + carId + '\'' +
                ", warningtype='" + warningtype + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }

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

    public WarningBean() {
    }
}
