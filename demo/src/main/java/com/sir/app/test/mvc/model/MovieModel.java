package com.sir.app.test.mvc.model;

import com.sir.app.test.api.MovieApi;
import com.sir.app.test.common.MyApplication;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvvm.contract.MovieContract;
import com.sir.library.mvc.base.BaseModel;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.callback.RxSubscriber;
import com.sir.library.retrofit.download.ProgressCallBack;
import com.sir.library.retrofit.exception.ResponseThrowable;
import com.sir.library.retrofit.transformer.ComposeTransformer;

/**
 * Created by zhuyinan on 2019/6/27.
 */
public class MovieModel extends BaseModel implements MovieContract {

    public String EVENT_KEY_LIVE;

    private MovieApi apiService;

    public MovieModel() {
        this.EVENT_KEY_LIVE = getEventKey();
        this.apiService = HttpUtils.getInstance(MyApplication.getContext())
                .getRetrofitClient()
                .setBaseUrl("http://op.juhe.cn/onebox/")
                .builder(MovieApi.class);
    }

    @Override
    public void getMovie(String city) {
        postState(ON_LOADING, "正在加载");
        addSubscribe(apiService.getMovieA("eff63ec0285b079f8fe418a13778a10d", city)
                .compose(ComposeTransformer.<MovieResult>Flowable())
                .subscribeWith(new RxSubscriber<MovieResult>() {
                    @Override
                    public void onSuccess(MovieResult movieResult) {
                        postState(ON_SUCCESS);
                        postData(EVENT_KEY_LIVE, movieResult);
                    }

                    @Override
                    protected void onFailure(ResponseThrowable ex) {
                        postState(ON_FAILURE, ex.message);
                    }
                }));

    }

    @Override
    public void download(String url, ProgressCallBack callBack) {

    }
}
