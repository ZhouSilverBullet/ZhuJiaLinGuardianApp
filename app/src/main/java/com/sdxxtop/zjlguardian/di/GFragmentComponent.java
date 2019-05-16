package com.sdxxtop.zjlguardian.di;

import android.app.Activity;

import com.sdxxtop.di.component.AppComponent;
import com.sdxxtop.di.module.FragmentModule;
import com.sdxxtop.di.qualifier.FragmentScope;
import com.sdxxtop.zjlguardian.ui.learn.course.CourseListFragment;
import com.sdxxtop.zjlguardian.ui.learn.news.NewsListFragment;

import dagger.Component;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 14:29
 * Version: 1.0
 * Description:
 */
@FragmentScope
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface GFragmentComponent {
    Activity getActivity();

    void inject(NewsListFragment newsListFragment);

    void inject(CourseListFragment fragment);

//    void inject(MineFragment fragment);
}
