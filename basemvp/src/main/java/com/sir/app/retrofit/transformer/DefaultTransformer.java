package com.sir.app.retrofit.transformer;

import com.sir.app.retrofit.response.HttpDefaultResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhuyinan on 2017/4/22.
 */

public class DefaultTransformer<T> implements Observable.Transformer<HttpDefaultResponse<T>, T> {
    @Override
    public Observable<T> call(Observable<HttpDefaultResponse<T>> httpResponseObservable) {
        return httpResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(ErrorTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
