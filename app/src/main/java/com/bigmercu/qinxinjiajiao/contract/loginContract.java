package com.bigmercu.qinxinjiajiao.contract;

import com.bigmercu.qinxinjiajiao.BasePresenter;
import com.bigmercu.qinxinjiajiao.BaseView;

import java.util.Map;

/**
 * Created by bigmercu on 16/5/16.
 */
public interface loginContract {
    interface Presenter extends BasePresenter{
        void Login(Map<String,String> map);
    }

    interface View extends BaseView<loginContract.Presenter> {
        void showAndHide(boolean option);
        void Onsuccess();
        void OnFiled(String msg);
    }
}
