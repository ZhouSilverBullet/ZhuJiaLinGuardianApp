package com.sdxxtop.zjlguardian.presenter;

import com.google.gson.internal.LinkedTreeMap;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.ImageParams;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.data.EventSearchTitleBean;
import com.sdxxtop.zjlguardian.data.ShowPartBean;
import com.sdxxtop.zjlguardian.presenter.constract.EventReportContract;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class EventReportPresenter extends GRxPresenter<EventReportContract.IView> implements EventReportContract.IPresenter {
    @Inject
    public EventReportPresenter() {
    }


    public void pushReport(String title, int pathType, int patrolType,
                           String place, String longitude, String content, List<File> imagePushPath) {
        ImageParams imageParams = new ImageParams();
        imageParams.put("tl", title);
        imageParams.put("pt", pathType);
        imageParams.put("plt", patrolType);
        imageParams.put("pl", place);
        imageParams.put("lt", longitude);
        imageParams.put("ct", content);

        imageParams.addImagePathList("img[]", imagePushPath);

        Observable<RequestBean> observable = getService().postEventAdd(imageParams.getImgData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                String eventId = "";
                Object data = requestBean.getData();
                if (data != null) {
                    eventId = (String) ((LinkedTreeMap) data).get("event_id");
                }
                mView.pushSuccess(eventId);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }

    public void loadAera() {
        Params params = new Params();
        Observable<RequestBean<ShowPartBean>> observable = getService().postEventShowPart2(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ShowPartBean>() {
            @Override
            public void onSuccess(ShowPartBean showPartBean) {
//                mView.modifyRefresh();
                List<ShowPartBean.PartBean> part = showPartBean.getPart();
                if (part != null) {
                    mView.showPart(part);
                }
            }

            @Override
            public void onFailure(int code, String error) {
//                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void searchTitle(String title) {
        Params params = new Params();
        params.put("kwd",title);
        Observable<RequestBean<EventSearchTitleBean>> observable = getService().postEventSearch(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventSearchTitleBean>() {
            @Override
            public void onSuccess(EventSearchTitleBean Bean) {
                mView.showSearchData(Bean);
            }

            @Override
            public void onFailure(int code, String error) {
//                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
