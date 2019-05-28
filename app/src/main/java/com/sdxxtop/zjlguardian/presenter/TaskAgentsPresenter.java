package com.sdxxtop.zjlguardian.presenter;


import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.presenter.bean.EventIndexBean;
import com.sdxxtop.zjlguardian.presenter.constract.TaskAgentsContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class TaskAgentsPresenter extends GRxPresenter<TaskAgentsContract.IView> implements TaskAgentsContract.IPresenter {
    @Inject
    public TaskAgentsPresenter() {
    }


    @Override
    public void loadData(int page, int type /* 1.事件上报，2.我的事件*/) {
        Params params = new Params();
        params.put("et", type);
        Observable<RequestBean<EventIndexBean>> observable = getService().postEventIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventIndexBean>() {
            @Override
            public void onSuccess(EventIndexBean eventIndexBean) {
                mView.showData(page, eventIndexBean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
