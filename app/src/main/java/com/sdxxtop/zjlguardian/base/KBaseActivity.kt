package com.sdxxtop.zjlguardian.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sdxxtop.zjlguardian.BR
import com.sdxxtop.zjlguardian.extens.statusBar

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-15 21:11
 * Version: 1.0
 * Description:
 */
abstract class KBaseActivity<VB : ViewDataBinding> : AppCompatActivity(), Presenter {
    protected val mBinding: VB by lazy { DataBindingUtil.setContentView<VB>(this, getLayoutId()) }
    protected lateinit var mContext: Context

    protected var autoRefresh = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.setVariable(BR.presenter, this)
        mBinding.executePendingBindings()
        mBinding.lifecycleOwner = this
        mContext = this

        //默认在6.0的时候设置为灰色
        this.statusBar(true)

        initView()
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int

    override fun onClick(v: View?) {
    }

}