package com.intelligent.v2xapp.activity.offline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.intelligent.v2xapp.R;
import com.intelligent.v2xapp.activity.offline.HotInterface;
import com.intelligent.v2xapp.activity.offline.bean.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by next on 2016/3/25.
 */
public class HotCityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<City> hotCities=new ArrayList<>();
    private HotInterface clicCity;

    public HotCityAdapter(Context context, List<City> hotCities,HotInterface clicCity) {
        this.context = context;
        this.clicCity=clicCity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_city_cell, null);
        Button city = (Button) convertView.findViewById(R.id.city);
        city.setText(hotCities.get(position).getName());
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicCity.onClick(position);
            }
        });
        return convertView;
    }
}
