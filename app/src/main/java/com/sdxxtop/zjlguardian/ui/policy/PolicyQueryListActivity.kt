package com.sdxxtop.zjlguardian.ui.policy

import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPolicyQueryListBinding

class PolicyQueryListActivity : KBaseActivity<ActivityPolicyQueryListBinding>() {
    var mineId = 0;
    var findId = 0;
    var title = "";

    override fun initView() {
        mBinding.vm = bindViewModel(PolicyQueryListViewModel::class.java)
    }

    override fun initObserver() {
        title = intent.getStringExtra("title");
        mineId = intent.getIntExtra("mineId", 0);
        findId = intent.getIntExtra("findId", 0);
    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.push(title, mineId, findId)
    }

    override fun getLayoutId() = R.layout.activity_policy_query_list

}
