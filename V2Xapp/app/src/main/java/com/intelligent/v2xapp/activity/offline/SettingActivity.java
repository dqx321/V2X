package com.intelligent.v2xapp.activity.offline;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligent.v2xapp.Final;
import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.V2XMainActivity;
import com.intelligent.v2xapp.activity.base.BaseActivity;
import com.intelligent.v2xapp.udp.UdpSocket1;
import com.vise.common_base.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.edit_red)
    EditText editRed;
    @BindView(R.id.text_red)
    TextView textRed;
    @BindView(R.id.edit_green)
    EditText editGreen;
    @BindView(R.id.text_green)
    TextView textGreen;
    public static SettingActivity instance = null;
    @BindView(R.id.top_centerText)
    TextView topCenterText;
    @BindView(R.id.right_btn)
    TextView rightBtn;
    private UdpSocket1 udpSocket;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg1) {
            switch (msg1.what) {
                case 10:

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        instance = this;
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        rightBtn.setVisibility(View.VISIBLE);
        topCenterText.setText("设置");
    }

    private void sendUdp(final String ip, final String message, final int port) {

        try {
            if (udpSocket == null) {
                udpSocket = new UdpSocket1(handler, port);
            }
            udpSocket.sendUdp(message + "", ip, port);
//            udpSocket.initSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offline(View view) {
        openActivityNoIntent(OfflineMapActivity.class);
    }

    public void setBack(View view) {
        openActivityNoIntent(V2XMainActivity.class);
    }

    @OnClick({R.id.text_red, R.id.text_green})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_red:
                if (editRed.getText() != null && editRed.getText().length() > 0) {
                    sendUdp(Final.Other_IP, "r" + editRed.getText().toString(), 8891);
                } else {
                    ToastUtil.showShortToast(this, "请输入时间");
                }
                break;
            case R.id.text_green:
                if (editGreen.getText() != null && editRed.getText().length() > 0) {
                    sendUdp(Final.Other_IP, "g" + editGreen.getText().toString(), 8891);
                } else {
                    ToastUtil.showShortToast(this, "请输入时间");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (udpSocket != null) {
            udpSocket.CloseUdp();
        }
        super.onDestroy();
    }
}
