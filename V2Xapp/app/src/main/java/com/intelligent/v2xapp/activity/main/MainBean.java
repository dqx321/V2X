package com.intelligent.v2xapp.activity.main;

import java.io.Serializable;

/**
 * Created by 长沙智能驾驶研究院cidi daiqinxue on 2018/3/29.
 */

public class MainBean implements Serializable {
    private String carId;
    private String latitu;
    private String longtitu;
    private String direction ;
    private String speed ;

    public MainBean() {
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "carId='" + carId + '\'' +
                ", latitu='" + latitu + '\'' +
                ", longtitu='" + longtitu + '\'' +
                ", direction='" + direction + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getLatitu() {
        return latitu;
    }

    public void setLatitu(String latitu) {
        this.latitu = latitu;
    }

    public String getLongtitu() {
        return longtitu;
    }

    public void setLongtitu(String longtitu) {
        this.longtitu = longtitu;
    }

    public String getDirection() {
        return direction;
    }

    public MainBean(String carId, String latitu, String longtitu, String direction, String speed) {
        this.carId = carId;
        this.latitu = latitu;
        this.longtitu = longtitu;
        this.direction = direction;
        this.speed = speed;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
