package com.sir.app.test.mvp.presenter;

import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvp.contract.MovieContract;
import com.sir.library.retrofit.exception.ResponseThrowable;

import io.reactivex.functions.Consumer;

/**
 * Created by zhuyinan on 2017/8/8.
 */
public class MoviePresenter extends MovieContract.Presenter {

    @Override
    public void getMovie(String city) {
        mView.onLoading("进行中");
        addSubscribe(mModel.getMovie(city).subscribe(new Consumer<MovieResult>() {
            @Override
            public void accept(MovieResult movieResult) {
                mView.onSuccess(100, movieResult);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                ResponseThrowable responseThrowable = (ResponseThrowable) throwable;
                mView.onFailure(100, responseThrowable.message);
            }
        }));
    }
}
