package com.sdxxtop.zjlguardian.ui.mine;

import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.ImageParams;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.data.UcenterIndexBean;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class MinePresenter extends GRxPresenter<MineContract.IView> implements MineContract.IPresenter {
    @Inject
    public MinePresenter() {
    }


    public void loadData() {
        Params params = new Params();
        Observable<RequestBean<UcenterIndexBean>> observable = getService().postUcenterIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<UcenterIndexBean>() {
            @Override
            public void onSuccess(UcenterIndexBean ucenterIndexBean) {
                mView.showList(ucenterIndexBean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

    public void changeIcon(String path) {
        ImageParams params = new ImageParams();
        params.addImagePath("img", new File(path));
        Observable<RequestBean> observable = getService().postUcenterModImg(params.getImgData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean ucenterIndexBean) {
                mView.changeIconSuccess();
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
