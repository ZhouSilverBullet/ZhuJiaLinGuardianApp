package com.sdxxtop.zjlguardian.ui.politics

import androidx.lifecycle.ViewModel
import com.sdxxtop.model.bean.RequestBean
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-23 15:18
 * Version: 1.0
 * Description:
 */
class PoliticsSearchViewModel : ViewModel() {
    fun load(content: String?) {
        val params = Params()
        params.put("tl", content ?: "")
        params.put("ih", 2)
        val politicsSearch = RetrofitHelper.getGuardianService().postPoliticsSearch(params.data)
        RxUtils.handleHttp(politicsSearch, object : IRequestCallback<RequestBean<Any>> {
            override fun onFailure(code: Int, error: String?) {
            }

            override fun onSuccess(t: RequestBean<Any>?) {
            }

        })

    }

}