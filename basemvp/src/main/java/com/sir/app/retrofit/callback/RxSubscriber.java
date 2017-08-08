package com.sir.app.retrofit.callback;

public abstract class RxSubscriber<T> extends ErrorSubscriber<T> {

    /**
     * 开始请求网络
     * 做一些常见的显示负载并检查网络是网络可用
     */
    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * 请求网络完成
     */
    @Override
    public void onCompleted() {

    }

    /**
     * 获取网络数据
     *
     * @param t
     */
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
}