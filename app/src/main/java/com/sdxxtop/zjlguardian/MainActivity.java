package com.sdxxtop.zjlguardian;


import com.sdxxtop.app.App;
import com.sdxxtop.zjlguardian.di.DaggerMyActivityComponent;
import com.sdxxtop.zjlguardian.di.MyActivityComponent;
import com.sdxxtop.zjlguardian.presenter.CopyPresenter;
import com.sdxxtop.zjlguardian.presenter.constract.CopyContract;
import com.sdxxtop.base.BaseMvpActivity;

import com.sdxxtop.di.module.ActivityModule;

public class MainActivity extends BaseMvpActivity<CopyPresenter> implements CopyContract.IView {

    @Override
    protected int getLayout() {
        return R.layout.activity_base_home;
    }

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    protected MyActivityComponent getMyActivityComponent() {
        return DaggerMyActivityComponent
                .builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData();
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }
}
