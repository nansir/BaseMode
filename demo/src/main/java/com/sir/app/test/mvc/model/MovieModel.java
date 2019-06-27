package com.sir.app.test.mvc.model;

import com.sir.app.test.api.MovieApi;
import com.sir.app.test.common.MyApplication;
import com.sir.app.test.mvvm.contract.MovieContract;
import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.app.test.transformer.MovieTransformer;
import com.sir.library.mvc.base.BaseModel;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.callback.RxSubscriber;
import com.sir.library.retrofit.exception.ResponseThrowable;

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
        addSubscribe(apiService.getMovie("eff63ec0285b079f8fe418a13778a10d", city)
                .compose(new MovieTransformer<MovieResult>())//进行预处理
                .subscribe(new RxSubscriber<MovieResult>() {

                    @Override
                    public void onSuccess(MovieResult movieResult) {
                        postData(EVENT_KEY_LIVE, movieResult);
                        postState(200,"成功");
                    }

                    @Override
                    protected void onError(ResponseThrowable ex) {
                        postState(ex.code, ex.message);
                    }

                }));
    }
}
