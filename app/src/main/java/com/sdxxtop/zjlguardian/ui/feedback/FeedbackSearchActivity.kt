package com.sdxxtop.zjlguardian.ui.feedback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPoliticsSearchBinding
import com.sdxxtop.zjlguardian.helper.adapter.FeedbackListAdapter
import com.sdxxtop.zjlguardian.helper.adapter.PoliticsListAdapter
import com.sdxxtop.zjlguardian.ui.politics.PoliticsSearchViewModel
import kotlinx.android.synthetic.main.activity_politics_list.*

class FeedbackSearchActivity : KBaseActivity<ActivityPoliticsSearchBinding>(), TextWatcher {
    var isSearch = false
    override fun initView() {
        val content = intent.getStringExtra("content")

        var title = intent.getStringExtra("title")

        isSearch = intent.getBooleanExtra("isSearch", false)

        tv_title.setTitleValue(title)

        mBinding.vm = bindViewModel(PoliticsSearchViewModel::class.java)

        mBinding.etSearch.addTextChangedListener(this)
        mBinding.tvCancel.setOnClickListener(View.OnClickListener {
            //清除
            mBinding.etSearch.setText("")
        })

        mBinding.etSearch.setText(content)

        initRecycler()
    }

    private fun initRecycler() {
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        val feedbackListAdapter = FeedbackListAdapter(isSearch = isSearch)
        mBinding.rv.adapter = feedbackListAdapter

        mBinding.vm?.mFeedbackData?.observe(this, Observer {
            if (mBinding.vm?.isPullLoad ?: false) {
                feedbackListAdapter.addData(it)
            } else {
                feedbackListAdapter.replaceData(it)
            }

            mBinding.srlLayout.finishLoadMore()
            mBinding.srlLayout.finishRefresh()
        })

        mBinding.srlLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                if (refreshLayout != null) {
                    mBinding.vm?.isPullLoad = true
                    mBinding.vm?.loadFeedback(isSearch, feedbackListAdapter.itemCount, mBinding.vm?.etValue)
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                if (refreshLayout != null) {
                    mBinding.vm?.isPullLoad = false
                    mBinding.vm?.loadFeedback(isSearch, feedbackListAdapter.itemCount, mBinding.vm?.etValue)
                }
            }

        });
    }

    override fun getLayoutId() = R.layout.activity_politics_search

    override fun loadData(isRefresh: Boolean) {

    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        mBinding.vm?.isPullLoad = false
        mBinding.vm?.etValue = s.toString()
        if (!TextUtils.isEmpty(s)) {
//            mPresenter.searchData(s.toString())
            mBinding.tvCancel.setVisibility(View.VISIBLE)
            mBinding.vm?.loadFeedback(isSearch, mBinding.rv.adapter?.itemCount ?: 0, s.toString())
        } else {
//            mPresenter.loadData()
            //            mAdapter.replaceData(new ArrayList<>());
            (mBinding.rv.adapter as FeedbackListAdapter).addData(ArrayList())
            mBinding.tvCancel.setVisibility(View.GONE)
        }
    }

    override fun afterTextChanged(s: Editable) {

    }

}
