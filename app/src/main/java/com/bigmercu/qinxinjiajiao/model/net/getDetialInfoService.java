package com.bigmercu.qinxinjiajiao.model.net;

import com.bigmercu.qinxinjiajiao.entity.DetialInfoEntity;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bigmercu on 16/5/17.
 */
public interface getDetialInfoService {
    @POST("Tutor/sentTutorMes")
    Observable<DetialInfoEntity> getDetialInfo(@Body MultipartBody body);
}
