package com.sdxxtop.zjlguardian.ui.politics

import androidx.lifecycle.ViewModel
import com.sdxxtop.model.bean.RequestBean
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-22 16:37
 * Version: 1.0
 * Description:
 */
class PoliticsViewModel : ViewModel() {
    var searchContent = ""
    var isOpen = false


    fun load() {
        val params = Params()
        val eventShowPart = RetrofitHelper.getGuardianService().postEventShowPart(params.data)
        RxUtils.handleHttp(eventShowPart, object : IRequestCallback<RequestBean<Any>> {
            override fun onSuccess(t: RequestBean<Any>?) {
            }

            override fun onFailure(code: Int, error: String?) {
            }

        })
    }
}