package com.sdxxtop.zjlguardian.ui.notice

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.MutableLiveData
import com.amap.api.mapcore.util.it
import com.sdxxtop.model.http.callback.IRequestCallback
import com.sdxxtop.model.http.net.Params
import com.sdxxtop.model.http.util.RxUtils
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.BaseViewModel
import com.sdxxtop.zjlguardian.extens.set
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper
import com.sdxxtop.zjlguardian.ui.notice.data.Notic
import com.sdxxtop.zjlguardian.ui.notice.data.NoticDateBean
import com.sdxxtop.zjlguardian.ui.notice.data.NoticeData


/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-28 18:09
 * Version: 1.0
 * Description:
 */
class NoticeViewModel : BaseViewModel() {

    val mNoticeData = MutableLiveData<ArrayList<NoticDateBean>>()
    val mSearchNoticeData = MutableLiveData<List<Notic>>()

    var isPullLoad = false
    var etValue = ""
    var isSearch = false

    fun load(startPage: Int?, title: String?) {
        val params = Params()
        params.put("lt", 10)
        params.put("st", startPage ?: 0)
        params.put("tl", title ?: "")
        val eventShowPart = RetrofitHelper.getGuardianService().postNoticeIndex(params.data)
        val disposable = RxUtils.handleDataHttp(eventShowPart, object : IRequestCallback<NoticeData> {
            override fun onSuccess(t: NoticeData?) {

                val list = ArrayList<NoticDateBean>()
                val map = HashMap<String, ArrayList<Notic>>();
                t?.notic_list?.forEach {
                    if (map.containsKey(it.time)) {
                        map[it.time]?.add(it)
                    } else {
                        map[it.time] = ArrayList()
                        map[it.time]?.add(it)
                    }
                }

                map.forEach {
                    list.add(NoticDateBean(it.key, it.value))
                }

                if (isPullLoad) {
                    mNoticeData.value?.forEach {
                        list.forEach { contentIt ->
                            if (contentIt.time.equals(it.time)) {
                                contentIt.notic_list.addAll(it.notic_list)
                            }
                        }
                    }
                    //如果上拉刷新为0的情况下，就不在设置adapter
                    if (list.size == 0) {
                        list.addAll(mNoticeData.value!!)
                    }
                }

                mNoticeData.set(list)
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

        })

        addDisposable(disposable)
    }

    fun loadSearch(startPage: Int?, title: String?) {
        val params = Params()
        params.put("lt", 10)
        params.put("st", startPage ?: 0)
        params.put("tl", title ?: "")
        val eventShowPart = RetrofitHelper.getGuardianService().postNoticeIndex(params.data)
        val disposable = RxUtils.handleDataHttp(eventShowPart, object : IRequestCallback<NoticeData> {
            override fun onSuccess(t: NoticeData?) {
                if (t?.notic_list == null) {
                    return
                }

                t.notic_list.forEach {
                    val str = it.title
                    if (!TextUtils.isEmpty(title)) {
                        if (str.contains(title!!)) {
                            val indexOf = str.indexOf(title)
                            val spannableString = SpannableString(str)
                            val colorSpan = ForegroundColorSpan(Color.parseColor("#14C8B3"))
                            spannableString.setSpan(colorSpan, indexOf, indexOf + title.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                            it.spanString = spannableString;
                        }
                    }
                }

                mSearchNoticeData.set(t.notic_list)
            }

            override fun onFailure(code: Int, error: String?) {
                UIUtils.showToast(error)
            }

        })

        addDisposable(disposable)
    }
}