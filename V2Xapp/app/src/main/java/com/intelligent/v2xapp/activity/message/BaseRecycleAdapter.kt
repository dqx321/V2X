package com.intelligent.v2xapp.activity.message

import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView



// Created by CIDI daiqinxue on 2018/5/21.
open class BaseRecycleAdapter<T>( val layoutId: Int,val list: List<T>) : RecyclerView.Adapter<BaseHolder>() {


    //onCreateViewHolder用来给rv创建缓存
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        //参数3 判断条件 true  1.打气 2.添加到paraent
        // false 1.打气 （参考parent的宽度）
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BaseHolder(view)
    }

    //onBindViewHolder给缓存控件设置数据
    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val item = list.get(position)
        convert(holder, item)
    }

    protected open fun convert(holder: BaseHolder, item: T) {
        //什么都没有做
    }

    //获取记录数据
    override fun getItemCount(): Int {
        return list.size
    }
}