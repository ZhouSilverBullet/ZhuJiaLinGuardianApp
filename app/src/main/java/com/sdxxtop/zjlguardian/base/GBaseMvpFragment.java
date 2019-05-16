package com.sdxxtop.zjlguardian.base;

import com.sdxxtop.app.App;
import com.sdxxtop.base.BaseMvpFragment;
import com.sdxxtop.base.RxPresenter;
import com.sdxxtop.di.module.FragmentModule;
import com.sdxxtop.zjlguardian.di.DaggerGFragmentComponent;
import com.sdxxtop.zjlguardian.di.GFragmentComponent;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 14:25
 * Version: 1.0
 * Description:
 */
public abstract class GBaseMvpFragment<T extends RxPresenter> extends BaseMvpFragment<T> {

    protected GFragmentComponent getGFragmentComponent() {
        return DaggerGFragmentComponent
                .builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }
}
