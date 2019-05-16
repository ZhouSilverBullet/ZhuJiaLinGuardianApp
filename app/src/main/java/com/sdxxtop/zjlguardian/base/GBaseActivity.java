package com.sdxxtop.zjlguardian.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.noober.background.BackgroundLibrary;
import com.sdxxtop.base.BaseActivity;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 13:03
 * Version: 1.0
 * Description:
 */
public abstract class GBaseActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject2(this);
        super.onCreate(savedInstanceState);
    }
}
