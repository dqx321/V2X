package com.intelligent.v2xapp.activity.base

import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView

/**
 * recycleview的baseholder
 * Created by CIDI daiqinxue on 2018/5/21
 */
class BaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //不写死控件变量，而采用Map方式
    private val mViews = HashMap<Int, View>()

    /**
     * 获取控件的方法
     */
    fun <T> getView(viewId: Int?): View? {
        //根据保存变量的类型 强转为该类型
        var view = mViews.get(viewId)
        if (view == null) {
            view = itemView.findViewById<View>(viewId!!)
            //缓存
            mViews.put(viewId, view)
        }
        return view
    }

    /**
     * 传入文本控件id和设置的文本值，设置文本
     */
    fun setText(viewId: Int?, value: String): BaseHolder {
        val textView = getView<TextView>(viewId) as TextView
        if (textView != null) {
            textView.text = value
        }
        return this
    }    /**
     * 传入文本控件id和设置的文本值，设置文本
     */
    fun setCheck(viewId: Int?, value: Boolean): BaseHolder {
        val checkBox = getView<CheckBox>(viewId) as CheckBox
        if (checkBox != null) {
            checkBox.isChecked = value
        }
        return this
    }

    /**
     * 传入图片控件id和资源id，设置图片
     */
    fun setImageResource(viewId: Int?, resId: Int?): BaseHolder {
        val imageView = getView<ImageView>(viewId) as ImageView
        if (imageView != null) {
            if (resId != null) {
                imageView!!.setImageResource(resId)
            }
        }
        return this
    }
    //...还可以扩展出各种控件。
    //Fluent API 链式api  obj.setxxx().setyyy()....
}