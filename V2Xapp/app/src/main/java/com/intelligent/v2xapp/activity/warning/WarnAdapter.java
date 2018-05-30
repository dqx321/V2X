package com.intelligent.v2xapp.activity.warning;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.base.BaseHolder;
import com.intelligent.v2xapp.activity.base.BaseRecycleAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

// Created by CIDI daiqinxue on 2018/5/25.
public class WarnAdapter extends BaseRecycleAdapter {
    CheckInterface checkInterface;
    List<LitPalWarnBean> list;

    //    public WarnAdapter(int layoutId, @NotNull List list) {
//        super(layoutId, list);
//    }
    public WarnAdapter(int layoutId, @NotNull List<LitPalWarnBean> list, CheckInterface checkInterface) {
        super(layoutId, list);
        this.checkInterface = checkInterface;
        this.list = list;

    }

    @Override
    protected void convert(@NotNull BaseHolder holder, Object item, final int position) {
        super.convert(holder, item, position);
        LitPalWarnBean litPalWarnBean = (LitPalWarnBean) item;
// printkey
        holder.setText(R.id.warn_type, litPalWarnBean.getWarnType() + "");
        holder.setCheck(R.id.warn_check, litPalWarnBean.isShown());
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                checkInterface.onChecked(position, isChecked);
//            }
//        });
    }

}
