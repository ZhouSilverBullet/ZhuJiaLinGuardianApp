package com.sdxxtop.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sdxxtop.app.App;
import com.sdxxtop.di.component.DaggerFragmentComponent;
import com.sdxxtop.di.component.FragmentComponent;
import com.sdxxtop.di.module.FragmentModule;

import javax.inject.Inject;

public abstract class BaseMvpFragment<T extends RxPresenter> extends BaseFragment implements BaseView {
    @Inject
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    protected abstract void initInject();

    public FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }
}
