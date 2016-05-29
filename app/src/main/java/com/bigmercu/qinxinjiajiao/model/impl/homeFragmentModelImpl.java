package com.bigmercu.qinxinjiajiao.model.impl;

import com.bigmercu.qinxinjiajiao.Util.RetrofitClient;
import com.bigmercu.qinxinjiajiao.contract.homeContract;
import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;
import com.bigmercu.qinxinjiajiao.model.homeFragmentModle;
import com.bigmercu.qinxinjiajiao.model.net.homeService;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sj.mblog.L;

import static com.bigmercu.qinxinjiajiao.model.impl.identifyModerImpl.StringMultipartBody;

/**
 * Created by bigmercu on 16/5/14.
 */
public class homeFragmentModelImpl implements homeFragmentModle {

    private homeService service;

    public homeFragmentModelImpl(){
        service = RetrofitClient.getInstance().create(homeService.class);
    }

    @Override
    public void getMore(int tag, int num, String phone, final homeContract.onGetMoreListener listener) {
//        listener.onGetFiled("helloASD");
        Map<String,String> map = new HashMap<>();
        map.put("count",String.valueOf(num));
        map.put("school","");
        map.put("course","");
        map.put("sex","");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        MultipartBody.Builder body = StringMultipartBody(map,builder);
        body.setType(MultipartBody.FORM);
        MultipartBody body1 = body.build();
        service.getHomeData(body1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MainInfoHome>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MainInfoHome mainInfoHome) {
                        L.d(mainInfoHome.getMes());
                        if(mainInfoHome.getCode() == 1) {
                            listener.onGetMore(mainInfoHome);
                        }else if(mainInfoHome.getCode() == 2){
                            listener.onGetFiled(mainInfoHome.getMes());
                        }
                    }
                });
    }

    private static class SingleHolder{
        private static final  homeFragmentModelImpl INSTANCE = new homeFragmentModelImpl();
    }

    public static homeFragmentModelImpl getInstance(){
        return SingleHolder.INSTANCE;
    }

//    @Override
//    public void getDetialInfo(String uid, bigmercuModel.onGetHomeDetialListener listener) {
//        TutorDetialEntity detialEntity = null;
//        listener.putDetialToView(detialEntity);
//    }
}
