package com.sdxxtop.zjlguardian.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.noober.background.BackgroundLibrary;
import com.sdxxtop.app.App;
import com.sdxxtop.base.BaseMvpActivity;
import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.di.module.ActivityModule;
import com.sdxxtop.zjlguardian.di.DaggerMyActivityComponent;
import com.sdxxtop.zjlguardian.di.MyActivityComponent;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 11:26
 * Version: 1.0
 * Description:
 */
public abstract class GBaseMvpActivity<T extends RxPresenter> extends BaseMvpActivity<T> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject2(this);
        super.onCreate(savedInstanceState);
    }

    protected MyActivityComponent getMyActivityComponent() {
        return DaggerMyActivityComponent
                .builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
