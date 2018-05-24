package com.intelligent.v2xapp.activity.offline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.offline.entity.City;

import java.util.List;

/**
 * Created by next on 2016/3/25.
 */
public class RecentCityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<MKOLUpdateElement> hotCities;

    public RecentCityAdapter(Context context, List<MKOLUpdateElement> hotCities) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.hotCities = hotCities;
    }

    @Override
    public int getCount() {
        return hotCities.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_city_cell, null);
        }
        TextView city = (TextView) convertView.findViewById(R.id.city);
        city.setText(hotCities.get(position).cityName + "==");
        return convertView;
    }
}