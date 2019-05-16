package com.sdxxtop.zjlguardian.ui.learn.news;

import com.orhanobut.logger.Logger;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.net.RetrofitHelper;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.data.LearnNewsBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NewsListFragmentPresenter extends GRxPresenter<NewsListFragmentContract.IView> implements NewsListFragmentContract.IPresenter {
    @Inject
    public NewsListFragmentPresenter() {
    }

    @Override
    public void loadData(int count,int type) {
        Params params = new Params();
        params.put("an", count);
        params.put("te", type);
        Disposable subscribe = getService().getAllArticle(params.getData())
                .compose(RxUtils.schedulers())
//                .compose(RxUtils.handleResult())
                .subscribe(new Consumer<RequestBean>() {
                    @Override
                    public void accept(RequestBean requestBean) throws Exception {
                        if (requestBean != null) {
                            if (requestBean.getCode()==200){
                                mView.showData((List<LearnNewsBean>) requestBean.getData());
                            }else{
                                mView.showError("");
                                UIUtils.showToast(requestBean.getMsg());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError("");
                        Logger.e( "--- " + throwable.toString());
                    }
                });
        addSubscribe(subscribe);
    }
}
