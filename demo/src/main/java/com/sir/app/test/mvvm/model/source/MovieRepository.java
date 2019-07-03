package com.sir.app.test.mvvm.model.source;

import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvvm.contract.MovieContract;
import com.sir.app.test.mvvm.model.Repository;
import com.sir.library.retrofit.download.DownLoadManager;
import com.sir.library.retrofit.download.ProgressCallBack;
import com.sir.library.retrofit.exception.ResponseThrowable;
import com.sir.library.retrofit.transformer.ComposeTransformer;

import io.reactivex.functions.Consumer;

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
        postState(ON_LOADING, "开始加工....");
        addSubscribe(apiService.getMovieB("eff63ec0285b079f8fe418a13778a10d", city)
                .compose(ComposeTransformer.<MovieResult>Observable())
                .subscribe(new Consumer<MovieResult>() {
                    @Override
                    public void accept(MovieResult movieResult) {
                        postData(EVENT_KEY_LIVE, movieResult);
                        postState(ON_SUCCESS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        ResponseThrowable ex = (ResponseThrowable) throwable;
                        postState(ON_FAILURE, ex.message);
                    }
                }));
    }

    @Override
    public void download(String url, final ProgressCallBack callBack) {
        DownLoadManager.getInstance().downLoad(url, callBack);
    }
}
