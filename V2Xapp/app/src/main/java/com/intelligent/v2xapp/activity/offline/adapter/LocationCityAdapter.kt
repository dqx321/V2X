package com.intelligent.v2xapp.activity.offline.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.baidu.mapapi.map.offline.MKOLUpdateElement
import com.intelligent.v2xapp.R

// Created by CIDI daiqinxue on 2018/5/18.


class LocationCityAdapter(var mList: List<MKOLUpdateElement>, var context: Context) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: MyViewHolder
        //重用view
        var v: View
        if (convertView == null) {
            holder = MyViewHolder()
            v = LayoutInflater.from(context).inflate(R.layout.location_adapter, parent, false)
            holder.textView = v.findViewById(R.id.city) as TextView
            //设置tag
            v.tag = holder
        } else {
            v = convertView
            //获取tag并强转
            holder = v.tag as MyViewHolder
        }

        //为TextView设置内容
        holder.textView.text = mList[position].cityName+"     "+mList[position].ratio+"%"
        return v
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }
    public fun updateView(mList: List<MKOLUpdateElement>){
        this.mList=mList
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mList.size
    }

    class MyViewHolder {

        lateinit var textView: TextView

    }
}

