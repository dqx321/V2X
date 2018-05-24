package com.intelligent.v2xapp.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.intelligent.v2xapp.R;

import java.util.List;

// Created by CIDI daiqinxue on 2018/5/19.
public class MeAdapter extends BaseAdapter {
    public MeAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, Object o) {
        holder.setText(R.id.textView, (String) o);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
