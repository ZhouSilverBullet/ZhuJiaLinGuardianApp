package com.sdxxtop.zjlguardian.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.noober.background.BackgroundLibrary
import com.sdxxtop.utils.DialogUtil
import com.sdxxtop.zjlguardian.BR
import com.sdxxtop.zjlguardian.extens.statusBar
import me.yokeyword.fragmentation.SupportActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-15 21:11
 * Version: 1.0
 * Description:
 */
abstract class KBaseActivity<VB : ViewDataBinding> : SupportActivity(), Presenter {
    protected val mBinding: VB by lazy { DataBindingUtil.setContentView<VB>(this, getLayoutId()) }
    protected lateinit var mContext: Context

    protected var autoRefresh = true
    protected val mDialogUtil: DialogUtil by lazy {
        DialogUtil()
    };

    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        mBinding.setVariable(BR.presenter, this)
        mBinding.executePendingBindings()
        mBinding.lifecycleOwner = this
        mContext = this

        //默认在6.0的时候设置为灰色
        this.statusBar(true)

        initView()
        initObserver()
        loadData(autoRefresh)
    }

    open fun initObserver() {

    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    override fun onClick(v: View?) {
    }

    fun showLoadingDialog() {
        mDialogUtil.showLoadingDialog(this)
    }

    fun hideLoadingDialog() {
        mDialogUtil.closeLoadingDialog()
    }

    fun <T : ViewModel> bindViewModel(clazz: Class<T>): T {
        return ViewModelProviders.of(this)[clazz]
    }

}