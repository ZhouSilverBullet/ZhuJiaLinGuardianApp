package com.sdxxtop.di.module;

import com.sdxxtop.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public App getApp() {
        return app;
    }
}
