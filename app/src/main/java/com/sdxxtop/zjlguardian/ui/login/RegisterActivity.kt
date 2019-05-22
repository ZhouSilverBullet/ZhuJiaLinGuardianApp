package com.sdxxtop.zjlguardian.ui.login

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityRegisterBinding
import com.sdxxtop.zjlguardian.ui.home.HomeTabActivity
import org.jetbrains.anko.startActivity

class RegisterActivity : KBaseActivity<ActivityRegisterBinding>() {
    override fun initView() {
        val phone = intent.getStringExtra("phone")
        mBinding.vm = ViewModelProviders.of(this)[RegisterViewModel::class.java]
        mBinding.vm?.phone = phone;
        mBinding.vm?.username = ""

        mBinding.vm?.registerLiveData?.observe(this, Observer {
            hideLoadingDialog()
            startActivity<HomeTabActivity>()
        })

        mBinding.vm?.registerErrorLiveData?.observe(this, Observer {
            hideLoadingDialog()
        })

    }

    override fun getLayoutId() = R.layout.activity_register

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.btnLogin -> {
                showLoadingDialog()
                mBinding.vm?.load()
            }
        }
    }

}
