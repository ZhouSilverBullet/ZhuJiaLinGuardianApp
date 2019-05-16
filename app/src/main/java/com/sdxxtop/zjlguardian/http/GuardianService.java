package com.sdxxtop.zjlguardian.http;

import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.zjlguardian.data.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 10:47
 * Version: 1.0
 * Description:
 */
public interface GuardianService {
    String BASE_URL = "http://envir.sdxxtop.com/api/";


    @FormUrlEncoded
    @POST("login/sendCode")
    Observable<RequestBean> postLoginSendCode(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/mobileLogin")
    Observable<RequestBean<LoginBean>> postLoginMobileLogin(@Field("data") String data);

}
