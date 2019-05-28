package com.sdxxtop.zjlguardian.presenter.constract;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zjlguardian.data.SignLogBean;

public interface PatrolContract {
    interface IView extends BaseView {
        void showData(SignLogBean signLogBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
