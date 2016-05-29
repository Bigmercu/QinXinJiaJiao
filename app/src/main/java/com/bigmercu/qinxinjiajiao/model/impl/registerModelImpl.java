package com.bigmercu.qinxinjiajiao.model.impl;

import com.bigmercu.qinxinjiajiao.Util.RetrofitClient;
import com.bigmercu.qinxinjiajiao.entity.loginEntity;
import com.bigmercu.qinxinjiajiao.model.net.registerService;
import com.bigmercu.qinxinjiajiao.model.registerModel;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sj.mblog.L;

import static com.bigmercu.qinxinjiajiao.model.impl.identifyModerImpl.StringMultipartBody;

/**
 * Created by bigmercu on 16/4/6.
 */
public class registerModelImpl implements registerModel {
    private registerService service;
    private Subscription subscription;
    public registerModelImpl(){
        service = RetrofitClient.getInstance().create(registerService.class);
    }

    //在访问register_modelImpl时创建单例
    private static class SingleHolder{
         private static  final registerModelImpl INSTANCE = new registerModelImpl();
    }

    //获取单例
    public static registerModelImpl getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    public void Login(Subscriber<loginEntity> subscriber, String identity, String phone, String password) {
//        loginEntity loginEntity = new loginEntity(phone,password,identity);
        Map<String,String> ListMap = new HashMap<String, String>();
        ListMap.put("identity",identity);
        ListMap.put("phone",phone);
        ListMap.put("password",password);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MultipartBody.Builder body = StringMultipartBody(ListMap,builder);
        MultipartBody body1 = body.build();
        subscription = service.uploadInfo(body1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public boolean verifyCode(String code) {
        //在此校验验证码
        return true;
    }

    @Override
    public void cancle() {
        L.d(subscription);
        if(subscription!= null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

}
