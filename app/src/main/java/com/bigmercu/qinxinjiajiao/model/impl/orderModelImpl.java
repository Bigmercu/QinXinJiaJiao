package com.bigmercu.qinxinjiajiao.model.impl;

import com.bigmercu.qinxinjiajiao.Util.RetrofitClient;
import com.bigmercu.qinxinjiajiao.contract.bigmercuContract;
import com.bigmercu.qinxinjiajiao.entity.messageEntity;
import com.bigmercu.qinxinjiajiao.model.net.orderService;
import com.bigmercu.qinxinjiajiao.model.orderModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.bigmercu.qinxinjiajiao.model.impl.identifyModerImpl.StringMultipartBody;

/**
 * Created by bigmercu on 16/5/30.
 * Email:bigmercu@gmail.com
 */

public class orderModelImpl implements orderModel {
    private orderService service;
    public void orderModelImpl(){
        service = RetrofitClient.getInstance().create(orderService.class);
    }

    @Override
    public void order(HashMap map, final bigmercuContract.orderListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        MultipartBody.Builder body = StringMultipartBody(map,builder);
        body.setType(MultipartBody.FORM);
        MultipartBody body1 = body.build();
        service.order(body1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<messageEntity>() {
                    @Override
                    public void call(messageEntity messageEntity) {
                        if(messageEntity.getCode() == 1){
                            listener.onSuccess();
                        }else {
                            listener.onFiled(messageEntity.getMes());
                        }
                    }
                });
    }
}
