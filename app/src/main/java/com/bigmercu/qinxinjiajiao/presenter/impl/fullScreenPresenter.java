package com.bigmercu.qinxinjiajiao.presenter.impl;

import com.bigmercu.qinxinjiajiao.Util.RxBus;
import com.bigmercu.qinxinjiajiao.contract.fullScreenContract;
import com.bigmercu.qinxinjiajiao.contract.homeContract;
import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;
import com.bigmercu.qinxinjiajiao.model.impl.homeFragmentModelImpl;

import java.util.Map;

import sj.mblog.L;

import static com.bigmercu.qinxinjiajiao.presenter.impl.homePresenterImpl.TAG_REFRESH;

/**
 * Created by bigmercu on 16/5/26.
 */

public class fullScreenPresenter implements fullScreenContract.Presenter,homeContract.onGetMoreListener{
    private fullScreenContract.View fullScreenView;
    private homeFragmentModelImpl homeFragmentModel;

    public fullScreenPresenter(fullScreenContract.View view) {
        this.fullScreenView = view;
        this.homeFragmentModel = homeFragmentModelImpl.getInstance();
        view.setPresenter(this);
    }

    @Override
    public void getInfo(Map<String, String> map) {
        homeFragmentModel.getMore(TAG_REFRESH,1,"13125375549",this);
    }

    @Override
    public void start() {
    }

    @Override
    public void onGetMore(MainInfoHome moreInfoHome) {
        fullScreenView.onSuccess(moreInfoHome);
    }

    @Override
    public void onGetFiled(String msg) {
        fullScreenView.onFiled(msg);
    }
}
