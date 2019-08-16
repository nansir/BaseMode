package com.sir.app.test.mvp.model;

import com.sir.app.test.api.MovieApi;
import com.sir.app.test.common.AppConstant;
import com.sir.app.test.common.MyApplication;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvp.contract.MovieContract;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.transformer.ComposeTransformer;

import io.reactivex.Observable;


/**
 * Created by zhuyinan on 2017/8/8.
 */
public class MovieModel implements MovieContract.Model {

    protected MovieApi apiService;

    public MovieModel() {
        this.apiService = HttpUtils.getInstance(MyApplication.getContext())
                .setLoadMemoryCache(false)//是否加载内存缓存数据
                .setLoadDiskCache(true)//是否加载内存缓存数据
                .getRetrofitClient()
                .setBaseUrl(AppConstant.URL_HOST)
                .builder(MovieApi.class);
    }

    @Override
    public Observable<MovieResult> getMovie(String city) {
        return apiService.getMovieB("eff63ec0285b079f8fe418a13778a10d", city)
                .compose(ComposeTransformer.<MovieResult>Observable());
    }
}
