package com.sdxxtop.zjlguardian.presenter;

import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.presenter.constract.LoginConfirmContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class LoginConfirmPresenter extends GRxPresenter<LoginConfirmContract.IView> implements LoginConfirmContract.IPresenter {
    @Inject
    public LoginConfirmPresenter() {
    }

    public void loadData(int userid) {
        Params params = new Params();
        params.put("ui", userid);
        Observable<RequestBean> observable = getService().postLoginConfirm(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                mView.confirmSuccess();
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
