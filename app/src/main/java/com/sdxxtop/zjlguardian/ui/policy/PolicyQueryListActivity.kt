package com.sdxxtop.zjlguardian.ui.policy

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPolicyQueryListBinding
import com.sdxxtop.zjlguardian.ui.policy.data.Policy

class PolicyQueryListActivity : KBaseActivity<ActivityPolicyQueryListBinding>() {
    var mineId = 0;
    var findId = 0;
    var title = "";
    val myAdapter = MyAdapter()

    override fun initView() {
        mBinding.vm = bindViewModel(PolicyQueryListViewModel::class.java)
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.adapter = myAdapter

    }

    override fun initObserver() {
        title = intent.getStringExtra("title");
        mineId = intent.getIntExtra("mineId", 0);
        findId = intent.getIntExtra("findId", 0);

        mBinding.vm?.mPolicy?.observe(this, Observer {
            myAdapter.replaceData(it)
        })
    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.push(title, mineId, findId)
    }

    override fun getLayoutId() = R.layout.activity_policy_query_list


    class MyAdapter(var layoutId: Int = R.layout.item_policy_query_list_recycler) : BaseQuickAdapter<Policy, BaseViewHolder>(layoutId) {

        override fun convert(helper: BaseViewHolder?, item: Policy?) {
            val textView = helper?.getView<TextView>(R.id.tv_text)
            textView?.text = item?.title
        }

    }
}
