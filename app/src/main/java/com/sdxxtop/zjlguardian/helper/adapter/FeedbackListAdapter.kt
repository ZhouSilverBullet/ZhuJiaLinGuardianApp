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
import com.sdxxtop.zjlguardian.databinding.ItemFeedbackListRecyclerBinding
import com.sdxxtop.zjlguardian.databinding.ItemPoliticsListRecyclerBinding
import com.sdxxtop.zjlguardian.ui.feedback.data.Proposal
import com.sdxxtop.zjlguardian.ui.learn.news.NewsDetailsActivity
import org.jetbrains.anko.startActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-24 17:30
 * Version: 1.0
 * Description:
 */
class FeedbackListAdapter(layoutResId: Int = R.layout.item_feedback_list_recycler
                          , val isSearch: Boolean) : BaseQuickAdapter<Proposal, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: Proposal?) {
        val bind = DataBindingUtil.bind<ItemFeedbackListRecyclerBinding>(helper?.itemView!!)
        bind?.proposal = item
        bind?.executePendingBindings()

        when (item?.status) { //0=意见,1=建议,2=投诉,
            "0" -> {
                bind?.tvType?.setText("反应类别：意见")
            }
            "1" -> {
                bind?.tvType?.setText("反应类别：建议")
            }
            "2" -> {
                bind?.tvType?.setText("反应类别：投诉")
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
            mContext.startActivity<NewsDetailsActivity>(
                    "article_path" to "http://villageapi.sdzhujialin.com/village/policy_info/index?policy_id=${item?.id}")
        }
    }


}