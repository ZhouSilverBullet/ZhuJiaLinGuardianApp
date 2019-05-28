package com.sdxxtop.zjlguardian.presenter.constract;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;

/**
 * 用来copy使用的
 */
public interface FaceLivenessContract {
    interface IView extends BaseView {
        void faceSuccess(String address);
        void faceRegisterSuccess(String address);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
