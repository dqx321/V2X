package com.intelligent.v2xapp.activity.offline

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import com.baidu.mapapi.map.offline.*
import com.intelligent.v2xapp.R
import com.intelligent.v2xapp.activity.base.BaseActivity
import com.intelligent.v2xapp.activity.offline.adapter.LocationCityAdapter
import com.vise.common_base.utils.ToastUtil
import com.vise.common_utils.log.LogUtils
import kotlinx.android.synthetic.main.activity_local.*
import java.util.ArrayList

class LocalActivity : BaseActivity(), MKOfflineMapListener {
    override fun onGetOfflineMapState(type: Int, state: Int) {//return type kotlin.Unit
        LogUtils.e("" + type + "vvv" + state)
        when (type) {
            MKOfflineMap.TYPE_DOWNLOAD_UPDATE -> {
                val update = mOffline!!.getUpdateInfo(state)
                // 处理下载进度更新提示
                if (update != null) {

//                    stateView.setText(String.format("%s : %d%%", update!!.cityName,
//                            update!!.ratio))
                    updateView()
                }
            }
            MKOfflineMap.TYPE_NEW_OFFLINE ->
                // 有新离线地图安装
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state))
            MKOfflineMap.TYPE_VER_UPDATE -> {
            }
            else -> {
            }
        }
    }

    var cityAdapter: LocationCityAdapter? = null
    lateinit var mList: ArrayList<MKOLUpdateElement>

    private fun updateView() {
        if (mOffline!!.allUpdateInfo != null) {
            mList = mOffline!!.allUpdateInfo
            cityAdapter!!.updateView(mList)
        }
    }

    var mOffline: MKOfflineMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local)
        initOfflineMap()
        updateView()
        setView()
    }

    private fun setView() {
//return type Unit
        locallist.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            LogUtils.e("点击了" + mList[position].cityID)
            if (mList[position].update || mList[position].ratio != 100) {
                mOffline!!.remove(mList[position].cityID)
                mOffline!!.start(mList[position].cityID)
                ToastUtil.showShortToast(this, "下载" + mList[position].cityName + "离线地图")

            }


        })
    }

    private fun initOfflineMap() {
        mOffline = MKOfflineMap()
        mOffline!!.init(this)

        mList = ArrayList<MKOLUpdateElement>()

        cityAdapter = LocationCityAdapter(mList, this)
        locallist.adapter = cityAdapter

    }
}

