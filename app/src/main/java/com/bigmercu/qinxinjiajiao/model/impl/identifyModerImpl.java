package com.bigmercu.qinxinjiajiao.model.impl;

import android.graphics.Bitmap;

import com.bigmercu.qinxinjiajiao.Util.FileUtil;
import com.bigmercu.qinxinjiajiao.Util.RetrofitClient;
import com.bigmercu.qinxinjiajiao.Util.imageCompress;
import com.bigmercu.qinxinjiajiao.entity.identifyEntity;
import com.bigmercu.qinxinjiajiao.model.identifyModel;
import com.bigmercu.qinxinjiajiao.model.net.identifyService;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sj.mblog.L;

/**
 * Created by bigmercu on 16/4/11.
 */
public class identifyModerImpl implements identifyModel {
//    String bserUrl = "http://192.168.1.100/enterprise/index.php/Home/Tutor/";
    private identifyService service;
    private Subscription subscription;
    int num;

    public identifyModerImpl() {
        RetrofitClient retrofit = RetrofitClient.getInstance();
        service = retrofit.create(identifyService.class);
    }

    //在访问register_modelImpl时创建单例
    private static class SingleHolder {
        private static final identifyModerImpl INSTANCE = new identifyModerImpl();
    }

    //获取单例
    public static identifyModerImpl getInstance() {
        return SingleHolder.INSTANCE;
    }

    @Override
    public void updateInfo(final Subscriber<identifyEntity> subscriber,
                           final Map<String, String> ListMap,
                           final ArrayList<String> selectPhotos) {
        final ArrayList<String> photos = new ArrayList<>();
        final MultipartBody.Builder builder = new MultipartBody.Builder();
        L.d(ListMap.size() + selectPhotos.size());
        final int[] i = {0};

        subscription = Observable.from(selectPhotos)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Bitmap bitmap = imageCompress.getSmallBitmap(s,1280,720);
                        String path = FileUtil.saveBitmap(bitmap,s.substring(s.lastIndexOf("/") + 1,s.lastIndexOf(".")));
                        return path;
                    }
                })
                .map(new Func1<String, File>() {
                    @Override
                    public File call(String s) {
                        return (new File(s));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {
                        L.d(photos.size());
                        //创建一个MultipartBody.Builder
//                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        //向builder 增加数据
//                        MultipartBody.Builder body1 = filesToMultipartBody(photos,builder);
                        MultipartBody.Builder body2;
                        body2 = StringMultipartBody(ListMap,builder);
                        body2.setType(MultipartBody.FORM);
                        MultipartBody multipartBody = body2.build();
                        //上传builder
                        service.updateIdentifyInfo(multipartBody)
                                .subscribeOn(Schedulers.io())
                                .unsubscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(subscriber);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(File s) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"),s);
                        builder.addFormDataPart(String.valueOf("photo" + num), s.getName(), requestBody);
                        num++;
                    }
                });
    }

    @Override
    public void cancle() {
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    //用于向MultipartBody.Builder中增加图片
    public static MultipartBody.Builder filesToMultipartBody(ArrayList<String> path,
                                                             final MultipartBody.Builder builder) {
        final int[] i = {0};
        final String photo = "photo";
        Observable.from(path)
                .map(new Func1<String, File>() {

                    @Override
                    public File call(String s) {
                        return new File(s);
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"),file);
                        builder.addFormDataPart(String.valueOf(photo + i[0]++), file.getName(), requestBody);
                    }
                });
        return builder;
    }
//用于向MultipartBody.Builder中增加键值对
    public static MultipartBody.Builder StringMultipartBody(
            Map<String,String> ListMap,MultipartBody.Builder multipartBody) {
        for (Map.Entry<String,String> entry : ListMap.entrySet()) {
            multipartBody.addFormDataPart(entry.getKey(),entry.getValue());
        }
        return multipartBody;
    }

}
