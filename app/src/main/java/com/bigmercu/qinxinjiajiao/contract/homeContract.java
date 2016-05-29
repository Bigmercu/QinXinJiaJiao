package com.bigmercu.qinxinjiajiao.contract;

import com.bigmercu.qinxinjiajiao.BasePresenter;
import com.bigmercu.qinxinjiajiao.BaseView;
import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;

/**
 * Created by bigmercu on 16/5/16.
 */
public interface homeContract {
    interface Presenter extends BasePresenter{
        void getMore(int tag);
        void getMore(int tag,int num);
        void onDestroy();
    }

    interface View extends BaseView<homeContract.Presenter>{
        void setData(MainInfoHome mainInfoHome);
        void OnLoadingMoreFiled(String msg);
        void setPresenter(homeContract.Presenter presenter);
    }

    interface onGetMoreListener{
        void onGetMore(MainInfoHome moreInfoHome);
        void onGetFiled(String msg);
    }
}
