package com.bigmercu.qinxinjiajiao.contract;

import com.bigmercu.qinxinjiajiao.BasePresenter;
import com.bigmercu.qinxinjiajiao.BaseView;

/**
 * Created by bigmercu on 16/5/16.
 */
public interface registerContract {
    interface Presenter extends BasePresenter{
        void uploadInfo(String identity, String phone, String password);
        void verifyCode(String code);
        void cancle();
    }
    interface View extends BaseView<registerContract.Presenter>{
        void setYZCode(String code);
        void hideDialog(String message);
        void startActivity();
        void onFiled(String msg);
        void VerifyCode(boolean isOk);
    }
}
