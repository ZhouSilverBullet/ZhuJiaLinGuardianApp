package com.sdxxtop.di.component;

import android.app.Activity;

import com.sdxxtop.base.BaseHomeActivity;
import com.sdxxtop.di.module.ActivityModule;
import com.sdxxtop.di.qualifier.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(BaseHomeActivity homeActivity);

}
