package com.intelligent.v2xapp.activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by daiqinxue on 2017/12/22.
 */
public class MainBroadReceiver extends BroadcastReceiver {
    private MainInfo mainInfo;

    public MainBroadReceiver(MainInfo mainInfo) {
        this.mainInfo=mainInfo;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        mainInfo.onreceiver(intent);

    }


}