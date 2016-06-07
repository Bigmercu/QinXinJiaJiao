package com.bigmercu.qinxinjiajiao.model;

import com.bigmercu.qinxinjiajiao.contract.bigmercuContract;

import java.util.HashMap;

/**
 * Created by bigmercu on 16/5/30.
 * Email:bigmercu@gmail.com
 */

public interface orderModel {
    void order(HashMap map, bigmercuContract.orderListener listener);
}
