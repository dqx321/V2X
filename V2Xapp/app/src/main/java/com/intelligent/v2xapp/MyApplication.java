package com.intelligent.v2xapp;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.intelligent.v2xapp.util.HXPreferenceUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by daiqinxue on 2018/2/5.
 */

public class MyApplication extends LitePalApplication {
    private static Context mContext;
    private static MyApplication instance;
    @Override
    public void onCreate() {
        SDKInitializer.initialize(getApplicationContext());
        instance = this;
        mContext = getApplicationContext();
        HXPreferenceUtils.init(this);
        LitePal.initialize(mContext);
        super.onCreate();
    }

    //实例化一次
    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }
}