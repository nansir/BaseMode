package com.sir.app.test;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.sir.app.test.api.MovieApi;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvc.vc.MVCActivity;
import com.sir.app.test.mvp.view.MVPActivity;
import com.sir.app.test.mvvm.view.MVVMActivity;
import com.sir.library.base.BaseActivity;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.callback.RxCallback;
import com.sir.library.retrofit.download.ProgressCallBack;
import com.sir.library.retrofit.exception.ResponseThrowable;

import javax.xml.transform.Transformer;

import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class MainActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSwipeBackEnable(false);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @OnClick({R.id.mvc, R.id.mvp, R.id.mvvm, R.id.request})
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.mvc:
                mOperation.forward(MVCActivity.class);
                break;
            case R.id.mvp:
                mOperation.forward(MVPActivity.class);
                break;
            case R.id.mvvm:
                mOperation.forward(MVVMActivity.class);
                break;
            case R.id.download:

                break;
            case R.id.request:
                setText(R.id.message, "开始请求");
                HttpUtils.getInstance(this)
                        .setLoadMemoryCache(true)//是否加载内存缓存数据
                        .setLoadDiskCache(true)//是否加载内存缓存数据
                        .getRetrofitClient()
                        .setBaseUrl("http://op.juhe.cn/onebox/")
                        .builder(MovieApi.class)
                        .getMovieC("eff63ec0285079f8fe418a13778a10d", "杭州")
                        .enqueue(new RxCallback<MovieResult>() {
                            @Override
                            protected void onSuccess(MovieResult movieResult) {
                                setText(R.id.message, new Gson().toJson(movieResult));
                            }

                            @Override
                            protected void onFailure(ResponseThrowable ex) {
                                setText(R.id.message, ex.message);
                            }
                        });
                break;
        }


    }

    //下载
    public void load(String downUrl, final ProgressCallBack callBack) {
//        HttpUtils.getInstance(this)
//                .getRetrofitClient()
//                .setBaseUrl("http://op.juhe.cn/onebox/")
//                .builder(MovieApi.class)
//                .download(downUrl)
//                .asObservable()

    }


}
