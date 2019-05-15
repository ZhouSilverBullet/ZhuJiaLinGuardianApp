package com.sdxxtop.di.component;

import android.app.Activity;

import com.sdxxtop.di.module.FragmentModule;
import com.sdxxtop.di.qualifier.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface FragmentComponent {
    Activity getActivity();

//    void inject(HomeFragment homeFragment);
//
//    void inject(NewsListFragment newsListFragment);
//    void inject(CourseListFragment fragment);
//    void inject(MineFragment fragment);
}
