package com.sdxxtop.presenter;

import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.presenter.contract.CopyContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class CopyPresenter extends RxPresenter<CopyContract.IView> implements CopyContract.IPresenter {
    @Inject
    public CopyPresenter() {
    }


}
