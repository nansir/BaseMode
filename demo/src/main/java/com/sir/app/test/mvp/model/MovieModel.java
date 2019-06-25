package com.sir.app.test.mvp.model;

import com.sir.app.test.api.MovieApi;
import com.sir.app.test.common.MyApplication;
import com.sir.app.test.mvp.contract.MovieContract;
import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.app.test.transformer.MovieTransformer;
import com.sir.library.retrofit.HttpUtils;

import rx.Observable;

/**
 * Created by zhuyinan on 2017/8/8.
 */
public class MovieModel implements MovieContract.Model {

    @Override
    public Observable<MovieResult> getMovie(String city) {
        return HttpUtils.getInstance(MyApplication.getContext())
                .setLoadMemoryCache(false)//是否加载内存缓存数据
                .setLoadDiskCache(true)//是否加载内存缓存数据
                .getRetrofitClient()
                .setBaseUrl("http://op.juhe.cn/onebox/")
                .builder(MovieApi.class)
                .getMovie("eff63ec0285b079f8fe418a13778a10d", city)
                .compose(new MovieTransformer<MovieResult>());// 进行预处理;
    }
}
