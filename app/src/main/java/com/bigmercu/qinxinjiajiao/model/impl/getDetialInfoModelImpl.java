package com.bigmercu.qinxinjiajiao.model.impl;

import android.os.Handler;

import com.bigmercu.qinxinjiajiao.Util.RetrofitClient;
import com.bigmercu.qinxinjiajiao.entity.DetialInfoEntity;
import com.bigmercu.qinxinjiajiao.model.bigmercuModel;
import com.bigmercu.qinxinjiajiao.model.net.getDetialInfoService;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sj.mblog.L;

import static com.bigmercu.qinxinjiajiao.model.impl.identifyModerImpl.StringMultipartBody;

/**
 * Created by bigmercu on 16/5/17.
 */
public class getDetialInfoModelImpl implements bigmercuModel {
    private getDetialInfoService getDetialInfoService;

    public getDetialInfoModelImpl(){
        getDetialInfoService = RetrofitClient.getInstance().create(getDetialInfoService.class);
    }

    @Override
    public void getDetialInfo(String id, final OnGetDetialInfoLinstener listener) {
        Map<String,String> map = new HashMap<>();
        map.put("phone",id);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        MultipartBody.Builder body = StringMultipartBody(map,builder);
        body.setType(MultipartBody.FORM);
        MultipartBody body1 = body.build();

        getDetialInfoService.getDetialInfo(body1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DetialInfoEntity>() {
                    @Override
                    public void call(final DetialInfoEntity getDetialInfoEntity) {
                        L.d(getDetialInfoEntity.getMes());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listener.OnSuccess(getDetialInfoEntity);
                            }
                        },0);
                    }
                });
//        getDetialInfoService.
    }
    public static class SingleHolder{
        private static final getDetialInfoModelImpl INSTANCE = new getDetialInfoModelImpl();
    }

    public static getDetialInfoModelImpl getInstance(){
        return SingleHolder.INSTANCE;
    }
}
