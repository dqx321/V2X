package com.intelligent.v2xapp.activity.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daiqinxue on 2018/2/5.
 */


public class BaseActivity extends Activity {
    private boolean mIsNeedBaseAnim = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //隐藏状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //保持常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        /**
//         * 横竖屏设置
//        SCREEN_ORIENTATION_LANDSCAPE
//         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        //禁止弹出系统软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //沉浸式管理 状态栏 和导航栏
//        ImmersionBar.with(this)
//                //透明导航栏，不写默认黑色
//                .transparentNavigationBar()
//                .init();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private static final int PERMISSON_REQUESTCODE = 0;
    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                isNeedCheck = false;

            }
        }
    }
    protected void openActivityByIntent(Intent intent) {
        startActivity(intent);
    }

    protected void openActivityNoIntent(Class<?> mclass) {
        Intent intent = new Intent(this, mclass);
        startActivity(intent);
    }
    public void back(View view) {
        finish();
    }
    protected void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (mIsNeedBaseAnim) {
            overridePendingTransition(com.vise.common_base.R.anim.push_in_right, com.vise.common_base.R.anim.push_out_left);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (mIsNeedBaseAnim) {
            overridePendingTransition(com.vise.common_base.R.anim.push_in_right, com.vise.common_base.R.anim.push_out_left);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.vise.common_base.R.anim.push_in_left, com.vise.common_base.R.anim.push_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setIsNeedBaseAnim(boolean isNeedBaseAnim) {
        mIsNeedBaseAnim = isNeedBaseAnim;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}




















































