package com.sdxxtop.zjlguardian.data;

import com.sdxxtop.zjlguardian.data.course.CourseCellBean;
import com.sdxxtop.zjlguardian.data.course.ExamCellBean;

import java.util.List;

public class StudyCourseBean {

    private List<CourseCellBean> course;
    private List<ExamCellBean> exam;

    public List<CourseCellBean> getCourse() {
        return course;
    }

    public void setCourse(List<CourseCellBean> course) {
        this.course = course;
    }

    public List<ExamCellBean> getExam() {
        return exam;
    }

    public void setExam(List<ExamCellBean> exam) {
        this.exam = exam;
    }
}
