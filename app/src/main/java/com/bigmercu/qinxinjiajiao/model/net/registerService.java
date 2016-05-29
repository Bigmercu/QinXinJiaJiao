package com.bigmercu.qinxinjiajiao.model.net;

import com.bigmercu.qinxinjiajiao.entity.loginEntity;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bigmercu on 16/4/6.
 * 该接口用于Retrofit结合Rxjava传输数据
 */
public interface registerService {
    @POST("User/Reg")
    Observable<loginEntity> uploadInfo(@Body MultipartBody body);
}
