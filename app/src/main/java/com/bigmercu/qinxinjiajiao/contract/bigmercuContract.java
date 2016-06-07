package com.bigmercu.qinxinjiajiao.contract;

import com.bigmercu.qinxinjiajiao.BasePresenter;
import com.bigmercu.qinxinjiajiao.BaseView;
import com.bigmercu.qinxinjiajiao.entity.DetialInfoEntity;

import java.util.HashMap;

/**
 * Created by bigmercu on 16/5/17.
 */
public interface bigmercuContract {
    interface Presenter extends BasePresenter{
        void getDetialInfo(String id);
        void updateOrder(HashMap map);
    }

    interface View extends BaseView<bigmercuContract.Presenter>{
        void setDetialInfo(DetialInfoEntity detialInfo);
        void orderSuccess();
        void orderFiled(String mes);
    }

    interface  orderListener{
        void onSuccess();
        void onFiled(String mes);
    }
}
