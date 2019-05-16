package com.sdxxtop.zjlguardian.ui.home;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.luck.picture.lib.permissions.RxPermissions;
import com.orhanobut.logger.Logger;
import com.sdxxtop.base.BaseActivity;
import com.sdxxtop.model.bean.InitBean;
import com.sdxxtop.ui.dialog.DownloadDialog;
import com.sdxxtop.utils.ReflectUtils;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.base.GBaseActivity;
import com.sdxxtop.zjlguardian.base.GBaseMvpActivity;
import com.sdxxtop.zjlguardian.ui.learn.LearningFragment;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 13:56
 * Version: 1.0
 * Description:
 */
public class HomeTabActivity extends GBaseMvpActivity<HomePresenter> implements HomeContract.IView {


    @BindView(R.id.ahn_home_navigation)
    AHBottomNavigation mAHBottomNavigation;
    private int prePosition;
    private SupportFragment[] mFragments = new SupportFragment[3];
    private boolean isAdmin;
    private RxPermissions mRxPermissions;

    @Override
    protected int getLayout() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initVariables() {
        super.initVariables();
        if (getIntent() != null) {
            isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        }
    }

    @Override
    protected void initView() {
        initAHNavigation();

        switchFragment(0);

        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });

        mRxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    startPatrolService();
                }
            }
        });
    }

    private void startPatrolService() {
        Logger.e("开启了服务");
//        Intent intent = new Intent(this, PatrolRecordService.class);
//        startService(intent);
    }

    private void initAHNavigation() {
        int[] tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu);
        navigationAdapter.setupWithBottomNavigation(mAHBottomNavigation, tabColors);

        // Set background color
        mAHBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        // Disable the translation inside the CoordinatorLayout
        mAHBottomNavigation.setBehaviorTranslationEnabled(false);
        mAHBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Change colors
        mAHBottomNavigation.setAccentColor(Color.parseColor("#34B26D"));
        mAHBottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        mAHBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Logger.i("position = " + position + "wasSelected = " + wasSelected);
//                if (position == 2) {
//                    return false;
//                }
//                itemSelectAnimator(position, wasSelected);
                switchFragment(position);
                return true;
            }
        });
    }

    private void switchFragment(int position) {
//        loadMultipleRootFragment(R.id.fl_home_container, 0, new LearningFragment());
//        HomeFragment fragment = findFragment(HomeFragment.class);
//        if (fragment == null) {
//            mFragments[0] = HomeFragment.newInstance(isAdmin);
//            mFragments[1] = new LearningFragment();
//            mFragments[2] = MineFragment.newInstance(isAdmin);
//
//            loadMultipleRootFragment(R.id.fl_home_container, position,
//                    mFragments[0],
//                    mFragments[1],
//                    mFragments[2]);
//        } else {
//            showHideFragment(mFragments[position], mFragments[prePosition]);
//        }
//        prePosition = position;
    }

    private void itemSelectAnimator(int position, boolean wasSelected) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Logger.i("版本不符合,不进行动画");
            return;
        }

        List<View> views = ReflectUtils.getFieldValueByFieldName("views", mAHBottomNavigation);
        if (views != null) {
            View view = views.get(position);
            ImageView icon = view.findViewById(R.id.bottom_navigation_item_icon);
            final int width = icon.getMeasuredWidth();
            final int height = icon.getMeasuredHeight();
            final float radius = (float) Math.sqrt(width * width + height * height) / 2;//半径
            Animator animator = ViewAnimationUtils.createCircularReveal(icon, width / 2, height / 2, 0, radius);
            animator.setDuration(300);
            animator.start();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.initApp();
    }

    @Override
    public void showInit(InitBean initBean) {
        if (initBean != null) {
            DownloadDialog downloadDialog = new DownloadDialog(this, initBean, new RxPermissions(this));
            downloadDialog.show();
        }
    }
}
