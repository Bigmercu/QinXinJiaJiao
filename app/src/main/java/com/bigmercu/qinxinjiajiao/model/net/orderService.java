package com.bigmercu.qinxinjiajiao.model.net;

import com.bigmercu.qinxinjiajiao.entity.messageEntity;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bigmercu on 16/5/30.
 * Email:bigmercu@gmail.com
 */

public interface orderService {
    @POST("YuYue/index")
    Observable<messageEntity> order(@Body MultipartBody multipartBody);
}
