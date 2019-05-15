package com.sdxxtop.base;

public interface BasePresenter<T> {
    void attachView(T t);

    void detachView();
}
