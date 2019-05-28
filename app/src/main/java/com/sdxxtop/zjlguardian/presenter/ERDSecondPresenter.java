package com.sdxxtop.zjlguardian.presenter;

import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.ImageParams;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.presenter.constract.ERDSecondContract;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ERDSecondPresenter extends GRxPresenter<ERDSecondContract.IView> implements ERDSecondContract.IPresenter {
    @Inject
    public ERDSecondPresenter() {
    }

    public void modify(String eventId, int status, String extra, List<File> imagePushPath) {
        ImageParams params = new ImageParams();
        params.put("ei", eventId);
        params.put("st", status);
        params.put("et", extra);

        params.addImagePathList("img[]", imagePushPath);
        Observable<RequestBean> observable = getService().postEventModify(params.getImgData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                mView.modifyRefresh();
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
