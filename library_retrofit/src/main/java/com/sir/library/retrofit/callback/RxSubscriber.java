package com.sir.library.retrofit.callback;

import com.sir.library.retrofit.exception.ResponseThrowable;

import io.reactivex.subscribers.DisposableSubscriber;
/**
 * Created by zhuyinan on 2019/6/28.
 */
public abstract class RxSubscriber<T> extends DisposableSubscriber<T> {

    /**
     * 获取网络数据
     *
     * @param t
     */
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ResponseThrowable) {
            onFailure((ResponseThrowable) e);
        } else if (e instanceof ResponseThrowable) {
            onFailure(new ResponseThrowable(e, 1000));
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 请求成功
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 错误回调
     */
    protected abstract void onFailure(ResponseThrowable ex);
}