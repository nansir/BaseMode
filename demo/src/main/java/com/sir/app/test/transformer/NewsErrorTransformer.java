package com.sir.app.test.transformer;


import com.sir.app.retrofit.exception.ExceptionHandle;
import com.sir.app.retrofit.exception.ServerException;
import com.sir.app.test.model.bean.NewsResponse;

import rx.Observable;
import rx.functions.Func1;


public class NewsErrorTransformer<T> implements Observable.Transformer<NewsResponse<T>, T> {
    @Override
    public Observable<T> call(Observable<NewsResponse<T>> httpResponseObservable) {
        //   对服务器端给出Json数据进行校验
        return httpResponseObservable.map(new Func1<NewsResponse<T>, T>() {
            @Override
            public T call(NewsResponse<T> tHttpResponse) {
                if (tHttpResponse.getShowapi_res_code() != 0) {
                    //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                    throw new ServerException(String.valueOf(tHttpResponse.getShowapi_res_error()), tHttpResponse.getShowapi_res_code());
                }
                //服务器请求数据成功，返回里面的数据实体
                return tHttpResponse.getShowapi_res_body();
            }
            //  对请求服务器出现错误信息进行拦截
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                throwable.printStackTrace();
                return Observable.error(ExceptionHandle.handleException(throwable));
            }
        });
    }

    public static <T> NewsErrorTransformer<T> create() {
        return new NewsErrorTransformer<>();
    }

    private static NewsErrorTransformer instance = null;

    private NewsErrorTransformer() {
    }

    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T> NewsErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (NewsErrorTransformer.class) {
                if (instance == null) {
                    instance = new NewsErrorTransformer();
                }
            }
        }
        return instance;
    }
}
