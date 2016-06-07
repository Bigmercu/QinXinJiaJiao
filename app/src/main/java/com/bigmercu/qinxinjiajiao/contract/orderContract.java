package com.bigmercu.qinxinjiajiao.contract;

import com.bigmercu.qinxinjiajiao.BasePresenter;
import com.bigmercu.qinxinjiajiao.BaseView;

import java.util.Map;

/**
 * Created by bigmercu on 16/5/30.
 * Email:bigmercu@gmail.com
 */

public interface orderContract {
    interface Presenter extends BasePresenter{
        void updateOrder(Map map,StateListener state);
    }
    interface View extends BaseView<BasePresenter>{
    }

    interface StateListener{
        void onSuccess();
        void onFiled();
    }
}
