package com.sdxxtop.zjlguardian.ui.learn.course;

import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zjlguardian.data.course.CourseCellBean;
import com.sdxxtop.zjlguardian.data.course.ExamCellBean;

import java.util.List;

public interface CourseListContract {
    interface IView extends BaseView {
        void showList(List<CourseCellBean> studyCourseBeans);
        void showExam(List<ExamCellBean> examCellBeans);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
