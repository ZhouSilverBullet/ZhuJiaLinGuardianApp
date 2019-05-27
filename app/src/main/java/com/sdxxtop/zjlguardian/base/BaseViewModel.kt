package com.sdxxtop.zjlguardian.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-24 15:34
 * Version: 1.0
 * Description:
 */

abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = lazy {
        CompositeDisposable()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.value.add(disposable)
    }

    fun remove() {
        compositeDisposable.value.clear()
    }
}