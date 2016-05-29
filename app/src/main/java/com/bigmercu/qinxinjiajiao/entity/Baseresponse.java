package com.bigmercu.qinxinjiajiao.entity;

/**
 * Created by bigmercu on 16/4/12.
 */
public class Baseresponse<T> {
    public int code;
    public String msg;
    public T data;
}