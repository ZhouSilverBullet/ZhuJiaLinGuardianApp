package com.sdxxtop.zjlguardian.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdxxtop.app.Constants
import com.sdxxtop.model.bean.RequestBean
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.SpUtil
import com.sdxxtop.zjlguardian.data.RegisterBean
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-21 16:41
 * Version: 1.0
 * Description:
 */
class RegisterViewModel : ViewModel() {
    var phone: String = "";
    var username: String = "";

    val registerLiveData = MutableLiveData<RegisterBean>()
    val registerErrorLiveData = MutableLiveData<String>()


    fun load() {

        val params = Params()
        params.put("mb", phone)
        params.put("n", username)
        val loginRegister = RetrofitHelper.getGuardianService()
                .postLoginRegister(params.data)
        RxUtils.handleDataHttp(loginRegister, object : IRequestCallback<RegisterBean> {
            override fun onFailure(code: Int, error: String?) {
                registerErrorLiveData.value = error
            }

            override fun onSuccess(t: RegisterBean) {
                registerLiveData.value = t;
                SpUtil.putInt(Constants.USER_ID, t.userid.toInt())
                SpUtil.putInt(Constants.EXPIRE_TIME, t.expire_time)
                SpUtil.putString(Constants.AUTO_TOKEN, t.auto_token)
            }

        })
    }
}