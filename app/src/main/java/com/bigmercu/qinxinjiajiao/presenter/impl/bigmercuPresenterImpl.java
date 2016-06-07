package com.bigmercu.qinxinjiajiao.presenter.impl;

import com.bigmercu.qinxinjiajiao.contract.bigmercuContract;
import com.bigmercu.qinxinjiajiao.entity.DetialInfoEntity;
import com.bigmercu.qinxinjiajiao.model.bigmercuModel;
import com.bigmercu.qinxinjiajiao.model.impl.getDetialInfoModelImpl;

import java.util.HashMap;

/**
 * Created by bigmercu on 16/5/17.
 */
public class bigmercuPresenterImpl implements bigmercuContract.Presenter,bigmercuModel.OnGetDetialInfoLinstener,bigmercuContract.orderListener{

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
    public void updateOrder(HashMap map) {

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

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFiled(String mes) {
    }

}
