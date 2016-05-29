package com.bigmercu.qinxinjiajiao.model;

import com.bigmercu.qinxinjiajiao.contract.homeContract;

/**
 * Created by bigmercu on 16/5/14.
 */
public interface homeFragmentModle {
//    void getDetialInfo(String uid,bigmercuModel.onGetHomeDetialListener listener);
    void getMore(int tag,int num,String phone,homeContract.onGetMoreListener listener);
}
