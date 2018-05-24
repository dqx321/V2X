package com.intelligent.v2xapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.intelligent.v2xapp.Final;
import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.base.BaseActivity;
import com.intelligent.v2xapp.activity.main.CarClassify;
import com.intelligent.v2xapp.activity.main.LightBean;
import com.intelligent.v2xapp.activity.main.MainBean;
import com.intelligent.v2xapp.activity.main.WarningBean;
import com.intelligent.v2xapp.activity.message.MessageActivity;
import com.intelligent.v2xapp.activity.message.MessageDb;
import com.intelligent.v2xapp.activity.offline.OfflineMapActivity;
import com.intelligent.v2xapp.activity.offline.SettingActivity;
import com.intelligent.v2xapp.review.DashboardView;
import com.intelligent.v2xapp.udp.UdpSocket;
import com.intelligent.v2xapp.util.CheckUtils;
import com.intelligent.v2xapp.util.DataUtil;
import com.intelligent.v2xapp.util.ExecutorUtil;
import com.intelligent.v2xapp.util.HXPreferenceUtils;
import com.intelligent.v2xapp.util.WarningSqlitBean;
import com.vise.common_utils.log.LogUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class V2XMainActivity extends BaseActivity {

    @BindView(R.id.mapview1)
    MapView mapview1;
    public static final String MAINACTIVITY_ACTION = "com.udp.mainbroad";
    public static final String UdpData = "UdpData";

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg1) {
            switch (msg1.what) {
                case 40:
                    adapter.notifyDataSetChanged();
                    break;
                case 20:
                    if (lightsLayout.isShown()) {
                        lightsLayout.setVisibility(View.GONE);
                        lightsLayout.startAnimation(animation_gone);
                    }
                    break;
                case 10:
                    String msg = msg1.obj.toString();

                    if (msg.substring(0, 2).equals("01") && msg.length() >= 30) {
                        MainBean bean;
                        String carId = DataUtil.getInter(msg.substring(2, 10)) + "";
                        String latitu = DataUtil.getInter(msg.substring(10, 18)) * 0.0000001 + "";
                        String longtitu = DataUtil.getInter(msg.substring(18, 26)) * 0.0000001 + "";
                        String direction = DataUtil.getInter(msg.substring(26, 30)) + "";
                        String speed = DataUtil.getInter(msg.substring(30, 34)) + "";
                        bean = new MainBean(carId, latitu, longtitu, direction, speed);
                        setMainbean(bean);
                    } else if (msg.substring(0, 2).equals("02") && msg.length() >= 18) {
                        WarningBean warningBean;
                        String carId = DataUtil.getInter(msg.substring(2, 10)) + "";
                        String warningtype = DataUtil.getInter(msg.substring(10, 14)) + "";
                        String describe = DataUtil.getInter(msg.substring(14, 18)) + "";

                        warningBean = new WarningBean(carId, warningtype, describe);
                        //保持到数据库
//                        WarningSqlitBean warningSqlitBean = new WarningSqlitBean();
//                        warningSqlitBean.setCarId(carId);
//                        warningSqlitBean.setDescribe(describe);
//                        warningSqlitBean.setTime(System.currentTimeMillis());
//                        warningSqlitBean.setWarningtype(warningtype);
//                        warningSqlitBean.save();
                        if (CheckUtils.strIsInteger(warningBean.getWarningtype())) {
                            setWarningBean(warningBean);
                        }

                    } else if (msg.substring(0, 2).equals("03") && msg.length() >= 8) {
                        LightBean lightBean;
                        String carId = DataUtil.getInter(msg.substring(2, 8)) + "";
                        String warningtype = DataUtil.getInter(msg.substring(10, 12)) + "";
                        String describe = DataUtil.getInter(msg.substring(12, 14)) + "";
                        lightBean = new LightBean(carId, warningtype, describe);
                        setLight(lightBean);

                    }
                    break;
            }
        }
    };


    @BindView(R.id.dashboardView)
    DashboardView dashboardView;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.lights_image)
    ImageView lightsImage;
    @BindView(R.id.lights_text)
    TextView lightsText;
    @BindView(R.id.lights_layout)
    LinearLayout lightsLayout;
    @BindView(R.id.set_btn)
    Button set_btn;
    @BindView(R.id.warning_btn)
    Button warning_btn;
    @BindView(R.id.message_btn)
    Button message_btn;
    private boolean issetCenter = false;
    private List<CarClassify> mMarkers = new ArrayList<>(); // 存储所有车辆marker
    private Map<Marker, Long> timers = new ConcurrentHashMap<>();// 存储所有车辆marker和更新数据时间

    @OnClick({R.id.set_btn, R.id.warning_btn, R.id.message_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.set_btn:
                openActivityNoIntent(SettingActivity.class);
                break;
            case R.id.message_btn:
                openActivityNoIntent(MessageActivity.class);

                break;
            case R.id.warning_btn:
                break;
        }
    }

    private Marker getCarMarker(String id) {
        if (mMarkers.size() > 0) {
            for (int i = 0; i < mMarkers.size(); i++) {
                if (mMarkers.get(i).getId().equals(id)) {
                    return mMarkers.get(i).getMarker();
                }
            }
        }
        return null;
    }

    private void setMainbean(MainBean bean) {
        if (bean.getCarId().equals("")) {
            dashboardView.setVelocity(Integer.parseInt(bean.getSpeed()));
        }
        Random rand = new Random();
        LatLng point = new LatLng(rand.nextInt(5), rand.nextInt(5));
//        LatLng point = conver(new LatLng(Double.parseDouble(bean.getLatitu()), Double.parseDouble(bean.getLongtitu())));
        long time = System.currentTimeMillis();
        Marker mMarker = getCarMarker(bean.getCarId());
        if (mMarker == null) {
            if ("3125031455".equals(bean.getCarId())) {
                LogUtils.e(bean.getCarId());
                mMarker = (Marker) mBaiduMap.addOverlay(new MarkerOptions().icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.car
                        ))
                        .position(point)
                        .title("其他")
                        .anchor(0.5f, 0.5f));
            } else {
                mMarker = (Marker) mBaiduMap.addOverlay(new MarkerOptions().icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.car))
                        .position(point)
                        .title("其他")
                        .anchor(0.5f, 0.5f));
            }
            mMarkers.add(new CarClassify(bean.getCarId(), mMarker));
            put(mMarker, time);

        } else {
            put(mMarker, time);
            mMarker.setPosition(point);
        }
        if (!issetCenter) {
            issetCenter = true;
            setUserMapCenter(point);
        }
    }

    private void put(Marker marker, long time) {
        if (timers.containsKey(marker)) {
            timers.remove(marker);
        }
        timers.put(marker, time);
    }

    Handler handler_timer = new Handler();
    Runnable runnable_timer = new Runnable() {
        @Override
        public void run() {
            long time = System.currentTimeMillis();

            if (timers.size() != 0) {
                Set set = timers.keySet();
                if (set != null) {
                    Iterator iterator = set.iterator();
                    while (iterator.hasNext()) {
                        // 取出单个的map键
                        Marker key = (Marker) iterator.next();
                        long value = timers.get(key);
                        if (time - value >= 2000) {
                            key.remove();
                            removeMarker(key);
                            timers.remove(key);
                        }
                    }
                }
            }

            handler_timer.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        handler_timer.post(runnable_timer);

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler_timer.removeCallbacks(runnable_timer);
        handler.removeCallbacks(null);
    }

    private void removeMarker(Marker marker) {
        if (mMarkers.size() > 0) {
            for (int i = 0; i < mMarkers.size(); i++) {
                if (mMarkers.get(i).getMarker().equals(marker)) {
                    mMarkers.remove(i);
                }
            }
        }
    }

    /**
     * 设置中心点
     */
    private void setUserMapCenter(LatLng cenpt) {
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        //构建Marker图标
    }

    private LatLng conver(LatLng sourceLatLng) {

        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
// 将GPS设备采集的原始GPS坐标转换成百度坐标
    }

    HXPreferenceUtils preferenceUtils;
    WarningAdapter adapter;
    List<WarnBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2_xmain);
        ButterKnife.bind(this);
        initData();
        initwarn();
        HXPreferenceUtils.init(this);
        preferenceUtils = new HXPreferenceUtils(this);
        adapter = new WarningAdapter(this, list);
        listview.setAdapter(adapter);
        initUdp("192.168.3.214", "data");
        initUdp1("192.168.3.214", "event");
        initUdp2("192.168.3.214", "tf_lights");
        initTimer();

    }

    private void initTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage(40, ""));
                if (System.currentTimeMillis() - time1 >= 2000) {
                    handler.sendMessage(handler.obtainMessage(20, ""));
                }
            }
        }, 1000, 1500);
    }

    MediaPlayer mediaPlayer;

    private void initwarn() {
        if (mediaPlayer == null) {
            //创建播放实例
            mediaPlayer = MediaPlayer.create(V2XMainActivity.this, R.raw.warning0);
        }
        for (int i = 0; i < (Final.NUMBER + 1); i++) {
            list.add(new WarnBean(i + "", 0));
        }
    }


    BaiduMap mBaiduMap;

    public void initData() {
        animation_show = AnimationUtils.loadAnimation(this, R.anim.light_show);
        animation_gone = AnimationUtils.loadAnimation(this, R.anim.light_gone);
        animation_gone.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lightsLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBaiduMap = mapview1.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        mBaiduMap.setTrafficEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
    }

    private void initUdp(final String ip, final String message) {

        try {
            if (udpSocket == null) {
                udpSocket = new UdpSocket(handler, 8888);
            }
            udpSocket.sendUdp(message + "", ip, 8888);
            udpSocket.initSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UdpSocket udpSocket;
    private UdpSocket udpSocket1;
    private UdpSocket udpSocket2;

    private void initUdp1(final String ip, final String message) {
        ExecutorUtil.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    if (udpSocket1 == null) {
                        udpSocket1 = new UdpSocket(handler, 8889);
                    }
                    udpSocket1.sendUdp(message + "", ip, 8889);
                    udpSocket1.initSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initUdp2(String ip, String event) {
        try {
            if (udpSocket2 == null) {
                udpSocket2 = new UdpSocket(handler, 8890);
            }
            udpSocket2.sendUdp(event + "", ip, 8890);
            udpSocket2.initSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Animation animation_show;
    Animation animation_gone;
    //设置红绿灯
    long time1 = 0;

    private void setLight(LightBean lightBean) {

        if (!lightsLayout.isShown()) {
            lightsLayout.setVisibility(View.VISIBLE);
            lightsLayout.startAnimation(animation_show);
        }
        lightsText.setText(lightBean.getTime() + "");
        switch (lightBean.getStyle()) {
            case "82":
                lightsText.setTextColor(getResources().getColor(R.color.red));
                lightsImage.setImageResource(R.mipmap.lights_red);
                //red
                break;
            case "71":
                //lv
                lightsText.setTextColor(getResources().getColor(R.color.green));
                lightsImage.setImageResource(R.mipmap.lights_blue);
                break;
            case "89":
                //huang
                lightsImage.setImageResource(R.mipmap.lights_yellow);
                lightsText.setTextColor(getResources().getColor(R.color.yellow));
                break;

            default:

                break;

        }
        time1 = System.currentTimeMillis();
    }


    private void setWarningBean(WarningBean warningBean) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getWarnid().equals(warningBean.getWarningtype())) {
                list.get(i).setTime(System.currentTimeMillis());
                list.get(i).setDescribe(warningBean.getDescribe());
            }
        }
        if (!warningBean.getWarningtype().equals("0")) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            MessageDb historyMessageDb = DataSupport.findLast(MessageDb.class);
            if (historyMessageDb == null) {
                MessageDb messageDb = new MessageDb();
                messageDb.setDescrib(warningBean.getDescribe());
                messageDb.setTime(System.currentTimeMillis());
                messageDb.setType(warningBean.getWarningtype());
                messageDb.save();
            } else if (!historyMessageDb.getType().equals(warningBean.getWarningtype()) || (System.currentTimeMillis() - historyMessageDb.getTime() > 2000)) {
                MessageDb messageDb = new MessageDb();
                messageDb.setDescrib(warningBean.getDescribe());
                messageDb.setTime(System.currentTimeMillis());
                messageDb.setType(warningBean.getWarningtype());
                messageDb.save();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                udpSocket.CloseUdp();
                udpSocket1.CloseUdp();
                udpSocket2.CloseUdp();
                if (isExistMainActivity(SettingActivity.class)) {
                    SettingActivity.instance.finish();
                }
                if (isExistMainActivity(OfflineMapActivity.class)) {
                    OfflineMapActivity.instance.finish();
                }
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //判断某一个类是否存在任务栈里面
    private boolean isExistMainActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);  //获取从栈顶开始往下查找的10个activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }
}
