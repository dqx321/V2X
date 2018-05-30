package com.intelligent.v2xapp.activity.warning;

import org.litepal.crud.DataSupport;

import java.util.HashMap;

// Created by CIDI daiqinxue on 2018/5/28.
public class LitPalWarnBean extends DataSupport {
     private int  warnType;
     private boolean  isShown;

    @Override
    public String toString() {
        return "LitPalWarnBean{" +
                "warnType=" + warnType +
                ", isShown=" + isShown +
                '}';
    }

    public int getWarnType() {
        return warnType;
    }

    public void setWarnType(int warnType) {
        this.warnType = warnType;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public LitPalWarnBean() {
    }

    public LitPalWarnBean(int warnType, boolean isShown) {
        this.warnType = warnType;
        this.isShown = isShown;
    }
}
