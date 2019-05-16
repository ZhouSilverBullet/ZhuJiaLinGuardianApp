package com.sdxxtop.zjlguardian.base;

import com.sdxxtop.base.BaseView;
import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.zjlguardian.http.GuardianService;
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 10:48
 * Version: 1.0
 * Description:
 */
public abstract class GuardianRxPresenter<T extends BaseView> extends RxPresenter<T> {

    public GuardianService getService() {
        return RetrofitHelper.getGuardianService();
    }
}
