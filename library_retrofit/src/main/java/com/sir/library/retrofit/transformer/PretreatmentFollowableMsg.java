package com.sir.library.retrofit.transformer;

import com.sir.library.retrofit.exception.ExceptionHandle;
import com.sir.library.retrofit.exception.ServerException;
import com.sir.library.retrofit.response.HttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by zhuyinan on 2019/6/28.
 */
public class PretreatmentFollowableMsg<String> implements FlowableTransformer<HttpResponse, String> {

    private static PretreatmentFollowableMsg instance = null;

    private PretreatmentFollowableMsg() {

    }

    /**
     * 双重校验锁单例(线程安全)
     */
    public static <String> PretreatmentFollowableMsg<String> getInstance() {
        if (instance == null) {
            synchronized (PretreatmentObservable.class) {
                if (instance == null) {
                    instance = new PretreatmentFollowableMsg();
                }
            }
        }
        return instance;
    }

    @Override
    public Publisher<String> apply(Flowable<HttpResponse> upstream) {
        return upstream.map(new Function<HttpResponse, String>() {
            //对服务器端给出Json数据进行校验
            @Override
            public String apply(HttpResponse response) {
                if (response.getResCode() != 1) {
                    //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                    throw new ServerException(response.getResMsg(), response.getResCode());
                }
                //服务器请求数据成功，返回里面的数据实体
                return (String) response.getResMsg();
            }
        }).onErrorResumeNext(new Function<Throwable, Publisher<? extends String>>() {
            @Override
            public Publisher<? extends String> apply(Throwable throwable) {
                return Flowable.error(ExceptionHandle.handleException(throwable));
            }
        });
    }
}