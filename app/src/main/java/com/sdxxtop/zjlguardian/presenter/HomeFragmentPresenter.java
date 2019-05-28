package com.sdxxtop.zjlguardian.presenter;


import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.presenter.bean.MainIndexBean;
import com.sdxxtop.zjlguardian.presenter.constract.HomeFragmentContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HomeFragmentPresenter extends GRxPresenter<HomeFragmentContract.IView> implements HomeFragmentContract.IPresenter {
    @Inject
    public HomeFragmentPresenter() {
    }

    @Override
    public void loadData() {
        Params params = new Params();
        Observable<RequestBean<MainIndexBean>> observable = getService().postMainIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<MainIndexBean>() {
            @Override
            public void onSuccess(MainIndexBean mainIndexBean) {
                mView.showData(mainIndexBean);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void loadSignData() {
    }

    @Override
    public void loadInfo() {

    }
}
