package com.sdxxtop.zjlguardian.ui.learn.news;

import com.orhanobut.logger.Logger;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.http.net.RetrofitHelper;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author :  lwb
 * Date: 2019/3/18
 * Desc:
 */
public class NewsDetailPresenter extends GRxPresenter<NewDetailContract.IView> implements NewDetailContract.IPresenter {
    @Inject
    public NewsDetailPresenter() {
    }

    public void loadData(int article_id) {
        Params params = new Params();
        params.put("ai", article_id);
        Disposable subscribe = RetrofitHelper.getGuardianService().getAllArticleInfo(params.getData())
                .compose(RxUtils.schedulers())
//                .compose(RxUtils.handleResult())
                .subscribe(new Consumer<RequestBean>() {
                    @Override
                    public void accept(RequestBean requestBean) throws Exception {
                        if (requestBean != null) {
                            if (requestBean.getCode()==200){
                                UIUtils.showToast(requestBean.getMsg());
                            }else{
                                UIUtils.showToast(requestBean.getMsg());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e( "--- " + throwable.toString());
                    }
                });
        addSubscribe(subscribe);
    }
}
