package com.sdxxtop.zjlguardian.ui.examine;


import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zjlguardian.data.ExamineFinishBean;
import com.sdxxtop.zjlguardian.data.StudyCheckBean;
import com.sdxxtop.zjlguardian.data.StudyQuestionBean;

public interface ExamineContract {
    interface IView extends BaseView {
        void showData(StudyQuestionBean studyQuestionBean);

        void pushQuestionSuccess(StudyCheckBean studyCheckBean);

        void finishSuccess(ExamineFinishBean finishBean);

        void finishFailure();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
