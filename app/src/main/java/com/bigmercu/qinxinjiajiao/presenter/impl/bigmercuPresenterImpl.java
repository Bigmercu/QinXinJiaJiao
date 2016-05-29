package com.bigmercu.qinxinjiajiao.presenter.impl;

import android.os.Bundle;

import com.bigmercu.qinxinjiajiao.contract.bigmercuContract;
import com.bigmercu.qinxinjiajiao.entity.DetialInfoEntity;
import com.bigmercu.qinxinjiajiao.model.bigmercuModel;
import com.bigmercu.qinxinjiajiao.model.impl.getDetialInfoModelImpl;

/**
 * Created by bigmercu on 16/5/17.
 */
public class bigmercuPresenterImpl implements bigmercuContract.Presenter,bigmercuModel.OnGetDetialInfoLinstener{

    private bigmercuContract.View view;
    private getDetialInfoModelImpl getDetialInfoModel;

    public bigmercuPresenterImpl(bigmercuContract.View view){
        this.view = view;
        getDetialInfoModel = getDetialInfoModelImpl.getInstance();
        view.setPresenter(this);
    }

    @Override
    public void getDetialInfo(String id) {
        getDetialInfoModel.getDetialInfo(id,this);
    }

    @Override
    public void start() {

    }

    @Override
    public void OnSuccess(DetialInfoEntity entity) {
        view.setDetialInfo(entity);
    }

    @Override
    public void OnFiled(DetialInfoEntity  entity) {

    }
}
