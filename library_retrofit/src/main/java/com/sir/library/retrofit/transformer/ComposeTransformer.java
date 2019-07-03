package com.sir.library.retrofit.transformer;

import com.sir.library.retrofit.response.HttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhuyinan on 2019/7/2.
 */
public class ComposeTransformer {

    public static <T> FlowableTransformer<HttpResponse<T>, T> Flowable() {
        return new FlowableTransformer<HttpResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<HttpResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(PretreatmentFollowable.<T>getInstance());
            }
        };
    }

    public static <T> ObservableTransformer<HttpResponse<T>, T> Observable() {
        return new ObservableTransformer<HttpResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(PretreatmentObservable.<T>getInstance());
            }
        };
    }
}
