package com.bigmercu.qinxinjiajiao.model.impl;

import android.util.Log;

import com.bigmercu.qinxinjiajiao.Util.RetrofitClient;
import com.bigmercu.qinxinjiajiao.entity.loginEntity;
import com.bigmercu.qinxinjiajiao.model.LoginModel;
import com.bigmercu.qinxinjiajiao.model.net.LoginService;

import java.util.Map;

import okhttp3.MultipartBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bigmercu.qinxinjiajiao.model.impl.identifyModerImpl.StringMultipartBody;

/**
 * Created by bigmercu on 16/4/23.
 * Email:bigmercu@gmail.com
 */
public class LoginModelImpl implements LoginModel {
    private LoginService loginService;
    private LoginModelImpl(){
        loginService = RetrofitClient.getInstance().create(LoginService.class);
    }


    @Override
    public void Login(Map<String, String> map, final onLoginFinishedListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        MultipartBody.Builder body = StringMultipartBody(map,builder);
        body.setType(MultipartBody.FORM);
        MultipartBody body1 = body.build();
        loginService.Login(body1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<loginEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                        Log.d("error",e.getMessage());
                    }
                    @Override
                    public void onNext(loginEntity loginEntity) {
                        int code = loginEntity.getCode();
                        if(code == 1) {
                            listener.onSuccess();
                        }else {
                            listener.onError(loginEntity.getMes());
                        }
                    }
                });
    }
//吴克石

    private static class SingleHolder{
        private static final LoginModelImpl INSTANCE = new LoginModelImpl();
    }
    public static LoginModelImpl getInstance(){
        return SingleHolder.INSTANCE;
    }
}
