package com.sdxxtop.zjlguardian.presenter.constract;


import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zjlguardian.presenter.bean.EventReadBean;

public interface EventReportDetailContract {
    interface IView extends BaseView {
        void readData(EventReadBean eventReadBean);

        void modifyRefresh();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
