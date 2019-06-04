package com.sdxxtop.zjlguardian.presenter.constract;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-06-04 15:49
 * Version: 1.0
 * Description:
 */
public interface LoginConfirmContract {
    interface IView extends BaseView {
        void confirmSuccess();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
