package com.sdxxtop.zjlguardian.ui.splash;


import com.sdxxtop.app.Constants;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.SpUtil;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.data.AutoLoginBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SplashPresenter extends GRxPresenter<SplashContract.IView> implements SplashContract.IPresenter {
    @Inject
    public SplashPresenter() {
    }

    public void autoLogin() {
        Params params = new Params();
        params.put("at", SpUtil.getString(Constants.AUTO_TOKEN));
        Observable<RequestBean<AutoLoginBean>> observable = getService().postLoginAutoLogin(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<AutoLoginBean>() {
            @Override
            public void onSuccess(AutoLoginBean autoLoginBean) {
                handleData(autoLoginBean);
                mView.autoSuccess(autoLoginBean);
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }

    private void handleData(AutoLoginBean autoLoginBean) {

    }
}
