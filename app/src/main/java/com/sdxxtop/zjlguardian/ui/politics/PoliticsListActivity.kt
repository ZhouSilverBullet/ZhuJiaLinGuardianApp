package com.sdxxtop.zjlguardian.ui.politics

import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPoliticsListBinding

class PoliticsListActivity : KBaseActivity<ActivityPoliticsListBinding>() {

    var politicsId = 0

    override fun getLayoutId() = R.layout.activity_politics_list


    override fun initView() {
        politicsId = intent.getIntExtra("politicsId", 0)
    }


    override fun loadData(isRefresh: Boolean) {
    }
}
