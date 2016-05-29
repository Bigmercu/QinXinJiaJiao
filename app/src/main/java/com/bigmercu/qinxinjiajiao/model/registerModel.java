package com.bigmercu.qinxinjiajiao.model;

import com.bigmercu.qinxinjiajiao.entity.loginEntity;

import rx.Subscriber;

/**
 * Created by bigmercu on 16/4/6.
 */
public interface registerModel {
    interface onLoginFinsh
    {
        void loginError();
    }
    void Login(Subscriber<loginEntity> subscriber, String identity, String phone, String password);
    boolean verifyCode(String code);
    void cancle();
}
