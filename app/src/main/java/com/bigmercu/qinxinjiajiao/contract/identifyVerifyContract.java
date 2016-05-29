package com.bigmercu.qinxinjiajiao.contract;

import com.bigmercu.qinxinjiajiao.BasePresenter;
import com.bigmercu.qinxinjiajiao.BaseView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by bigmercu on 16/5/16.
 */
public interface identifyVerifyContract {
    interface Presenter extends BasePresenter{
        void updateInfo(Map<String,String> ListMap, ArrayList<String> selectPhotos);
        void cancle();
    }

    interface View extends BaseView<identifyVerifyContract.Presenter>{
        //上层调用下层接口
        void updateInfo();
        void onSuccess(String info);
        void onFiled(String msg);
    }
}
