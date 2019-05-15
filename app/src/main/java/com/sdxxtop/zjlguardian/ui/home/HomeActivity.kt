package com.sdxxtop.zjlguardian.ui.home


import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityHomeBinding

class HomeActivity : KBaseActivity<ActivityHomeBinding>() {

    override fun getLayoutId() = R.layout.activity_home

    override fun loadData(isRefresh: Boolean) {

    }

    override fun initView() {
        mBinding.toolbar.title = ""
        setSupportActionBar(mBinding.toolbar)
    }


}
