package com.sdxxtop.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.cy.translucentparent.StatusNavUtils;
import com.sdxxtop.utils.DialogUtil;
import com.sdxxtop.utils.StatusBarUtil;
import com.sdxxtop.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public abstract class BaseActivity extends SwipeBackActivity {

    protected Unbinder mUnbinder;
    protected BaseActivity mContext;
    private DialogUtil mDialogUtil;

    protected boolean isButterBind = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;
        setSwipeBackEnable(false);
        if (isInitStatusBar()) {
//            initStatusBar();
            StatusNavUtils.setStatusBarColor(this,0x00000000);
        }

        if (getButterBind()) {
            mUnbinder = ButterKnife.bind(this);
        }
        initVariables();
        initView();
        initEvent();
        initData();
    }

    public boolean getButterBind() {
        return isButterBind;
    }

//    /**
//     * statusBar 控制
//     */
//    private void initStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isHomeTabActivity()) { //这个全透明
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                window.setStatusBarColor(Color.TRANSPARENT);
//            } else { //这个是半透明
//                window.setFlags(
//                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            }
//
//            if (StatusBarUtil.MIUISetStatusBarLightMode(getWindow(), true)) {//小米MIUI系统
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上系统
//                    StatusBarUtil.android6_SetStatusBarLightMode(getWindow());
//                    StatusBarUtil.compat(this);
//                } else {
//                    StatusBarUtil.compat(this);
//                }
//            } else if (StatusBarUtil.FlymeSetStatusBarLightMode(getWindow(), true)) {//魅族flyme系统
//                StatusBarUtil.compat(this);
//            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上系统
//                StatusBarUtil.android6_SetStatusBarLightMode(getWindow());
//                StatusBarUtil.compat(this);
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }

        if (mDialogUtil != null) {
            mDialogUtil = null;
        }
    }

    public void statusBar(boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上系统
            StatusBarUtil.setDarkStatusIcon(this.getWindow(), isDark);
        }
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        UIUtils.showToast(msg);
    }

    public void showLoadingDialog() {
        if (mDialogUtil == null) {
            mDialogUtil = new DialogUtil();
        }
        mDialogUtil.showLoadingDialog(this);
    }

    public void hideLoadingDialog() {
        if (mDialogUtil != null) {
            mDialogUtil.closeLoadingDialog();
        }
    }

    /**
     * 当全屏的时候，状态栏继续显示
     * 调用该方法
     * https://blog.csdn.net/a872822645/article/details/74482323
     */
    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    protected void initEvent() {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void initVariables() {
    }

    protected boolean isInitStatusBar() {
        return true;
    }

    /**
     * 如果是tabActivity，要求把布局顶上去
     * 需要这么做
     *
     * @return
     */
    protected boolean isHomeTabActivity() {
        return false;
    }

    protected abstract int getLayout();
}
