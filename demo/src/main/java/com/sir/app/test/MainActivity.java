package com.sir.app.test;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.sir.app.test.api.MovieApi;
import com.sir.app.test.mvp.view.MVPActivity;
import com.sir.app.test.mvvm.model.bean.MovieResponse;
import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.app.test.mvvm.view.MVVMActivity;
import com.sir.library.base.BaseActivity;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.callback.RxSubscriber;
import com.sir.library.retrofit.exception.ResponseThrowable;

import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @OnClick({R.id.mvp, R.id.mvvm, R.id.request})
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.mvp:
                mOperation.forward(MVPActivity.class);
                break;
            case R.id.mvvm:
                mOperation.forward(MVVMActivity.class);
                break;
            case R.id.request:
                setText(R.id.message, "开始请求");
                HttpUtils.getInstance(this)
                        .setLoadMemoryCache(true)//是否加载内存缓存数据
                        .setLoadDiskCache(true)//是否加载内存缓存数据
                        .getRetrofitClient()
                        .setBaseUrl("http://op.juhe.cn/onebox/")
                        .builder(MovieApi.class)
                        .getMovie("eff63ec0285b079f8fe418a13778a10d", "杭州")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<MovieResponse<MovieResult>>() {
                            @Override
                            protected void onError(ResponseThrowable ex) {
                                setText(R.id.message, ex.message);
                            }

                            @Override
                            public void onSuccess(MovieResponse<MovieResult> response) {
                                MovieResult result = response.getResult();
                                setText(R.id.message, new Gson().toJson(result));
                            }
                        });
                break;
        }
    }

}
