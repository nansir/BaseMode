package com.sir.library.retrofit.transformer;

import com.sir.library.retrofit.exception.ExceptionHandle;
import com.sir.library.retrofit.exception.ServerException;
import com.sir.library.retrofit.response.HttpDefaultResponse;

import rx.Observable;
import rx.functions.Func1;


public class ErrorTransformer<T> implements Observable.Transformer<HttpDefaultResponse<T>, T> {

    private static ErrorTransformer instance = null;

    @Override
    public Observable<T> call(Observable<HttpDefaultResponse<T>> httpResponseObservable) {
        //   对服务器端给出Json数据进行校验
        return httpResponseObservable.map(new Func1<HttpDefaultResponse<T>, T>() {
            @Override
            public T call(HttpDefaultResponse<T> tHttpResponse) {
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

    private ErrorTransformer() {
    }

    public static <T> ErrorTransformer<T> create() {
        return new ErrorTransformer<>();
    }

    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T> ErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer.class) {
                if (instance == null) {
                    instance = new ErrorTransformer();
                }
            }
        }
        return instance;
    }
}
