package com.sdxxtop.zjlguardian.ui.notice.adapter

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.utils.ItemDivider
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.ui.notice.data.Notic
import com.sdxxtop.zjlguardian.ui.notice.data.NoticDateBean

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-28 18:30
 * Version: 1.0
 * Description:
 */
class NoticeAdapter(layoutResId: Int = R.layout.item_notice_recycler) : BaseQuickAdapter<NoticDateBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: NoticDateBean?) {
        val recyclerView = helper?.getView<RecyclerView>(R.id.rv_item)
        val textAdapter = TextAdapter()
        if (recyclerView?.adapter == null) {
            recyclerView?.addItemDecoration(ItemDivider()
                    .setDividerWidth(UIUtils.dip2px(1))
                    .setLastLineNotDraw(true))
            recyclerView?.layoutManager = LinearLayoutManager(mContext)
            recyclerView?.isNestedScrollingEnabled = false
        }
        recyclerView?.adapter = textAdapter
        textAdapter.replaceData(item?.notic_list!!)

        var textView = helper?.getView<TextView>(R.id.tv_time_title)
        textView?.text = item.time

    }
}

class TextAdapter(layoutResId: Int = R.layout.item_text_recycler) : BaseQuickAdapter<Notic, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: Notic?) {
        val textView = helper?.itemView as TextView
        textView.text = item?.title
    }

}