package com.bigmercu.qinxinjiajiao.model.net;

import com.bigmercu.qinxinjiajiao.entity.loginEntity;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bigmercu on 16/4/23.
 */
public interface LoginService {
    @POST("User/Login")
    Observable<loginEntity> Login(@Body MultipartBody multipartBody);
}
