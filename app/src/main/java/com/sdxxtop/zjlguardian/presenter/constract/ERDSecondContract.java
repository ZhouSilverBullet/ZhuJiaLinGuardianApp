package com.sdxxtop.zjlguardian.presenter.constract;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;

/**
 * EventReportDetailSecondContract
 */
public interface ERDSecondContract {
    interface IView extends BaseView {
        void modifyRefresh();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
