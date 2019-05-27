package com.sdxxtop.zjlguardian.ui.politics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPoliticsSearchBinding
import com.sdxxtop.zjlguardian.helper.adapter.PoliticsListAdapter

class PoliticsSearchActivity : KBaseActivity<ActivityPoliticsSearchBinding>(), TextWatcher {
    override fun initView() {
        val content = intent.getStringExtra("content")

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
        val politicsListAdapter = PoliticsListAdapter()
        mBinding.rv.adapter = politicsListAdapter

        mBinding.vm?.mPoliticData?.observe(this, Observer {
            politicsListAdapter.replaceData(it)
        })
    }

    override fun getLayoutId() = R.layout.activity_politics_search

    override fun loadData(isRefresh: Boolean) {

    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (!TextUtils.isEmpty(s)) {
//            mPresenter.searchData(s.toString())
            mBinding.tvCancel.setVisibility(View.VISIBLE)
            mBinding.vm?.load(s.toString())
        } else {
//            mPresenter.loadData()
            //            mAdapter.replaceData(new ArrayList<>());
            mBinding.tvCancel.setVisibility(View.GONE)
        }
    }

    override fun afterTextChanged(s: Editable) {

    }

}
