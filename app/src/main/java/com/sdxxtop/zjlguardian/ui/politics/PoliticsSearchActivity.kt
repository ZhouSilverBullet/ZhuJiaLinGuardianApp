package com.sdxxtop.zjlguardian.ui.politics

import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPoliticsSearchBinding
import com.sdxxtop.zjlguardian.helper.adapter.PoliticsListAdapter
import com.sdxxtop.zjlguardian.ui.feedback.FeedbackSearchActivity
import kotlinx.android.synthetic.main.activity_politics_list.*
import org.jetbrains.anko.startActivity

class PoliticsSearchActivity : KBaseActivity<ActivityPoliticsSearchBinding>(), TextWatcher {
    var isSearch = false
    var isToEditSkip = false

    var isFirst = true

    override fun initView() {
        val content = intent.getStringExtra("content")

        var title = intent.getStringExtra("title")
        tv_title.setTitleValue(title)

        isSearch = intent.getBooleanExtra("isSearch", false)

        isToEditSkip = intent.getBooleanExtra("isToEditSkip", false)

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

        val spannableString = SpannableString("快去主动发起问政吧")
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
        val politicsListAdapter = PoliticsListAdapter(isSearch = isSearch)
        mBinding.rv.adapter = politicsListAdapter

        mBinding.vm?.mPoliticData?.observe(this, Observer {
            if (mBinding.vm?.isPullLoad ?: false) {
                politicsListAdapter.addData(it)
            } else {
                politicsListAdapter.replaceData(it)
            }

//            if (!isFirst) {
                handleEmpty(mBinding.rv.adapter?.itemCount == 0)
//            }

//            isFirst = false

            mBinding.srlLayout.finishLoadMore()
            mBinding.srlLayout.finishRefresh()
        })

        mBinding.srlLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                if (refreshLayout != null) {
                    mBinding.vm?.isPullLoad = true
                    mBinding.vm?.load(isSearch, politicsListAdapter.itemCount, mBinding.vm?.etValue)
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                if (refreshLayout != null) {
                    mBinding.vm?.isPullLoad = false
                    mBinding.vm?.load(isSearch, 0, mBinding.vm?.etValue)
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
            mBinding.vm?.load(isSearch,  0, s.toString())
        } else {
            mBinding.vm?.load(isSearch,  0, s.toString())
//            if (mBinding.rv.adapter is PoliticsListAdapter) {
//                (mBinding.rv.adapter as PoliticsListAdapter).addData(ArrayList())
//            }
//
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
                if (isToEditSkip) {
                    finish()
                } else {
                    startActivity<PoliticsActivity>()
                    finish()
                }
            }
        }
    }

}
