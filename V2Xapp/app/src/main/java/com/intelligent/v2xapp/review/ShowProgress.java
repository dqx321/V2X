package com.intelligent.v2xapp.review;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.intelligent.v2xapp.R;


/**
 * Created by daiqinxue on 2017/12/13.
 */


public class ShowProgress extends Dialog {

    private static final int CHANGE_TITLE_WHAT = 1;
    private static final int CHNAGE_TITLE_DELAYMILLIS = 300;
    private static final int MAX_SUFFIX_NUMBER = 3;
    private static final char SUFFIX = '.';
    private Animation animation;

    private ImageView img;
    private RotateAnimation mAnim;


    private Handler handler = new Handler() {
        private int num = 0;


        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_TITLE_WHAT) {
                StringBuilder builder = new StringBuilder();
                if (num >= MAX_SUFFIX_NUMBER) {
                    num = 0;
                }
                num++;
                for (int i = 0; i < num; i++) {
                    builder.append(SUFFIX);
                }
//                tv_point.setText(builder.toString());
                if (isShowing()) {
                    handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHNAGE_TITLE_DELAYMILLIS);
                } else {
                    num = 0;
                }
            }
        }

        ;
    };


    public ShowProgress(Context context) {
        super(context, R.style.loading_dialog);
        animation = AnimationUtils.loadAnimation(context, R.anim.progressdialog);

        init();
    }





    private void init() {
        setContentView(R.layout.layout_loading_dialog);
        img = (ImageView) findViewById(R.id.img);
//        detail_tv = (TextView) findViewById(R.id.detail_tv);
//        tv_point = (TextView) findViewById(R.id.tv_point);
//        ImageUtil.loadImage(R.drawable.selectspinner, img);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        initAnim();
//        getWindow().setWindowAnimations(R.anim.progressdialog);
    }


    private void initAnim() {
// mAnim = new RotateAnimation(360, 0, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
        mAnim = new RotateAnimation(0, 360, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
        mAnim.setDuration(2000);
        mAnim.setRepeatCount(Animation.INFINITE);
        mAnim.setRepeatMode(Animation.RESTART);
        mAnim.setStartTime(Animation.START_ON_FIRST_FRAME);
    }


    @Override
    public void show() {//在要用到的地方调用这个方法
        img.startAnimation(animation);
        handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
        super.show();
    }


    @Override
    public void dismiss() {
        mAnim.cancel();
        super.dismiss();
    }


    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
//            detail_tv.setText("正在加载");
        } else {
//            detail_tv.setText(title);
        }
    }


    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }


    public static void dismissDialog(ShowProgress loadingDialog) {
        if (null == loadingDialog) {
            return;
        }
        loadingDialog.dismiss();
    }


}
