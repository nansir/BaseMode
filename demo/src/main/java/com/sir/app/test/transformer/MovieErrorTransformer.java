package com.sir.app.test.transformer;

import com.sir.app.test.mvvm.model.bean.MovieResponse;
import com.sir.library.retrofit.exception.ExceptionHandle;
import com.sir.library.retrofit.exception.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zhuyinan on 2019/6/25.
 */
public class MovieErrorTransformer<T> implements Observable.Transformer<MovieResponse<T>, T> {

    private static MovieErrorTransformer instance = null;

    private MovieErrorTransformer() {

    }

    public static <T> MovieErrorTransformer<T> create() {
        return new MovieErrorTransformer<>();
    }

    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T> MovieErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (MovieErrorTransformer.class) {
                if (instance == null) {
                    instance = new MovieErrorTransformer();
                }
            }
        }
        return instance;
    }


    @Override
    public Observable<T> call(Observable<MovieResponse<T>> httpResponseObservable) {
        //   对服务器端给出Json数据进行校验
        return httpResponseObservable.map(new Func1<MovieResponse<T>, T>() {
            @Override
            public T call(MovieResponse<T> tHttpResponse) {
                if (tHttpResponse.getError_code() != 0) {
                    //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                    throw new ServerException(String.valueOf(tHttpResponse.getReason()), tHttpResponse.getError_code());
                }
                //服务器请求数据成功，返回里面的数据实体
                return tHttpResponse.getResult();
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

}
