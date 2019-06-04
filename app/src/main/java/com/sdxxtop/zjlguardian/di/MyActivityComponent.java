package com.sdxxtop.zjlguardian.di;

import android.app.Activity;

import com.sdxxtop.zjlguardian.MainActivity;
import com.sdxxtop.di.component.AppComponent;
import com.sdxxtop.di.module.ActivityModule;
import com.sdxxtop.di.qualifier.ActivityScope;
import com.sdxxtop.zjlguardian.ui.examine.ExamineActivity;
import com.sdxxtop.zjlguardian.ui.guardianapp.ContactActivity;
import com.sdxxtop.zjlguardian.ui.guardianapp.EventReportActivity;
import com.sdxxtop.zjlguardian.ui.guardianapp.EventReportDetailActivity;
import com.sdxxtop.zjlguardian.ui.guardianapp.EventReportDetailSecondActivity;
import com.sdxxtop.zjlguardian.ui.guardianapp.EventReportListActivity;
import com.sdxxtop.zjlguardian.ui.guardianapp.GridMapActivity;
import com.sdxxtop.zjlguardian.ui.guardianapp.PatrolRecordActivity;
import com.sdxxtop.zjlguardian.ui.guardianapp.TaskAgentsActivity;
import com.sdxxtop.zjlguardian.ui.home.HomeTabActivity;
import com.sdxxtop.zjlguardian.ui.learn.news.NewsDetailsActivity;
import com.sdxxtop.zjlguardian.ui.login.LoginActivity;
import com.sdxxtop.zjlguardian.ui.login.LoginConfirmActivity;
import com.sdxxtop.zjlguardian.ui.splash.SplashActivity;

import dagger.Component;

/**
 * @Author: zhousaito
 * @Date: 2019-04-29 08:25
 * @Version 1.0
 * @UserWhat what
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface MyActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(HomeTabActivity activity);
    void inject(NewsDetailsActivity activity);
    void inject(ExamineActivity activity);
    void inject(SplashActivity activity);
    void inject(TaskAgentsActivity activity);

    void inject(EventReportDetailSecondActivity activity);
    void inject(EventReportDetailActivity activity);
    void inject(PatrolRecordActivity activity);
    void inject(GridMapActivity activity);
    void inject(ContactActivity activity);
    void inject(EventReportListActivity activity);
    void inject(EventReportActivity activity);
    void inject(LoginConfirmActivity activity);

}
