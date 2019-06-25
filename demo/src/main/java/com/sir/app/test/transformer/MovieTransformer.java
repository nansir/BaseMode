package com.sir.app.test.transformer;

import com.sir.app.test.mvvm.model.bean.MovieResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhuyinan on 2019/6/25.
 */
public class MovieTransformer<T> implements Observable.Transformer<MovieResponse<T>, T> {

    @Override
    public Observable<T> call(Observable<MovieResponse<T>> httpResponseObservable) {
        return httpResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(MovieErrorTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
