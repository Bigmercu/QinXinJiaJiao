package com.bigmercu.qinxinjiajiao.presenter.impl;

import com.bigmercu.qinxinjiajiao.contract.registerContract;
import com.bigmercu.qinxinjiajiao.entity.loginEntity;
import com.bigmercu.qinxinjiajiao.model.impl.registerModelImpl;

import rx.Subscriber;
import sj.mblog.L;

/**
 * Created by bigmercu on 16/4/6.
 */
public class RegisterPresenterImpl implements registerContract.Presenter{

    private registerModelImpl register_modelLmplb;
    private registerContract.View registerView;
    private Subscriber subscriber;

    public RegisterPresenterImpl(registerContract.View register_view) {
        this.registerView = register_view;
        this.register_modelLmplb = new registerModelImpl();
        registerView.setPresenter(this);
    }

    @Override
    public void uploadInfo(String identity, String phone, String password) {
        registerModelImpl.getInstance().Login(new Subscriber<loginEntity>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                registerView.hideDialog(e.getMessage());
            }

            @Override
            public void onNext(loginEntity loginEntity) {
                L.d(loginEntity.getCode());
                if(loginEntity.getCode() == 1){
                    registerView.startActivity();
                }else {
                    registerView.hideDialog(loginEntity.getMes());
                }
            }
        },identity, phone, password);
    }

    @Override
    public void verifyCode(String code) {
        //校验码
        registerView.VerifyCode(true);
    }

    @Override
    public void cancle() {
        register_modelLmplb.cancle();
    }

    @Override
    public void start() {

    }
}
