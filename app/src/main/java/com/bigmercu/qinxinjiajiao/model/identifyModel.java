package com.bigmercu.qinxinjiajiao.model;

import com.bigmercu.qinxinjiajiao.entity.identifyEntity;

import java.util.ArrayList;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by bigmercu on 16/4/11.
 */
public interface identifyModel {
    void updateInfo(Subscriber<identifyEntity> subscriber, Map<String, String> ListMap, ArrayList<String> selectPhotos);
    void cancle();
}
