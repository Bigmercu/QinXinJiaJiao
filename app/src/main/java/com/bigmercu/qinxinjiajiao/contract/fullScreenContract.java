package com.bigmercu.qinxinjiajiao.contract;

import com.bigmercu.qinxinjiajiao.BasePresenter;
import com.bigmercu.qinxinjiajiao.BaseView;
import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;

import java.util.Map;

/**
 * Created by bigmercu on 16/5/26.
 */

public interface fullScreenContract {
    interface Presenter extends BasePresenter {
        void getInfo(Map<String,String> map);
    }

    interface View extends BaseView<fullScreenContract.Presenter> {
        void onFiled(String msg);
        void onSuccess(MainInfoHome mainInfoHome);
    }
}
