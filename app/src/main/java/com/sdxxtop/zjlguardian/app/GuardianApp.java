package com.sdxxtop.zjlguardian.app;

import com.baidu.idl.face.platform.FaceSDKManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.sdxxtop.app.App;
import com.sdxxtop.zjlguardian.BuildConfig;

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

        Toasty.Config.getInstance()
                .allowQueue(false)
                .apply(); // required

        initLogger();
    }

    //初始化logger
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(3)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .logStrategy(new LogcatLogStrategy())  //（可选）更改要打印的日志策略。 默认LogCat
                .tag("sdxxtop-TAG")                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    @Override
    protected void initBaiduFace() {
        FaceSDKManager.getInstance().initialize(this, "zjlguardian-face-android", "idl-license.face-android");

    }
}
