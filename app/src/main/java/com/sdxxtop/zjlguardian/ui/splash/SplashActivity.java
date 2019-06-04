package com.sdxxtop.zjlguardian.ui.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.sdxxtop.app.Constants;
import com.sdxxtop.utils.SpUtil;
import com.sdxxtop.zjlguardian.BuildConfig;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.base.GBaseMvpActivity;
import com.sdxxtop.zjlguardian.data.AutoLoginBean;
import com.sdxxtop.zjlguardian.ui.guardianapp.GuideActivity;
import com.sdxxtop.zjlguardian.ui.home.HomeActivity;
import com.sdxxtop.zjlguardian.ui.home.HomeTabActivity;
import com.sdxxtop.zjlguardian.ui.login.LoginActivity;

import me.jessyan.autosize.internal.CancelAdapt;


public class SplashActivity extends GBaseMvpActivity<SplashPresenter> implements SplashContract.IView, CancelAdapt {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            // 仅当缺口区域完全包含在状态栏之中时，才允许窗口延伸到刘海区域显示
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            // 永远不允许窗口延伸到刘海区域
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            // 始终允许窗口延伸到屏幕短边上的刘海区域
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
    }

    @Override
    protected boolean isInitStatusBar() {
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        super.initData();

        boolean guideIsShow = SpUtil.getBoolean(Constants.GUIDE_IS_SHOW, false);
        if (guideIsShow) {
//            mPresenter.autoLogin();
            skipToActivity();
        } else {
            Intent intent = new Intent(mContext, GuideActivity.class);
            startActivity(intent);
            finish();
        }
//        Intent intent = new Intent(mContext, HomeTabActivity.class);
//        startActivity(intent);
//        finish();
    }

    private void skipToActivity() {
        int timeTemp = SpUtil.getInt(Constants.EXPIRE_TIME, 0);
        if (timeTemp != 0 && timeTemp < System.currentTimeMillis()) {
            //测试用暂关此功能
//            postAutoLogin();
            mPresenter.autoLogin();
        } else { //没有自动登陆去登陆界面
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void autoSuccess(AutoLoginBean autoLoginBean) {

        String autoToken = SpUtil.getString(Constants.AUTO_TOKEN);
        if (TextUtils.isEmpty(autoToken)) {
            skipLogin();
            return;
        }

        int expireTime = SpUtil.getInt(Constants.EXPIRE_TIME, 0);
        if (expireTime == 0) {
            skipLogin();
            return;
        }

        //token时间失效 php发过来的时间戳需要 除1000
        if (expireTime - (System.currentTimeMillis() / 1000) < 0) {
            skipLogin();
            return;
        }


        //再进行缓存一遍
        String auto_token = autoLoginBean.getAuto_token();
        int expire_time = autoLoginBean.getExpire_time();
        String name = autoLoginBean.getName();
        int part_id = autoLoginBean.getPart_id();
        int userid = autoLoginBean.getUserid();
        final int type = autoLoginBean.getType();

        SpUtil.putInt(Constants.USER_ID, userid);
        SpUtil.putInt(Constants.EXPIRE_TIME, expire_time);
        SpUtil.putInt(Constants.PART_ID, part_id);
        SpUtil.putString(Constants.AUTO_TOKEN, auto_token);
        SpUtil.putInt(Constants.TYPE, type);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, HomeTabActivity.class);
                intent.putExtra("isAdmin", type == 1);
                startActivity(intent);
                finish();
            }
        }, BuildConfig.DEBUG ? 0 : 2000);

    }

    private void skipLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, BuildConfig.DEBUG ? 0 : 2000);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }

    @Override
    protected void onStop() {
//        finish();
        super.onStop();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    @Override
    public void showError(String error) {
        showToast(error);
        skipLogin();
    }
}
