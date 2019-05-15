package com.sdxxtop.di.component;

import com.sdxxtop.app.App;
import com.sdxxtop.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    App getAppContext();
}
