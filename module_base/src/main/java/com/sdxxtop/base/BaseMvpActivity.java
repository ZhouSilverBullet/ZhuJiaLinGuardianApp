package com.sdxxtop.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sdxxtop.app.App;
import com.sdxxtop.di.component.ActivityComponent;
import com.sdxxtop.di.component.DaggerActivityComponent;
import com.sdxxtop.di.module.ActivityModule;

import javax.inject.Inject;

public abstract class BaseMvpActivity<T extends RxPresenter> extends BaseActivity implements BaseView {
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent
                .builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void initInject();
}
