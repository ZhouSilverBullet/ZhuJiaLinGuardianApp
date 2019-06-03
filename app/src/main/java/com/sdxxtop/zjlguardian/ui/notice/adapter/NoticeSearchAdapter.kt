package com.sdxxtop.zjlguardian.ui.notice.adapter

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.utils.ItemDivider
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.ui.learn.news.NewsDetailsActivity
import com.sdxxtop.zjlguardian.ui.notice.data.Notic
import com.sdxxtop.zjlguardian.ui.notice.data.NoticDateBean
import org.jetbrains.anko.startActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-28 18:30
 * Version: 1.0
 * Description:
 */
class NoticeSearchAdapter(layoutResId: Int = R.layout.item_text_recycler) : BaseQuickAdapter<Notic, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: Notic?) {
        val textView = helper?.itemView as TextView

        textView.text = item?.spanString ?: item?.title ?: ""

        textView.setOnClickListener {
            mContext.startActivity<NewsDetailsActivity>("article_path" to item?.url)
        }
    }
}