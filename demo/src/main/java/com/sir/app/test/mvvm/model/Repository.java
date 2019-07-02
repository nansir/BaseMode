package com.sir.app.test.mvvm.model;

import com.sir.app.test.api.MovieApi;
import com.sir.app.test.common.MyApplication;
import com.sir.library.mvvm.base.BaseRepository;
import com.sir.library.retrofit.HttpUtils;

public class Repository extends BaseRepository {

    protected MovieApi apiService;







    public Repository() {
        this.apiService = HttpUtils.getInstance(MyApplication.getContext())
                .getRetrofitClient()
                .setBaseUrl("http://op.juhe.cn/onebox/")
                .builder(MovieApi.class);
    }
}
