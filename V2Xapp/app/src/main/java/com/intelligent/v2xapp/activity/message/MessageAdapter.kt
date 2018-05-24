package com.intelligent.v2xapp.activity.message

import com.intelligent.v2xapp.R
import com.intelligent.v2xapp.activity.base.BaseHolder
import com.intelligent.v2xapp.activity.base.BaseRecycleAdapter
import com.intelligent.v2xapp.util.DataUtil

/**
 * 1.extends  把父类的代码继承过来。  可以少写很多代码。
 * 2.因为是全部拿来的代码，所以不是所有的代码都适合当前。对于不适合当前的代码（方法）
 * 我们可选择@Override 覆盖|重写
 */
class MessageAdapter(list: List<MessageDb>) : BaseRecycleAdapter<MessageDb>(R.layout.message_adapter, list) {
     override fun convert(holder: BaseHolder, item: MessageDb) {
        holder.setText(R.id.message_text, "时间："+DataUtil.getTimeFromMillisecond(item.time)+"\n警告类型："+item.type+"   警告描述："+item.describ)
    }
}