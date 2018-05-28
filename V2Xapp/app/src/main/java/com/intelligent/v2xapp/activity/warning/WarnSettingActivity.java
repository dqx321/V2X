package com.intelligent.v2xapp.activity.warning;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

//import com.androidkun.PullToRefreshRecyclerView;
//import com.androidkun.callback.PullToRefreshListener;
import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WarnSettingActivity extends BaseActivity{
//    @BindView(R.id.left_btn)
//    ImageView leftBtn;
//    @BindView(R.id.top_centerText)
//    TextView topCenterText;
//    @BindView(R.id.right_btn)
//    TextView rightBtn;
//    List<HashMap<Integer, Boolean>> listwarning = new ArrayList<>();
//    @BindView(R.id.warning_recycle)
//    PullToRefreshRecyclerView warningRecycle;
//    WarnAdapter warnAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_warn_setting);
//        ButterKnife.bind(this);
//        initView();
//        initData();
//        updateView();
//    }
//
//    private void updateView() {
//
//
//    }
//
//    private void initData() {
//        for (int i = 0; i < 10; i++) {
//            HashMap hashMap = new HashMap<Integer, Boolean>();
//            hashMap.put(i, true);
//            listwarning.add(hashMap);
//        }
//    }
//
//
//    private void initView() {
//        topCenterText.setText("警告设置");
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
////设置布局管理器
//        warningRecycle.setLayoutManager(layoutManager);
//        warningRecycle.setPullRefreshEnabled(true);
//        warningRecycle.setLoadingMoreEnabled(true);
//        warningRecycle.displayLastRefreshTime(true);
//        warningRecycle.setPullToRefreshListener(this);
////设置增加或删除条目的动画
//        warningRecycle.setItemAnimator(new DefaultItemAnimator());
//        warnAdapter = new WarnAdapter(R.layout.warn_adapter, listwarning, new CheckInterface() {
//            @Override
//            public void onChecked(int position) {
//
//            }
//        });
//        warningRecycle.setAdapter(warnAdapter);
//    }
//
//    @Override
//    public void onRefresh() {
//        warningRecycle.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                warningRecycle.setRefreshComplete();
//            }
//        }, 1000);
//    }
//
//    @Override
//    public void onLoadMore() {
//        warningRecycle.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                warningRecycle.setLoadMoreComplete();
//            }
//        }, 1000);
//    }
}
