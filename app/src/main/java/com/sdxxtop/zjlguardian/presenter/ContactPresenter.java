package com.sdxxtop.zjlguardian.presenter;


import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.data.ContactIndexBean;
import com.sdxxtop.zjlguardian.presenter.constract.ContactContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ContactPresenter extends GRxPresenter<ContactContract.IView> implements ContactContract.IPresenter {
    @Inject
    public ContactPresenter() {
    }

    public void loadData() {
        Params params = new Params();
        Observable<RequestBean<ContactIndexBean>> observable = getService().postContactIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ContactIndexBean>() {
            @Override
            public void onSuccess(ContactIndexBean contactIndexBean) {
                List<ContactIndexBean.ContactBean> user = contactIndexBean.getUser();
                mView.showList(user);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

    public void searchData(String key) {
        Params params = new Params();
        params.put("sh", key);
        Observable<RequestBean<ContactIndexBean>> observable = getService().postContactSearch(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ContactIndexBean>() {
            @Override
            public void onSuccess(ContactIndexBean contactIndexBean) {
                List<ContactIndexBean.ContactBean> user = contactIndexBean.getUser();
                mView.showSearchList(user);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

}
