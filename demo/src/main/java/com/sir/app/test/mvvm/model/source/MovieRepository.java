package com.sir.app.test.mvvm.model.source;

import com.sir.app.test.mvvm.contract.MovieContract;
import com.sir.app.test.mvvm.model.Repository;
import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.app.test.transformer.MovieTransformer;
import com.sir.library.retrofit.callback.RxSubscriber;
import com.sir.library.retrofit.exception.ResponseThrowable;

/**
 * Created by zhuyinan on 2019/6/24.
 */
public class MovieRepository extends Repository implements MovieContract {

    public static String EVENT_KEY_LIVE = null;

    public MovieRepository() {
        if (EVENT_KEY_LIVE == null) {
            EVENT_KEY_LIVE = getEventKey();
        }
    }

    @Override
    public void getMovie(String city) {
        addSubscribe(apiService.getMovie("eff63ec0285b079f8fe418a13778a10d", city)
                .compose(new MovieTransformer<MovieResult>())//进行预处理
                .subscribe(new RxSubscriber<MovieResult>() {

                    @Override
                    public void onSuccess(MovieResult movieResult) {
                        postData(EVENT_KEY_LIVE, movieResult);
                    }

                    @Override
                    protected void onError(ResponseThrowable ex) {

                    }

                }));
    }
}
