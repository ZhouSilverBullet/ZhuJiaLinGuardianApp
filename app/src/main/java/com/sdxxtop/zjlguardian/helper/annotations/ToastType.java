package com.sdxxtop.zjlguardian.helper.annotations;

@IntDef({ERROR,NORMAL,SUCCESS,WARNING})
public @interface ToastType {
    int ERROR=-2;
    int WARNING=-1;
    int NORMAL=0;
    int SUCCESS=1;
}