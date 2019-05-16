package com.sdxxtop.zjlguardian.app;

import com.sdxxtop.app.App;

import es.dmoral.toasty.Toasty;

/**
 * @Author: zhousaito
 * @Date: 2019-04-29 08:43
 * @Version 1.0
 * @UserWhat what
 */
public class GuardianApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();

        Toasty.Config.getInstance().apply(); // required
    }
}
