package com.sdxxtop.zjlguardian.ui.politics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luck.picture.lib.entity.LocalMedia
import com.sdxxtop.model.bean.RequestBean
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.ImageParams
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zjlguardian.base.BaseViewModel
import com.sdxxtop.zjlguardian.data.PartBean
import com.sdxxtop.zjlguardian.data.PushDataBean
import com.sdxxtop.zjlguardian.extens.set
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper
import java.io.File

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-22 16:37
 * Version: 1.0
 * Description:
 */
class PoliticsViewModel : BaseViewModel() {


    var searchContent = ""
    var isOpen = false

    var partBean: ArrayList<PartBean>? = null
    val pushSuccess = MutableLiveData<String>()


    fun load() {
        val params = Params()
        val eventShowPart = RetrofitHelper.getGuardianService().postEventShowPart(params.data)
        val disposable = RxUtils.handleDataHttp(eventShowPart, object : IRequestCallback<ArrayList<PartBean>> {
            override fun onSuccess(t: ArrayList<PartBean>?) {
                t?.add(0, PartBean(0, "我不知道部门", false))
                partBean = t
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

        })
//        val disposable = RxUtils.handleHttp(eventShowPart, object : IRequestCallback<RequestBean<Any>> {
//            override fun onSuccess(t: RequestBean<Any>?) {
//            }
//
//            override fun onFailure(code: Int, error: String?) {
//            }
//
//        })

        addDisposable(disposable)
    }

    fun pushPolitics(partId: Int, titleValue: String, contentValue: String, imgList: List<File>) {
        val params = ImageParams()
        params.put("rd", partId)
        params.put("tl", titleValue)
        //是否匿名
        params.put("tp", if (isOpen) 0 else 1)
        params.put("ct", contentValue)
//        params.put("ct", contentValue)
        params.addImagePathList("img[]", imgList)

        val eventShowPart = RetrofitHelper.getGuardianService().postPoliticsConfirm(params.imgData)
        val disposable = RxUtils.handleDataHttp(eventShowPart, object : IRequestCallback<PushDataBean> {
            override fun onSuccess(t: PushDataBean?) {
                pushSuccess.value = t?.url
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

        })
        addDisposable(disposable)
    }
}