package com.sir.app.test.transformer;

import com.sir.app.test.model.bean.NewsResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhuyinan on 2017/4/22.
 */
public class NewsTransformer<T> implements Observable.Transformer<NewsResponse<T>, T> {


    @Override
    public Observable<T> call(Observable<NewsResponse<T>> httpResponseObservable) {
        return httpResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(NewsErrorTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
