package com.sdxxtop.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sdxxtop.presenter.CopyPresenter;
import com.sdxxtop.presenter.contract.CopyContract;

/**
 * @Author: zhousaito
 * @Date: 2019-04-28 16:54
 * @Version 1.0
 * @UserWhat what
 */
public class BaseHomeActivity extends BaseMvpActivity<CopyPresenter> implements CopyContract.IView {

    @Override
    protected int getLayout() {
        return R.layout.activity_base;
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void showError(String error) {

    }
}