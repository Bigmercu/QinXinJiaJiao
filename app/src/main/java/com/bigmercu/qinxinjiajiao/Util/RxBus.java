package com.bigmercu.qinxinjiajiao.Util;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by bigmercu on 16/4/18.
 */
public class RxBus {
    private static volatile RxBus defaultInstance;
    private final SerializedSubject<Object, Object> subject;

    private RxBus() {
        subject = new SerializedSubject<>(BehaviorSubject.create());
    }

    public static RxBus getDefault() {
        RxBus rxBus = defaultInstance;
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                rxBus = defaultInstance;
                if (defaultInstance == null) {
                    rxBus = new RxBus();
                    defaultInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    public void post(Object object) {
        subject.onNext(object);
    }

    public   <T> Observable<T> toObservable(final Class<T> type) {
        return subject.ofType(type);
    }

    public boolean hasObservers() {
        return subject.hasObservers();
    }
}
