package com.intelligent.v2xapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by daiqinxue on 17/2/14.
 */

public class HXPreferenceUtils {

    public static final String PREFERENCE_NAME = "saveInfo";
    private static SharedPreferences mSharedPreferences;
    private static HXPreferenceUtils mPreferenceUtils;
    private static SharedPreferences.Editor editor;

    private String isInter = "isInter";
    private String isFirst = "isFirst";
    private String isfirstSetWarn = "isfirstSetWarn";
    private String account = "account";
    private String password = "password";
    private String total = "total";
    private String udpip = "udpip";
    private String udpiport = "udpiport";
    public static String MUSIC_FLAG = "music_flag";


    public String getudpiport() {
        return mSharedPreferences.getString(udpiport, "");
    }

    public void setudpiport(String port) {
        editor.putString(udpiport, port);
        editor.commit();
    } public boolean getIsfirstSetWarn() {
        return mSharedPreferences.getBoolean(isfirstSetWarn, true);
    }

    public void setIsfirstSetWarn(boolean isfirstSet) {
        editor.putBoolean(isfirstSetWarn, isfirstSet);
        editor.commit();
    }
    public String getaccount() {
        return mSharedPreferences.getString(account, "");
    }

    public void setaccount(String accoun) {
        editor.putString(account, accoun);
        editor.commit();
    }

    public String getudpip() {
        return mSharedPreferences.getString(udpip, "");
    }

    public void setudpip(String ip) {
        editor.putString(udpip, ip);
        editor.commit();
    }

    public String gettotal() {
        return mSharedPreferences.getString(total, "0");
    }

    public void settotal(String accoun) {
        editor.putString(total, accoun);
        editor.commit();
    }

    public String getpassword() {
        return mSharedPreferences.getString(password, "");
    }

    public void setpassword(String passwor) {
        editor.putString(password, passwor);
        editor.commit();
    }

    public boolean getIsinter() {
        return mSharedPreferences.getBoolean(isInter, false);
    }

    public void setIsinter(boolean isinter) {
        editor.putBoolean(isInter, isinter);
        editor.commit();
    }

    public boolean getisFirst() {
        return mSharedPreferences.getBoolean(isFirst, false);
    }

    public void setisFirst(boolean isinter) {
        editor.putBoolean(isFirst, isinter);
        editor.commit();
    }

    public HXPreferenceUtils(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context cxt) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new HXPreferenceUtils(cxt);
        }
    }

    public static void clear() {

        mSharedPreferences.edit().clear().commit();


    }


    /**
     * 单例模式，获取instance实例
     *
     * @param
     * @return
     */
    public static HXPreferenceUtils getInstance() {
        if (mPreferenceUtils == null) {
            throw new RuntimeException("please init first!");
        }

        return mPreferenceUtils;
    }


}
