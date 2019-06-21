package com.sir.library.mvp.http.callback;

import com.sir.library.mvp.http.exception.ResponseThrowable;

import rx.Subscriber;

public abstract class ErrorSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof ResponseThrowable) {
            onError((ResponseThrowable) e);
        } else {
            onError(new ResponseThrowable(e, 1000));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ResponseThrowable ex);
}
