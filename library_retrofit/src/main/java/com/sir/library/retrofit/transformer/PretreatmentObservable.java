package com.sir.library.retrofit.transformer;

import com.sir.library.retrofit.exception.ExceptionHandle;
import com.sir.library.retrofit.exception.ServerException;
import com.sir.library.retrofit.response.HttpResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by zhuyinan on 2019/6/28.
 */
public class PretreatmentObservable<T> implements ObservableTransformer<HttpResponse<T>, T> {

    private static PretreatmentObservable instance = null;

    private PretreatmentObservable() {

    }

    public static <T> PretreatmentObservable<T> create() {
        return new PretreatmentObservable<>();
    }

    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T> PretreatmentObservable<T> getInstance() {
        if (instance == null) {
            synchronized (PretreatmentObservable.class) {
                if (instance == null) {
                    instance = new PretreatmentObservable();
                }
            }
        }
        return instance;
    }

    @Override
    public ObservableSource<T> apply(Observable<HttpResponse<T>> upstream) {
        return upstream.map(new Function<HttpResponse<T>, T>() {
            //对服务器端给出Json数据进行校验
            @Override
            public T apply(HttpResponse<T> response) {
                if (response.getResCode() != 0) {
                    //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                    throw new ServerException(response.getResMsg(), response.getResCode());
                }
                //服务器请求数据成功，返回里面的数据实体
                return response.getResBody();
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> apply(Throwable throwable) {
                return Observable.error(ExceptionHandle.handleException(throwable));
            }
        });
    }
}