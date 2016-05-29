package com.bigmercu.qinxinjiajiao.model.net;

import com.bigmercu.qinxinjiajiao.entity.identifyEntity;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bigmercu on 16/4/11.
 */
public interface identifyService {
    //上传包括json数组  多张图片  混合数据
    @POST("Tutor/Reg")
    Observable<identifyEntity> updateIdentifyInfo(@Body MultipartBody part);
}

