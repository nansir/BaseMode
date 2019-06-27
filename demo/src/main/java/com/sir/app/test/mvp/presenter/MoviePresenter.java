package com.sir.app.test.mvp.presenter;

import com.sir.app.test.mvp.contract.MovieContract;
import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.library.retrofit.callback.RxSubscriber;
import com.sir.library.retrofit.exception.ResponseThrowable;

/**
 * Created by zhuyinan on 2017/8/8.
 */
public class MoviePresenter extends MovieContract.Presenter {

    @Override
    public void getMovie(String city) {
      mView.onProgress("进行中");
        addSubscribe(mModel.getMovie(city).subscribe(new RxSubscriber<MovieResult>() {
            @Override
            public void onSuccess(MovieResult movieResult) {
                mView.onSuccess(100, movieResult);
            }

            @Override
            protected void onError(ResponseThrowable ex) {
                mView.onFailure(100, ex.message);
            }
        }));
    }
}
