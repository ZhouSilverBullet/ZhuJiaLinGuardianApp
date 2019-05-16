package com.sdxxtop.zjlguardian.ui.home;


import com.sdxxtop.model.bean.InitBean;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HomePresenter extends GRxPresenter<HomeContract.IView> implements HomeContract.IPresenter {
    @Inject
    public HomePresenter() {
    }


    public void initApp() {
        Params params = new Params();
        params.put("pi", 1);
        Observable<RequestBean<InitBean>> observable = getService().postAppInit(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<InitBean>() {
            @Override
            public void onSuccess(InitBean initBean) {
                mView.showInit(initBean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
