package com.sdxxtop.zjlguardian.ui.notice

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityNoticeBinding
import com.sdxxtop.zjlguardian.ui.notice.adapter.NoticeAdapter
import com.sdxxtop.zjlguardian.ui.notice.adapter.NoticeSearchAdapter

class NoticeActivity : KBaseActivity<ActivityNoticeBinding>(), TextWatcher {
    override fun getLayoutId() = R.layout.activity_notice

    val noticeAdapter = NoticeAdapter()
    val searchAdapter = NoticeSearchAdapter()

    override fun initView() {
        mBinding.vm = bindViewModel(NoticeViewModel::class.java)

        mBinding.etSearch.addTextChangedListener(this)
        mBinding.tvCancel.setOnClickListener(View.OnClickListener {
            //清除
            mBinding.etSearch.setText("")
        })

        mBinding.etSearch.setText("")

        initRecycler()
    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.load(0, "")
    }

    private fun initRecycler() {
        mBinding.rv.layoutManager = LinearLayoutManager(this)

        mBinding.rv.adapter = noticeAdapter

        mBinding.vm?.mNoticeData?.observe(this, Observer {
            if (mBinding.vm?.isPullLoad ?: false) {
                noticeAdapter.addData(it)
            } else {
                noticeAdapter.replaceData(it)
            }

            mBinding.srlLayout.finishLoadMore()
            mBinding.srlLayout.finishRefresh()
        })

        mBinding.vm?.mSearchNoticeData?.observe(this, Observer {
            if (mBinding.vm?.isPullLoad ?: false) {
                searchAdapter.addData(it)
            } else {
                searchAdapter.replaceData(it)
            }

            mBinding.srlLayout.finishLoadMore()
            mBinding.srlLayout.finishRefresh()
        })

        mBinding.srlLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                if (refreshLayout != null) {
                    mBinding.vm?.isPullLoad = true
                    //是否是搜索
                    if (mBinding.vm?.isSearch ?: false) {
                        mBinding.vm?.load(0, "")
                    } else {
                        mBinding.vm?.loadSearch(0, "")
                    }
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                if (refreshLayout != null) {
                    mBinding.vm?.isPullLoad = false

                    //是否是搜索
                    if (mBinding.vm?.isSearch ?: false) {
                        mBinding.vm?.load(0, "")
                    } else {
                        mBinding.vm?.loadSearch(0, "")
                    }
                }
            }

        });
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        mBinding.vm?.isPullLoad = false
        mBinding.vm?.etValue = s.toString()
        if (!TextUtils.isEmpty(s)) {

            mBinding.vm?.isSearch = true

            mBinding.rv.adapter = searchAdapter
            mBinding.tvCancel.setVisibility(View.VISIBLE)
            mBinding.vm?.loadSearch(0, s.toString())
        } else {

            mBinding.rv.adapter = noticeAdapter

            mBinding.vm?.isSearch = false

            mBinding.vm?.load(0, "")
            mBinding.tvCancel.setVisibility(View.GONE)
        }
    }

    override fun afterTextChanged(s: Editable) {

    }
}
