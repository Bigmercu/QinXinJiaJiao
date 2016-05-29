package com.bigmercu.qinxinjiajiao.presenter.impl;

import android.os.Bundle;

import com.bigmercu.qinxinjiajiao.contract.homeContract;
import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;
import com.bigmercu.qinxinjiajiao.model.impl.homeFragmentModelImpl;

import rx.Subscription;
import sj.mblog.L;

/**
 * Created by bigmercu on 16/5/11.
 */
public class homePresenterImpl implements homeContract.Presenter,
        homeContract.onGetMoreListener {

    private Subscription subscription;
    private homeFragmentModelImpl homeFragmentModel;
    private final homeContract.View homeView;
    public static final int TAG_REFRESH = 0x001;
    public static final int TAG_MORE = 0x002;
    private Bundle data;

    public homePresenterImpl(homeContract.View view, Bundle data) {
        this.homeView = view;
        homeFragmentModel = homeFragmentModelImpl.getInstance();
        view.setPresenter(this);
        this.data = data;
    }

    @Override
    public void getMore(int tag) {

    }

    @Override
    public void getMore(int tag, int num) {
        homeFragmentModel.getMore(tag, num, "13125375549", this);
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void start() {
        if (data != null) {
            onGetMore((MainInfoHome) data.getParcelable("mainInfoHome"));
        } else {
            L.e(">>>>>>>Asa");
            homeFragmentModel.getMore(TAG_REFRESH, 1, "13125375549", this);
        }
    }

    @Override
    public void onGetMore(MainInfoHome moreInfoHome) {
        homeView.setData(moreInfoHome);
    }

    @Override
    public void onGetFiled(String msg) {
        homeView.OnLoadingMoreFiled(msg);
    }

//    @Override
//    public void putDetialToView(TutorDetialEntity tutorDetial) {
//
//    }
}
