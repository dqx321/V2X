package com.intelligent.v2xapp.activity.offline;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.base.BaseActivity;
import com.intelligent.v2xapp.activity.offline.adapter.CityListAdapter;
import com.intelligent.v2xapp.activity.offline.entity.City;
import com.intelligent.v2xapp.activity.offline.utils.AscllUtil;
import com.intelligent.v2xapp.activity.offline.utils.DensityUtil;
import com.intelligent.v2xapp.activity.offline.utils.PingYinUtil;
import com.intelligent.v2xapp.activity.offline.view.LetterListView;
import com.vise.common_base.utils.ToastUtil;
import com.vise.common_utils.log.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfflineMapActivity extends BaseActivity implements LetterListView.OnTouchingLetterChangedListener, AbsListView.OnScrollListener, MKOfflineMapListener, BDLocationListener, ClicCity {


    @BindView(R.id.city_container)
    ListView city_container;
    @BindView(R.id.letter_container)
    LetterListView letter_container;
    @BindView(R.id.localcity)
    Button localcity;
    private List<City> allCities = new ArrayList<>();
    private List<City> hotCities = new ArrayList<>();
    private List<City> localarrayList = new ArrayList<>();
    private List<City> citiesData;
    private Map<String, Integer> letterIndex = new HashMap<>();
    private CityListAdapter cityListAdapter;
    public static OfflineMapActivity instance;

//    private TextView letterOverlay; // 对话框首字母textview
//    private OverlayThread overlayThread; // 显示首字母对话框

    private boolean isScroll;
    //    private boolean isOverlayReady;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlin_map);
        ButterKnife.bind(this);
        instance = this;
        getCityData();
        handler = new Handler();
        initCity();
        setupView();
//        initOverlay();
        startLocate();
    }

    public void setBack(View view) {
        finish();
    }

    //离线地图
    private MKOfflineMap mkOfflineMap;

    private void getCityData() {
        mkOfflineMap = new MKOfflineMap();
        mkOfflineMap.init(this);
// 构造定位数据

    }

    public LocationClient mLocationClient = null;

    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(this);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        //开启定位
        mLocationClient.start();
    }

    City locationcity;

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
//定位
        locationcity = new City(bdLocation.getCity(), bdLocation.getCityCode());
        cityListAdapter.updateLocation(locationcity);
    }


    @Override
    public void onGetOfflineMapState(int i, int i1) {
    }

    private void initCity() {
        City city = new City("定位", "0"); // 当前定位城市
        allCities.add(city);
        city = new City("已下载", "1"); // 热门城市 allCities.add(city);
        allCities.add(city);
        city = new City("热门", "2"); // 热门城市
        allCities.add(city);
        city = new City("全部", "3"); // 全部城市
        allCities.add(city);
        citiesData = getCityList();
        LogUtils.e(citiesData.toString());
        allCities.addAll(citiesData);
    }

    private void updateLocal() {
        // 获取已下过的离线地图信息
        List<MKOLUpdateElement> localarray = mkOfflineMap.getAllUpdateInfo();
        if (localarray != null) {
            for (int k = 0; k < localarray.size(); k++) {
                localarrayList.add(new City(localarray.get(k).cityName, ""));
                LogUtils.e("city=" + localarray.get(k).cityName);
            }
        }
    }

    private String getPinyin(String text) {
//    PingYinUtil.getPinYinHeadChar()
        return AscllUtil.getInstance().getSelling(text);
    }

    private ArrayList<City> getCityList() {
        updateLocal();
        //添加热门城市
        ArrayList<MKOLSearchRecord> arrayList1 = mkOfflineMap.getHotCityList();
        for (int i = 0; i < arrayList1.size(); i++) {
            hotCities.add(new City(arrayList1.get(i).cityName, getPinyin(arrayList1.get(i).cityName), arrayList1.get(i)));
        }
        //添加所有城市
        ArrayList<City> list = new ArrayList<>();
        ArrayList<MKOLSearchRecord> arrayList = mkOfflineMap.getOfflineCityList();
        for (int i = 0; i < arrayList.size(); i++) {
            list.add(new City(arrayList.get(i).cityName, getPinyin(arrayList.get(i).cityName), arrayList.get(i)));
            if (arrayList.get(i).childCities != null) {
                int s = arrayList.get(i).childCities.size();
                for (int j = 0; j < s; j++) {
                    list.add(new City(arrayList.get(i).childCities.get(j).cityName, getPinyin(arrayList.get(i).childCities.get(j).cityName), arrayList.get(i).childCities.get(j)));
                }
            }
        }
        Collections.sort(list, comparator);
        return list;
    }


    /**
     * a-z排序
     */
    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    private void setupView() {
        city_container.setOnScrollListener(this);
        letter_container.setOnTouchingLetterChangedListener(this);
        cityListAdapter = new CityListAdapter(this, allCities, hotCities, localarrayList, letterIndex, this);
        city_container.setAdapter(cityListAdapter);


    }


    //    // 初始化汉语拼音首字母弹出提示框
//    private void initOverlay() {
//        overlayThread = new OverlayThread();
//        isOverlayReady = true;
//        LayoutInflater inflater = LayoutInflater.from(this);
//        letterOverlay = (TextView) inflater.inflate(R.layout.v_letter_overlay, null);
//        letterOverlay.setVisibility(View.INVISIBLE);
//
//        int width = DensityUtil.dp2px(this, 65);
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
//                width, width,
//                WindowManager.LayoutParams.TYPE_APPLICATION,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                PixelFormat.TRANSLUCENT);
//        WindowManager   windowManager   = (WindowManager) this
//                .getSystemService(Context.WINDOW_SERVICE);
//        windowManager.addView(letterOverlay, lp);
//    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }
//        if (isOverlayReady) {
//            String text;
//            String name = allCities.get(firstVisibleItem).getName();
//            String pinyin = allCities.get(firstVisibleItem).getPinyin();
//            if (firstVisibleItem < 4) {
//                text = name;
//            } else {
//                text = PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
//            }
//            Pattern pattern = Pattern.compile("^[A-Za-z]+$");
//            if (pattern.matcher(text).matches()) {
//                letterOverlay.setTextSize(40);
//            } else {
//                letterOverlay.setTextSize(20);
//            }
//            letterOverlay.setText(text);
//            letterOverlay.setVisibility(View.VISIBLE);
//            handler.removeCallbacks(overlayThread);
//            // 延迟一秒后执行，让overlay为不可见
//            handler.postDelayed(overlayThread, 1000);
//        }
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        isScroll = false;
        if (letterIndex.get(s) != null) {
            int position = letterIndex.get(s);
            city_container.setSelection(position);
            Pattern pattern = Pattern.compile("^[A-Za-z]+$");
//            if (pattern.matcher(s).matches()) {
//                letterOverlay.setTextSize(40);
//            } else {
//                letterOverlay.setTextSize(20);
//            }
//            letterOverlay.setText(s);
//            letterOverlay.setVisibility(View.VISIBLE);
//            handler.removeCallbacks(overlayThread);
//            handler.postDelayed(overlayThread, 1000);
        }
    }

    @Override
    public void downCity(MKOLSearchRecord city) {
        LogUtils.e("点击了下载" + city.cityName + "==" + city.cityID);
        if (city != null) {
            //下载当前城市
            mkOfflineMap.start(city.cityID);
            ToastUtil.showShortToast(this, "开始下载" + city.cityName + "离线地图");
        } else {
            ToastUtil.showShortToast(this, "未知错误，请联系程序员");
        }

    }

    @Override
    public void downLocation() {
        if (locationcity != null) {
            //下载当前城市
            mkOfflineMap.start(Integer.parseInt(locationcity.getPinyin()));
            ToastUtil.showShortToast(this, "开始下载" + locationcity.getName() + "离线地图");

        } else {
            ToastUtil.showShortToast(this, "定位失败，请开启GPS");
        }
    }

    @OnClick({R.id.localcity})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.localcity:
                //跳到已下载地图

                openActivityNoIntent(LocalActivity.class);
                break;
        }
    }


    private class OverlayThread implements Runnable {
        @Override
        public void run() {
//            letterOverlay.setVisibility(View.GONE);
        }
    }
}
