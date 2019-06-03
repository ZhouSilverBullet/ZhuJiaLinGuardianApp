package com.sdxxtop.zjlguardian.ui.feedback

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.ImageParams
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zjlguardian.base.BaseViewModel
import com.sdxxtop.zjlguardian.data.PartBean
import com.sdxxtop.zjlguardian.extens.set
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper
import com.sdxxtop.zjlguardian.ui.feedback.data.ProposalBean
import com.sdxxtop.zjlguardian.ui.policy.data.PolicyQueryBean
import java.io.File

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-28 10:06
 * Version: 1.0
 * Description:
 */

class FeedbackViewModel : BaseViewModel() {
    //默认为不选
    //1.意见 2.建议 3.投诉
    var type = 0
    var typeCheck = false
    var open = false
    var searchContent = ""

    var mProposalIdData = MutableLiveData<String>()

    var partBean: ArrayList<PartBean>? = null


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

        addDisposable(disposable)
    }


    fun pushFeedback(status: Int, partId: Int, titleValue: String, contentValue: String, imgList: List<File>) {
        val params = ImageParams()
        params.put("pi", partId)
        params.put("tl", titleValue)
        //是否匿名
        params.put("tp", if (open) 0 else 1)
        params.put("ct", contentValue)
        params.put("ss", status)
//        params.put("ct", contentValue)
        params.addImagePathList("img[]", imgList)
        val politicsSearch = RetrofitHelper.getGuardianService().postProposalPolics(params.imgData)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<ProposalBean> {
            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

            override fun onSuccess(t: ProposalBean?) {
//                mPolicyQueryData.set(t)
                mProposalIdData.set(t?.url)
            }
        })

        addDisposable(disposable)

    }

}