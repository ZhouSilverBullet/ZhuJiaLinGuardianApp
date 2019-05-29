package com.sdxxtop.zjlguardian.extens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.amap.api.mapcore.util.it
import com.sdxxtop.utils.StatusBarUtil
import com.sdxxtop.utils.UIUtils
import com.sdxxtop.zjlguardian.BuildConfig
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.helper.KConstants
import com.sdxxtop.zjlguardian.helper.annotations.ToastType
import es.dmoral.toasty.Toasty
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


/**
 * 页面描述：一些扩展
 *
 * Created by ditclear on 2017/9/29.
 */

fun Activity.getCompactColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Activity.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT, @ToastType type: Int = ToastType.NORMAL) {
//    when (type) {
//        ToastType.WARNING -> Toasty.warning(this, msg, duration, true).show()
//        ToastType.ERROR -> Toasty.error(this, msg, duration, true).show()
//        ToastType.NORMAL -> Toasty.info(this, msg, duration, false).show()
//        ToastType.SUCCESS -> Toasty.success(this, msg, duration, true).show()
//    }
    UIUtils.showToast(msg.toString())
}

fun Activity.dispatchFailure(error: Throwable?) {
    error?.let {
        if (BuildConfig.DEBUG) {
            it.printStackTrace()
        }
        if (error is SocketTimeoutException) {
            it.message?.let { toast("网络连接超时", ToastType.ERROR) }

        } else if (it is UnknownHostException || it is ConnectException) {
            //网络未连接
            it.message?.let { toast("网络未连接", ToastType.ERROR) }

        } else {
            it.message?.let { toast(it, ToastType.ERROR) }
        }
    }
}

fun Activity.statusBar(isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上系统
        StatusBarUtil.setDarkStatusIcon(this.getWindow(), isDark)
    }
}

fun <T : Any> FragmentActivity.argument(key: String) =
        lazy { intent.extras[key] as? T ?: error("Intent Argument $key is missing") }

fun AppCompatActivity.switchFragment(current: Fragment?, targetFg: Fragment, tag: String? = null) {
    val ft = supportFragmentManager.beginTransaction()
    current?.run { ft.hide(this) }
    if (!targetFg.isAdded) {
        ft.add(R.id.container, targetFg, tag)
    }
    ft.show(targetFg)
    ft.commitAllowingStateLoss();
}

fun Activity.dpToPx(@DimenRes resID: Int): Int = this.resources.getDimensionPixelOffset(resID)

fun Activity.navigateToActivity(c: Class<*>, serializable: Serializable? = null) {
    val intent = Intent()
    serializable?.let {
        val bundle = Bundle()
        bundle.putSerializable(KConstants.KEY_SERIALIZABLE, it)
        intent.putExtras(bundle)
    }
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()

    intent.setClass(this, c)
    startActivity(intent, options)
}

fun Any.logD(msg: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(javaClass.simpleName, msg)
    }
}

fun <T> Flowable<T>.async(withDelay: Long = 0): Flowable<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

//fun <R : BaseResponse> Single<R>.getOriginData(): Single<R> {
//    return this.compose { upstream ->
//        upstream.flatMap { t: R ->
//            with(t) {
//                if (t.success == 1) {
//                    return@flatMap Single.just(t)
//                } else {
//                    return@flatMap Single.error<R>(Throwable(message))
//                }
//            }
//        }
//    }
//}

//fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
//        this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))
//
//fun <T> Flowable<T>.bindLifeCycle(owner: LifecycleOwner): FlowableSubscribeProxy<T> =
//        this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))


//fun Activity.navigateToWebPage(@NonNull url: String) {
//    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
//        return
//    }
//
//    val intent = CustomTabsIntent.Builder()
//            .setShowTitle(true)
//            .setToolbarColor(ContextCompat.getColor(this, R.color.theme))
//            .build()
//
//    intent.launchUrl(this, Uri.parse(url))
//}
//
//fun Fragment.navigateToWebPage(@NonNull url: String?) {
//    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
//        return
//    }
//    context?.let {
//        val intent = CustomTabsIntent.Builder()
//                .setShowTitle(true)
//                .setToolbarColor(ContextCompat.getColor(it, R.color.theme))
//                .build()
//
//        intent.launchUrl(activity, Uri.parse(url))
//    }
//}

//////////////////////////LiveData///////////////////////////////////

fun <T> MutableLiveData<T>.set(t: T?) = this.postValue(t)
fun <T> MutableLiveData<T>.get() = this.value

fun <T> MutableLiveData<T>.get(t: T): T = get() ?: t

fun <T> MutableLiveData<T>.init(t: T) = MutableLiveData<T>().apply {
    postValue(t)
}

fun <T> LiveData<T>.toFlowable(): Flowable<T> = Flowable.create({ emitter ->
    val observer = Observer<T> { data ->
        data?.let { emitter.onNext(it) }
    }
    observeForever(observer)

    emitter.setCancellable {
        object : MainThreadDisposable() {
            override fun onDispose() = removeObserver(observer)
        }
    }
}, BackpressureStrategy.LATEST)

//////////////////////////DataBinding///////////////////////////////////
fun <T> ObservableField<T>.toFlowable(): Flowable<T> = Flowable.create({ emitter ->
    val observer = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            get()?.let { emitter.onNext(it) }
        }
    }
    addOnPropertyChangedCallback(observer)

    emitter.setCancellable {
        object : MainThreadDisposable() {
            override fun onDispose() = removeOnPropertyChangedCallback(observer)
        }
    }
}, BackpressureStrategy.LATEST)

fun ObservableBoolean.toFlowable(): Flowable<Boolean> = Flowable.create({ emitter ->
    val observer = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            emitter.onNext(get())
        }
    }
    addOnPropertyChangedCallback(observer)

    emitter.setCancellable {
        object : MainThreadDisposable() {
            override fun onDispose() = removeOnPropertyChangedCallback(observer)
        }
    }
}, BackpressureStrategy.LATEST)