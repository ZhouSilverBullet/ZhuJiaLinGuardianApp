package com.sdxxtop.zjlguardian.ui.feedback

import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPoliticsSearchBinding
import com.sdxxtop.zjlguardian.helper.adapter.FeedbackListAdapter
import com.sdxxtop.zjlguardian.ui.politics.PoliticsSearchViewModel
import kotlinx.android.synthetic.main.activity_politics_list.*
import kotlinx.android.synthetic.main.activity_politics_list.tv_title
import kotlinx.android.synthetic.main.activity_politics_search.*
import org.jetbrains.anko.startActivity


class FeedbackSearchActivity : KBaseActivity<ActivityPoliticsSearchBinding>(), TextWatcher {
    var isSearch = false
    var isToEditSkip = false

    override fun initView() {
        val content = intent.getStringExtra("content")

        var title = intent.getStringExtra("title")

        isSearch = intent.getBooleanExtra("isSearch", false)
        isToEditSkip = intent.getBooleanExtra("isToEditSkip", false)

        tv_title.setTitleValue(title)

        mBinding.vm = bindViewModel(PoliticsSearchViewModel::class.java)

        mBinding.etSearch.addTextChangedListener(this)
        mBinding.tvCancel.setOnClickListener(View.OnClickListener {
            //清除
            mBinding.etSearch.setText("")
        })

        mBinding.etSearch.setText(content)

        initRecycler()

        if (!isSearch) {
            mBinding.rlSearchBackground.visibility = View.GONE
//            mBinding.srlLayout.autoRefresh()
        }

        setTvEmpty()
    }

    private fun setTvEmpty() {

        if (!isSearch) {
            mBinding.tvTopEmpty.setText("没有找到内容")
        }

        val spannableString = SpannableString("快去主动发送信件吧")
//        val strikethroughSpan = StrikethroughSpan()
//        spannableString.setSpan(strikethroughSpan, 4, spannableString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#14C8B3"))
        spannableString.setSpan(colorSpan, 4, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        val underlineSpan = UnderlineSpan()
        spannableString.setSpan(underlineSpan, 4, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mBinding.tvEmpty.setText(spannableString)
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

            handleEmpty(mBinding.rv.adapter?.itemCount == 0)

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
                    mBinding.vm?.loadFeedback(isSearch, 0, mBinding.vm?.etValue)
                }
            }

        });
    }

    override fun getLayoutId() = com.sdxxtop.zjlguardian.R.layout.activity_politics_search

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
            mBinding.vm?.loadFeedback(isSearch, 0, s.toString())
        } else {
            mBinding.vm?.loadFeedback(isSearch, 0, mBinding.vm?.etValue)
//            if (mBinding.rv.adapter is FeedbackListAdapter) {
//                (mBinding.rv.adapter as FeedbackListAdapter).addData(ArrayList())
//            }
//            mBinding.tvCancel.setVisibility(View.GONE)
        }
    }

    override fun afterTextChanged(s: Editable) {

    }

    fun handleEmpty(isEmpty: Boolean) {
        mBinding.llEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.tvEmpty -> {
                if (isToEditSkip) { //搜索页面过来的，直接结束就好了
                    finish()
                } else {
                    startActivity<FeedbackActivity>()
                    finish()
                }
            }
        }
    }

}
