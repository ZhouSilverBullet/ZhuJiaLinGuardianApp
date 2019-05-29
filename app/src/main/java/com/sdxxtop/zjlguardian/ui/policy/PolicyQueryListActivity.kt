package com.sdxxtop.zjlguardian.ui.policy

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.utils.ItemDivider
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPolicyQueryListBinding
import com.sdxxtop.zjlguardian.ui.policy.data.Policy

class PolicyQueryListActivity : KBaseActivity<ActivityPolicyQueryListBinding>() {
    var mineId = 0;
    var findId = 0;
    var title = "";
    var isSearch = false;
    val myAdapter = MyAdapter()

    override fun initView() {
        mBinding.vm = bindViewModel(PolicyQueryListViewModel::class.java)
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.addItemDecoration(ItemDivider()
                .setDividerWidth(UIUtils.dip2px(1))
                .setLastLineNotDraw(true))
        mBinding.rv.adapter = myAdapter


        mBinding.srfLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                mBinding.vm?.isPullLoad = true
                mBinding.vm?.push(title, mineId, findId, mBinding.rv.adapter?.itemCount!!)
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                mBinding.vm?.isPullLoad = false
                mBinding.vm?.push(title, mineId, findId, 0)

            }

        })
    }

    override fun initObserver() {
        title = intent.getStringExtra("title");
        mineId = intent.getIntExtra("mineId", 0);
        findId = intent.getIntExtra("findId", 0);
        isSearch = intent.getBooleanExtra("isSearch", false);

        mBinding.vm?.isSearch = isSearch

        mBinding.vm?.mPolicy?.observe(this, Observer {
            mBinding.tvContentTitle.text = mBinding.vm?.titleValue

            if (mBinding.vm?.isPullLoad ?: false) {
                myAdapter.addData(it)
            } else {
                myAdapter.replaceData(it)
            }

            mBinding.srfLayout.finishLoadMore()
            mBinding.srfLayout.finishRefresh()
        })
    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.push(title, mineId, findId, mBinding.rv.adapter?.itemCount!!)
    }

    override fun getLayoutId() = R.layout.activity_policy_query_list


    class MyAdapter(var layoutId: Int = R.layout.item_policy_query_list_recycler) : BaseQuickAdapter<Policy, BaseViewHolder>(layoutId) {

        override fun convert(helper: BaseViewHolder?, item: Policy?) {
            val textView = helper?.getView<TextView>(R.id.tv_text)
            textView?.text = item?.title
        }

    }
}
