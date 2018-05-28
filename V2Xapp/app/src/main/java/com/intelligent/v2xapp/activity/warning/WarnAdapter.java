package com.intelligent.v2xapp.activity.warning;

import com.intelligent.v2xapp.activity.base.BaseHolder;
import com.intelligent.v2xapp.activity.base.BaseRecycleAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

// Created by CIDI daiqinxue on 2018/5/25.
public class WarnAdapter extends BaseRecycleAdapter {
    CheckInterface checkInterface;
//    public WarnAdapter(int layoutId, @NotNull List list) {
//        super(layoutId, list);
//    }
    public WarnAdapter(int layoutId, @NotNull List list,CheckInterface checkInterface) {
        super(layoutId, list);
        this.checkInterface=checkInterface;

    }

    @Override
    protected void convert(@NotNull BaseHolder holder, Object item) {
        super.convert(holder, item);

    }

}
