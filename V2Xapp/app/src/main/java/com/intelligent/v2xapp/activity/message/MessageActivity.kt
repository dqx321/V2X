package com.intelligent.v2xapp.activity.message

import android.os.Bundle
//import com.androidkun.callback.PullToRefreshListener
import com.intelligent.v2xapp.R
import com.intelligent.v2xapp.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_message.*
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.androidkun.callback.PullToRefreshListener
import com.intelligent.v2xapp.activity.offline.OfflineMapActivity
import com.vise.common_utils.log.LogUtils
import kotlinx.android.synthetic.main.top_layout.*
import org.litepal.crud.DataSupport


class MessageActivity : BaseActivity(), PullToRefreshListener {
    var size: Int = 10
    override fun onLoadMore() {//return type kotlin.Unit
        message_recycle.postDelayed(Runnable {
            size = size + 10
            list!!.clear()
            getData()

            message_recycle.setLoadMoreComplete()
        }, 500)
    }



    override fun onRefresh() {//return type kotlin.Unit
        message_recycle.postDelayed(Runnable {
            message_recycle.setRefreshComplete()
            list!!.clear()
            size = 10
            getData()
        }, 1500)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        initView();
        initRecycle()
        getData()

    }

    private fun initView() {
//return type Unit
        top_centerText.text="消息"
    }

    private fun getData() {
        //查10条数据
        list!!.addAll((DataSupport.limit(size).find<MessageDb>(MessageDb::class.java) as ArrayList<MessageDb>?)!!)
        LogUtils.e(list.toString())
        adapter.notifyDataSetChanged()

    }

    var list: ArrayList<MessageDb>? = null
    lateinit var adapter: MessageAdapter
    private fun initRecycle() {
//return type Unit
        val layoutManager = LinearLayoutManager(this)
//设置布局管理器
        message_recycle.setLayoutManager(layoutManager)
        message_recycle.setPullRefreshEnabled(true)
        message_recycle.setLoadingMoreEnabled(true)
        message_recycle.displayLastRefreshTime(true)
        message_recycle.setPullToRefreshListener(this)
//设置增加或删除条目的动画
        message_recycle.setItemAnimator(DefaultItemAnimator())
        list = ArrayList()
        adapter = MessageAdapter(list!!)
        message_recycle.setAdapter(adapter)
    }
}
