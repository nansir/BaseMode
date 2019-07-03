package com.sir.library.retrofit.download;

import io.reactivex.observers.DisposableObserver;

/**
 * 下载观察
 * Created by zhuyinan on 2018/3/28.
 */
public class DownLoadSubscriber<T> extends DisposableObserver<T> {

    private ProgressCallBack fileCallBack;

    public DownLoadSubscriber(ProgressCallBack fileCallBack) {
        this.fileCallBack = fileCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fileCallBack != null) {
            fileCallBack.onStart();
        }
    }

    @Override
    public void onNext(T t) {
        if (fileCallBack != null) {
            fileCallBack.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (fileCallBack != null) {
            fileCallBack.onError(e);
        }
    }

    @Override
    public void onComplete() {
        if (fileCallBack != null) {
            fileCallBack.onCompleted();
        }
    }
}