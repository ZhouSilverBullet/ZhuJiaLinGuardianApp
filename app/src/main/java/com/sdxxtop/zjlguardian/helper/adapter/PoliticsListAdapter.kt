package com.sdxxtop.zjlguardian.helper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.data.Politic
import com.sdxxtop.zjlguardian.data.PoliticsListBean
import com.sdxxtop.zjlguardian.databinding.ItemPoliticsListRecyclerBinding

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-24 17:30
 * Version: 1.0
 * Description:
 */
class PoliticsListAdapter(layoutResId: Int = R.layout.item_politics_list_recycler) : BaseQuickAdapter<Politic, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: Politic?) {
        val bind = DataBindingUtil.bind<ItemPoliticsListRecyclerBinding>(helper?.itemView!!)
        bind?.politic = item;
        bind?.executePendingBindings()
    }


}