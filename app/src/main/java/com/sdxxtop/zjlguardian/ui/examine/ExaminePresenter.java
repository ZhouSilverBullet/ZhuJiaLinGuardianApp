package com.sdxxtop.zjlguardian.ui.examine;


import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.model.http.callback.IRequestCallback;
import com.sdxxtop.model.http.net.Params;
import com.sdxxtop.model.http.util.RxUtils;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.base.GRxPresenter;
import com.sdxxtop.zjlguardian.data.ExamineFinishBean;
import com.sdxxtop.zjlguardian.data.StudyQuestionBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ExaminePresenter extends GRxPresenter<ExamineContract.IView> implements ExamineContract.IPresenter {
    @Inject
    public ExaminePresenter() {
    }

    public void loadData(int examId, int number, String attendId) {
        Params params = new Params();
        params.put("ei", examId);
        params.put("nm", number);
        params.put("ai", attendId);
        Observable<RequestBean<StudyQuestionBean>> observable = getService().postStudyQuestion(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<StudyQuestionBean>() {
            @Override
            public void onSuccess(StudyQuestionBean studyQuestionBean) {
                mView.showData(studyQuestionBean);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });

        addSubscribe(disposable);
    }

    public void pushSelectQuestion(int examId, int questionId, String answer, int number, String attendId) {

        Params params = new Params();
        params.put("ei", examId);
        params.put("qi", questionId);
        params.put("nm", number);
        params.put("an", answer);
        params.put("ai", attendId);

        Observable<RequestBean> observable = getService().postStudyCheck(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean checkBean) {
                mView.pushQuestionSuccess(null);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });

        addSubscribe(disposable);
    }

    public void finishData(int examId, String attendId) {
        Params params = new Params();
        params.put("ei", examId);
        params.put("ai", attendId);
        Observable<RequestBean<ExamineFinishBean>> observable = getService().postStudyFinish(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ExamineFinishBean>() {
            @Override
            public void onSuccess(ExamineFinishBean finishBean) {
                mView.finishSuccess(finishBean);
            }

            @Override
            public void onFailure(int code, String error) {
                mView.finishFailure();
                UIUtils.showToast(error);
            }
        });

        addSubscribe(disposable);
    }
}
