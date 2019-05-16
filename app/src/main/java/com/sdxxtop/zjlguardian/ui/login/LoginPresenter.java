package com.sdxxtop.zjlguardian.ui.login;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.data.LoginBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LoginPresenter extends GRxPresenter<LoginContract.IView> implements LoginContract.IPresenter {
    @Inject
    public LoginPresenter() {
    }


    @Override
    public void login(String mobile, String authCode, String partId) {
        Params params = new Params();
        params.put("mb", mobile);
        params.put("ac", authCode);
        params.put("pi", partId);
        Observable<RequestBean<LoginBean>> requestBeanObservable = getService()
                .postLoginMobileLogin(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(requestBeanObservable, new IRequestCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean loginBean) {
                mView.loginSuccess(loginBean);
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void sendCode(View flCode,TextView tvCode, ProgressBar pbCode, String mobile) {

        flCode.setEnabled(false);
        tvCode.setVisibility(View.GONE);
        pbCode.setVisibility(View.VISIBLE);
        Params params = new Params();
        params.put("mb", mobile);

        Observable<RequestBean> observable = getService().postLoginSendCode(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                pbCode.setVisibility(View.GONE);
                mView.sendCodeSuccess();
                UIUtils.showToast("发送成功");
            }

            @Override
            public void onFailure(int code, String error) {
                pbCode.setVisibility(View.GONE);
                tvCode.setVisibility(View.VISIBLE);
                flCode.setEnabled(true);
                mView.sendCodeError();
                UIUtils.showToast(error);
            }
        });

        addSubscribe(disposable);
    }


}
