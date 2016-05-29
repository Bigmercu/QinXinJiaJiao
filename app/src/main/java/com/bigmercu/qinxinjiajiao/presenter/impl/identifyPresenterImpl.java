package com.bigmercu.qinxinjiajiao.presenter.impl;

import android.util.Log;

import com.bigmercu.qinxinjiajiao.contract.identifyVerifyContract;
import com.bigmercu.qinxinjiajiao.entity.identifyEntity;
import com.bigmercu.qinxinjiajiao.model.impl.identifyModerImpl;

import java.util.ArrayList;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by bigmercu on 16/4/11.
 */
public class identifyPresenterImpl implements identifyVerifyContract.Presenter {

    private identifyModerImpl identifymodel;
    private identifyVerifyContract.View identifyVerifyView;

    public identifyPresenterImpl(identifyVerifyContract.View identifyVerifyInter) {
        this.identifyVerifyView = identifyVerifyInter;
        this.identifyVerifyView.setPresenter(this);
//        identifymodel = new identifyModerImpl();
    }
//你大爷
    @Override
    public void updateInfo(Map<String,String> ListMap, ArrayList<String> selectPhotos) {
        identifyModerImpl.getInstance().updateInfo(new Subscriber<identifyEntity>() {
            @Override
            public void onCompleted() {
                Log.d("completeA","complete");
            }

            @Override
            public void onError(Throwable e) {
                identifyVerifyView.onFiled(e.getMessage());
            }

            @Override
            public void onNext(identifyEntity identifyEntity) {
                Log.d("message",identifyEntity.getMes());
                int code = identifyEntity.getCode();
                if(code == 1 || code == 2) {
                    if (code ==1) {
                        identifyVerifyView.onSuccess(identifyEntity.getMes());
                    } else {
                        identifyVerifyView.onFiled(identifyEntity.getMes());
                    }
                }else {
                    identifyVerifyView.onFiled("认证失败，请检查网络和填写内容后再次提交！");
                }
            }
        },ListMap,selectPhotos);
    }

    @Override
    public void cancle() {
        identifymodel.cancle();
    }

    @Override
    public void start() {
        
    }
}
