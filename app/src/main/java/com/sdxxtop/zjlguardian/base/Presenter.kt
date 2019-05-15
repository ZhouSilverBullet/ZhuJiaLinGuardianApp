package com.sdxxtop.zjlguardian.base

import android.view.View

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-15 21:18
 * Version: 1.0
 * Description:
 */
interface Presenter : View.OnClickListener {

    fun loadData(isRefresh:Boolean)
    override fun onClick(v: View?)
}