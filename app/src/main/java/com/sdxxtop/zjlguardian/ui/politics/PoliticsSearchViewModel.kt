package com.sdxxtop.zjlguardian.ui.politics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdxxtop.model.bean.RequestBean
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zjlguardian.base.BaseViewModel
import com.sdxxtop.zjlguardian.data.Politic
import com.sdxxtop.zjlguardian.data.PoliticsListBean
import com.sdxxtop.zjlguardian.extens.set
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper
import com.sdxxtop.zjlguardian.ui.feedback.data.FeedbackData
import com.sdxxtop.zjlguardian.ui.feedback.data.Proposal

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-23 15:18
 * Version: 1.0
 * Description:
 */
class PoliticsSearchViewModel : BaseViewModel() {

    val mPoliticData = MutableLiveData<List<Politic>>()
    val mFeedbackData = MutableLiveData<List<Proposal>>()
    var isPullLoad = false
    var etValue = ""

    fun load(isSearch: Boolean, currentPage: Int, content: String?) {
        val params = Params()
        params.put("tl", content ?: "")
        params.put("ih", if (isSearch) 2 else 1)
        params.put("lt", 10)
        params.put("st", currentPage)

        val politicsSearch = RetrofitHelper.getGuardianService().postPoliticsSearch(params.data)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<PoliticsListBean> {
            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

            override fun onSuccess(t: PoliticsListBean?) {
                mPoliticData.set(t?.politics)
            }

        })

        addDisposable(disposable)
    }

    fun loadFeedback(isSearch: Boolean, currentPage: Int, content: String?) {
        val params = Params()
        params.put("tl", content ?: "")
        params.put("ih", if (isSearch) 2 else 1)
        params.put("lt", 10)
        params.put("st", currentPage)

        val politicsSearch = RetrofitHelper.getGuardianService().postProposalSearch(params.data)
        val disposable = RxUtils.handleDataHttp(politicsSearch, object : IRequestCallback<FeedbackData> {
            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

            override fun onSuccess(t: FeedbackData?) {
                mFeedbackData.set(t?.proposal)
            }

        })

        addDisposable(disposable)
    }

}