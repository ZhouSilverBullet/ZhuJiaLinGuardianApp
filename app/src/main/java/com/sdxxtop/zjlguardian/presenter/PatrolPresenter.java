package com.sdxxtop.zjlguardian.presenter;

import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.data.SignLogBean;
import com.sdxxtop.zjlguardian.presenter.constract.PatrolContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class PatrolPresenter extends GRxPresenter<PatrolContract.IView> implements PatrolContract.IPresenter {
    @Inject
    public PatrolPresenter() {
    }


    public void loadData(String date) {
        Params params = new Params();
        params.put("sd", date);
        Observable<RequestBean<SignLogBean>> observable = getService().postMainSignLog(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<SignLogBean>() {
            @Override
            public void onSuccess(SignLogBean signLogBean) {
                mView.showData(signLogBean);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }


}
