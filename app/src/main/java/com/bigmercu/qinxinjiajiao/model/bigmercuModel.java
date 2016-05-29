package com.bigmercu.qinxinjiajiao.model;

import com.bigmercu.qinxinjiajiao.entity.DetialInfoEntity;

/**
 * Created by bigmercu on 16/5/17.
 */
public interface bigmercuModel {
    void getDetialInfo(String id, OnGetDetialInfoLinstener listener);
    interface OnGetDetialInfoLinstener{
        void OnSuccess(DetialInfoEntity entity);
        void OnFiled(DetialInfoEntity  entity);
    }
}
