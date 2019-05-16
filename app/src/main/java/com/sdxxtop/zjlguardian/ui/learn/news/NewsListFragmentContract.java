package com.sdxxtop.zjlguardian.ui.learn.news;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zjlguardian.data.LearnNewsBean;

import java.util.List;

public interface NewsListFragmentContract {
    interface IView extends BaseView {
        void showData(List<LearnNewsBean> data);
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData(int count, int type);
    }
}
