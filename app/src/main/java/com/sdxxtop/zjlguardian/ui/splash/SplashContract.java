package com.sdxxtop.zjlguardian.ui.splash;


import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zjlguardian.data.AutoLoginBean;

public interface SplashContract {
    interface IView extends BaseView {
        void autoSuccess(AutoLoginBean autoLoginBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
