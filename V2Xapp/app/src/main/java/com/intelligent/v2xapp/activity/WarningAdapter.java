package com.intelligent.v2xapp.activity;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.intelligent.v2xapp.R;
import com.vise.common_utils.log.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by daiqinxue on 2017/12/6.
 */

public class WarningAdapter extends BaseAdapter {

    private List<WarnBean> list = new ArrayList<>();
    private Context context;


    public WarningAdapter(Context context, List<WarnBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = View.inflate(context, R.layout.adapteritem, null);
        }
        ImageView warningImage = (ImageView) convertView.findViewById(R.id.warning_adapter);
        TextView describ = (TextView) convertView.findViewById(R.id.describ);
        if (list.get(position).getWarnid().equals("0")) {
            warningImage.setVisibility(View.GONE);
            describ.setVisibility(View.GONE);
        } else {
            if ((System.currentTimeMillis() - list.get(position).getTime()) < 2000) {
                warningImage.setVisibility(View.VISIBLE);
                switch (list.get(position).getWarnid()) {

                    case "1":
                        warningImage.setImageResource(R.drawable.fcw);
                        describ.setVisibility(View.GONE);

                        break;

                    case "2":
                        warningImage.setImageResource(R.drawable.icw_left);
                        describ.setVisibility(View.GONE);

                        break;

                    case "3":
                        warningImage.setImageResource(R.drawable.lta);
                        describ.setVisibility(View.GONE);

                        break;

                    case "4":
                        warningImage.setImageResource(R.drawable.bsw_lcw);
                        describ.setVisibility(View.GONE);


                        break;

                    case "5":
                        warningImage.setImageResource(R.drawable.dnpw);
                        describ.setVisibility(View.GONE);

                        break;

                    case "6":
                        warningImage.setImageResource(R.drawable.ebw);
                        describ.setVisibility(View.GONE);

                        break;

                    case "7":
                        warningImage.setImageResource(R.drawable.avw);
                        describ.setVisibility(View.GONE);


                        break;

                    case "8":
                        warningImage.setImageResource(R.drawable.clw);
                        describ.setVisibility(View.GONE);


                        break;

                    case "9":
                        warningImage.setImageResource(R.drawable.hlw);
                        describ.setVisibility(View.GONE);


                        break;

                    case "10":
                        warningImage.setImageResource(R.drawable.slw);
                        describ.setVisibility(View.GONE);


                        break;
                    case "11":
                        warningImage.setImageResource(R.drawable.rlvw);
                        describ.setVisibility(View.GONE);

                        break;

                    case "12":
                        warningImage.setImageResource(R.drawable.vrucw);
                        describ.setVisibility(View.GONE);


                        break;
                    case "13":
                        describ.setVisibility(View.VISIBLE);
                        describ.setText("绿波车速：" + list.get(position).getDescribe());
                        warningImage.setImageResource(R.drawable.glosa);

                        break;

                    case "14":
                        warningImage.setImageResource(R.drawable.ivs);
                        describ.setVisibility(View.GONE);

                        break;

                    case "15":
                        warningImage.setImageResource(R.drawable.tjw);
                        describ.setVisibility(View.GONE);


                        break;

                    case "16":
                        warningImage.setImageResource(R.drawable.evw);
                        describ.setVisibility(View.GONE);


                        break;

                    case "17":
                        warningImage.setImageResource(R.drawable.vnfp);
                        describ.setVisibility(View.GONE);


                        break;
                    case "18":
                        describ.setVisibility(View.GONE);

                        break;


                    default:
//                Glide.with(context).load(R.drawable.headway_monitor).into(warningImage);
                        describ.setVisibility(View.GONE);
                        warningImage.setVisibility(View.GONE);
                        break;

                }
            } else {
                warningImage.setVisibility(View.GONE);
                describ.setVisibility(View.GONE);

            }
        }

        return convertView;
    }

}
