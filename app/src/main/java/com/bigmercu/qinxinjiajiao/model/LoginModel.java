package com.bigmercu.qinxinjiajiao.model;

import java.util.Map;

/**
 * Created by bigmercu on 16/4/23.
 */
public interface LoginModel {

    interface onLoginFinishedListener{
        void onSuccess();
        void onError(String error);
    }
    void Login(Map<String, String> map,  onLoginFinishedListener listener);

}
