package com.sir.app.test;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.callback.RxSubscriber;
import com.sir.library.retrofit.exception.ResponseThrowable;
import com.sir.app.test.api.MeiZiApi;
import com.sir.app.test.model.bean.MeiZi;
import com.sir.app.test.model.bean.MeiZiResponse;
import com.sir.app.test.view.ModuleActivity;
import com.sir.library.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.message)
    TextView message;

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

    @OnClick({R.id.mvp, R.id.request})
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.mvp:
                mOperation.forward(ModuleActivity.class);
                break;
            case R.id.request:
                message.setText("开始请求");
                HttpUtils.getInstance(this)
                        .setLoadMemoryCache(true)//是否加载内存缓存数据
                        .setLoadDiskCache(true)//是否加载内存缓存数据
                        .getRetorfitClinet()
                        .setBaseUrl("http://gank.io/api/data/")
                        .builder(MeiZiApi.class)
                        .getMezi()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<MeiZiResponse<List<MeiZi>>>() {
                            @Override
                            public void onSuccess(MeiZiResponse<List<MeiZi>> response) {
                                List<MeiZi> results = response.getResults();
                                message.setText("请求成功，妹子个数为" + results.size()+": "+new Gson().toJson(results));
                            }

                            @Override
                            protected void onError(ResponseThrowable ex) {
                                message.setText(ex.getMessage());
                                Log.e("TAG", ex.getMessage());
                            }
                        });
                break;
        }
    }

}
