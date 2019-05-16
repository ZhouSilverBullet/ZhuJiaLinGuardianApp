package com.sdxxtop.zjlguardian.http;

import com.sdxxtop.model.bean.InitBean;
import com.sdxxtop.model.bean.RequestBean;
import com.sdxxtop.zjlguardian.data.AutoLoginBean;
import com.sdxxtop.zjlguardian.data.ExamineFinishBean;
import com.sdxxtop.zjlguardian.data.LearnNewsBean;
import com.sdxxtop.zjlguardian.data.LoginBean;
import com.sdxxtop.zjlguardian.data.StudyCourseBean;
import com.sdxxtop.zjlguardian.data.StudyQuestionBean;
import com.sdxxtop.zjlguardian.data.UcenterIndexBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-16 10:47
 * Version: 1.0
 * Description:
 */
public interface GuardianService {
    String BASE_URL = "http://envir.sdxxtop.com/api/";

    @FormUrlEncoded
    @POST("app/init")
    Observable<RequestBean<InitBean>> postAppInit(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/sendCode")
    Observable<RequestBean> postLoginSendCode(@Field("data") String data);

    @FormUrlEncoded
    @POST("login/mobileLogin")
    Observable<RequestBean<LoginBean>> postLoginMobileLogin(@Field("data") String data);


    @FormUrlEncoded
    @POST("login/autoLogin")
    Observable<RequestBean<AutoLoginBean>> postLoginAutoLogin(@Field("data") String data);
//
//    ////////////// 首页 ////////////
//    @FormUrlEncoded
//    @POST("main/index")
//    Observable<RequestBean<MainIndexBean>> postMainIndex(@Field("data") String data);

    @FormUrlEncoded
    @POST("main/sign")
    Observable<RequestBean> postMainSign(@Field("data") String data);

//    @FormUrlEncoded
//    @POST("main/signlog")
//    Observable<RequestBean<SignLogBean>> postMainSignLog(@Field("data") String data);
//
//    @FormUrlEncoded
//    @POST("main/map")
//    Observable<RequestBean<MainMapBean>> postMainMap(@Field("data") String data);

    //////////////首页 ////////////


//    @FormUrlEncoded
//    @POST("event/index")
//    Observable<RequestBean<EventIndexBean>> postEventIndex(@Field("data") String data);

    @Multipart
    @POST("event/add")
    Observable<RequestBean> postEventAdd(@PartMap Map<String, RequestBody> data);

//    @FormUrlEncoded
//    @POST("event/read")
//    Observable<RequestBean<EventReadBean>> postEventRead(@Field("data") String data);

    @Multipart
    @POST("event/modify")
    Observable<RequestBean> postEventModify(@PartMap HashMap<String, RequestBody> data);
//
//    @FormUrlEncoded
//    @POST("event/showPart")
//    Observable<RequestBean<ShowPartBean>> postEventShowPart(@Field("data") String data);
//
//    @FormUrlEncoded
//    @POST("contact/index")
//    Observable<RequestBean<ContactIndexBean>> postContactIndex(@Field("data") String data);
//
//    @FormUrlEncoded
//    @POST("contact/search")
//    Observable<RequestBean<ContactIndexBean>> postContactSearch(@Field("data") String data);
//
//
    @FormUrlEncoded
    @POST("ucenter/index")
    Observable<RequestBean<UcenterIndexBean>> postUcenterIndex(@Field("data") String data);

    @Multipart
    @POST("ucenter/modImg")
    Observable<RequestBean> postUcenterModImg(@PartMap Map<String, RequestBody> data);

    /**
     * exam
     * course
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("study/{name}")
    Observable<RequestBean<StudyCourseBean>> postStudyCourse(@Path("name") String name, @Field("data") String data);

    /**
     * exam
     * course
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("study/question")
    Observable<RequestBean<StudyQuestionBean>> postStudyQuestion(@Field("data") String data);

    @FormUrlEncoded
    @POST("study/check")
    Observable<RequestBean> postStudyCheck(@Field("data") String data);

    @FormUrlEncoded
    @POST("study/finish")
    Observable<RequestBean<ExamineFinishBean>> postStudyFinish(@Field("data") String data);


    @Multipart
    @POST("face/reg")
    Observable<RequestBean> postFaceReg(@PartMap Map<String, RequestBody> data);

    @Multipart
    @POST("face/verify")
    Observable<RequestBean> postFaceVerify(@PartMap Map<String, RequestBody> data);


    @FormUrlEncoded
    @POST("article/article_info")
    Observable<RequestBean> getAllArticleInfo(@Field("data") String data);

    @FormUrlEncoded
    @POST("article/allarticle")
    Observable<RequestBean<List<LearnNewsBean>>> getAllArticle(@Field("data") String data);

}
