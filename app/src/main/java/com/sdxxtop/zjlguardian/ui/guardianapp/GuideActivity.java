package com.sdxxtop.zjlguardian.ui.guardianapp;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.app.Constants;
import com.sdxxtop.utils.SpUtil;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.ui.login.LoginActivity;
import com.sdxxtop.zjlguardian.ui.splash.SplashActivity;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class GuideActivity extends SplashActivity implements ViewPager.OnPageChangeListener {
    private ArrayList<ImageView> cacheView;
    private int[] imgs = {R.drawable.guide_one,
            R.drawable.guide_two,
            R.drawable.guide_three
    };

    @BindView(R.id.guide_pager)
    ViewPager viewPager;
    @BindView(R.id.guide_btn)
    Button btn;
    @BindView(R.id.guide_skip)
    ImageView skipImg;
    private RxPermissions mRxPermissions;
    private long totalTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        SpUtil.putBoolean(Constants.GUIDE_IS_SHOW, true);
        SpUtil.putInt(Constants.GUIDE_SHOW_VERSION, getVersionCode());

        String[] perm = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(perm).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });
//        if (!EasyPermissions.hasPermissions(this, perm)) {
//            EasyPermissions.requestPermissions(this, "请允许开启手机定位权限", REQUEST_PERMISSION_LOACTION, perm);
//        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new GuidePagerAdapter());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        viewPager.addOnPageChangeListener(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToActivity();
            }
        });

        skipImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToActivity();
            }
        });
    }

    private void skipToActivity() {
        totalTime = System.currentTimeMillis();
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == imgs.length - 1) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class GuidePagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image;
            if (cacheView != null && cacheView.size() > 0) {
                image = cacheView.get(0);
                cacheView.remove(0);
            } else {
                image = new ImageView(getBaseContext());
                image.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            Glide.with(mContext).load(imgs[position]).into(image);
//            image.setImageResource(imgs[position]);
            container.addView(image, 0);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (cacheView == null) {
                cacheView = new ArrayList<>();
            }
            cacheView.add((ImageView) object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cacheView != null) {
            cacheView.clear();
        }
    }
}
