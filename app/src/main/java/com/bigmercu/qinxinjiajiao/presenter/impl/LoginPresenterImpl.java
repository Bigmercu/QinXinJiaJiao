package com.bigmercu.qinxinjiajiao.presenter.impl;

import com.bigmercu.qinxinjiajiao.contract.loginContract;
import com.bigmercu.qinxinjiajiao.model.LoginModel;
import com.bigmercu.qinxinjiajiao.model.impl.LoginModelImpl;

import java.util.Map;

/**
 * Created by bigmercu on 16/4/23.
 */
public class LoginPresenterImpl implements loginContract.Presenter,LoginModel.onLoginFinishedListener{
    loginContract.View loginView;
    LoginModelImpl loginModel;

    private String phone;

    public LoginPresenterImpl(loginContract.View loginView) {
        this.loginView = loginView;
        loginModel = LoginModelImpl.getInstance();
        loginView.setPresenter(this);
    }


    @Override
    public void Login(Map<String,String> map) {
        loginView.showAndHide(true);
        loginModel.Login(map,this);
        phone = map.get("phone");
    }


    @Override
    public void onSuccess() {
        loginView.Onsuccess();
    }

    @Override
    public void onError(String error) {
        loginView.showAndHide(false);
        loginView.OnFiled(error);
    }

    @Override
    public void start() {
        //testNetWork
    }
}
