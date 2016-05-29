package com.bigmercu.qinxinjiajiao.entity;

/**
 * Created by bigmercu on 16/4/25.
 */
public enum PRINT {
    /**
     * 不打印Log
     */
    NONE,
    /**
     * 使用系统默认Log打印
     */
    SYSTEM,
    /**
     * 使用MBLog格式化打印
     */
    MBLOG,
    /**
     * 使用MBLog格式化打印,但只打印内容,不打印方法和线程等信息
     */
    MBLOG_NOMETHOD
}