package com.sdxxtop.zjlguardian.ui.politics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPoliticsSearchBinding

class PoliticsSearchActivity : KBaseActivity<ActivityPoliticsSearchBinding>() {
    override fun initView() {
        val content = intent.getStringExtra("content")

        mBinding.vm = bindViewModel(PoliticsSearchViewModel::class.java)

        mBinding.vm?.load(content)
    }

    override fun getLayoutId() = R.layout.activity_politics_search

    override fun loadData(isRefresh: Boolean) {

    }

}
