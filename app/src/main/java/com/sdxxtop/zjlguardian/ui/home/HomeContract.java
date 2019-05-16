package com.sdxxtop.zjlguardian.ui.home;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.model.bean.InitBean;

public interface HomeContract {
    interface IView extends BaseView {
        void showInit(InitBean initBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
