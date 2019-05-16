package com.sdxxtop.zjlguardian.ui.mine;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zjlguardian.data.UcenterIndexBean;

/**
 * 用来copy使用的
 */
public interface MineContract {
    interface IView extends BaseView {
        void showList(UcenterIndexBean ucenterIndexBean);

        void changeIconSuccess();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
