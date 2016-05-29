package com.bigmercu.qinxinjiajiao.model.net;

import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bigmercu on 16/4/20.
 */
public interface homeService {
    //首页面service
    @POST("Tutor/sentMes")
    Observable<MainInfoHome> getHomeData(@Body MultipartBody multipartBody);
}
