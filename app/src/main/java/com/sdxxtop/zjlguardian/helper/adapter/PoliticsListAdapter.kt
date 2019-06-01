package com.sdxxtop.zjlguardian.helper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.data.Politic
import com.sdxxtop.zjlguardian.data.PoliticsListBean
import com.sdxxtop.zjlguardian.databinding.ItemPoliticsListRecyclerBinding
import com.sdxxtop.zjlguardian.ui.learn.news.NewsDetailsActivity
import org.jetbrains.anko.startActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-24 17:30
 * Version: 1.0
 * Description:
 */
class PoliticsListAdapter(layoutResId: Int = R.layout.item_politics_list_recycler
                          , val isSearch: Boolean) : BaseQuickAdapter<Politic, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: Politic?) {
        val bind = DataBindingUtil.bind<ItemPoliticsListRecyclerBinding>(helper?.itemView!!)
        bind?.politic = item
        bind?.executePendingBindings()

        when (item?.type) {
            "0" -> {

            }
            "1" -> {

            }
        }

        when (isSearch) {
            true -> { //如果是搜索，就用匿名和公开，否则用名字
                when (item?.type) {
                    "0" -> {
                        bind?.tvName?.setText("匿名")
                    }
                    "1" -> {
                        bind?.tvName?.setText("公开")
                    }
                }
            }
            else -> {
                bind?.tvName?.setText(item?.user_name);
            }
        }

        //0=待受理,1=已受理,2=已办复
        when (item?.state) {
            "0" -> {
                bind?.tvStatus?.setText("待受理 ＞");
            }
            "1" -> {
                bind?.tvStatus?.setText("已受理 ＞");
            }
            "2" -> {
                bind?.tvStatus?.setText("已办复 ＞");
            }
        }

        bind?.tvTime?.setText(item?.time?.split(" ")?.get(0) ?: item?.time)

        bind?.root?.setOnClickListener {
            mContext.startActivity<NewsDetailsActivity>("article_path" to item?.url)
        }
    }


}