package com.bigmercu.qinxinjiajiao.contract;

import com.bigmercu.qinxinjiajiao.BasePresenter;
import com.bigmercu.qinxinjiajiao.BaseView;
import com.bigmercu.qinxinjiajiao.entity.DetialInfoEntity;

/**
 * Created by bigmercu on 16/5/17.
 */
public interface bigmercuContract {
    interface Presenter extends BasePresenter{
        void getDetialInfo(String id);
    }

    interface View extends BaseView<bigmercuContract.Presenter>{
        void setDetialInfo(DetialInfoEntity detialInfo);
    }
}
