package com.intelligent.v2xapp.activity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.base.BaseActivity;
import com.intelligent.v2xapp.review.FadeInTextView;
import com.intelligent.v2xapp.review.ProGressBar;
import com.intelligent.v2xapp.util.TagUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

//    @BindView(R.id.chat_msg_face)
//    FadeInTextView chatMsgFace;
    @BindView(R.id.myview)
    ProGressBar myview;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
//        chatMsgFace.setTextString("kajdjkdawklhkdjjkhghjjhjjhjhghjhjhdgfdssiyttiuioouioiuuiuioiyouyuiooiioiuoioiuyiyyiiyyi哦i啊我丢i哦啊对哦i偶啊的声卡声卡的ahwkj").startFadeInAnimation().setTextAnimationListener(new FadeInTextView.TextAnimationListener() {
//            @Override
//            public void animationFinish() {
//
//            }
//        });

        myview.setProgressListener(new ProGressBar.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
                TagUtils.showTip(currentProgress + "=");
            }
        });
    }

    float i = 0;

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                if (i == 100) {
                    i = i - 20;

                } else if (i == 0) {
                    i = i + 20;
                }
                myview.setProgressWithAnimation(i);
                break;
            case R.id.btn2:
                btn2.setText(SystemClock.uptimeMillis() + "");

                break;
            case R.id.btn3:
                break;
            case R.id.btn4:
                break;
            case R.id.btn5:
                break;
        }
    }
}
