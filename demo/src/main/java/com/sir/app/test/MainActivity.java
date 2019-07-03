package com.sir.app.test;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.sir.app.test.api.MovieApi;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvc.vc.MVCActivity;
import com.sir.app.test.mvp.view.MVPActivity;
import com.sir.app.test.mvvm.view.MVVMActivity;
import com.sir.library.base.BaseActivity;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.callback.RxCallback;
import com.sir.library.retrofit.download.DownLoadManager;
import com.sir.library.retrofit.download.ProgressCallBack;
import com.sir.library.retrofit.exception.ExceptionHandle;
import com.sir.library.retrofit.exception.ResponseThrowable;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;


public class MainActivity extends BaseActivity {


    @BindView(R.id.progress)
    ProgressBar progressBar;

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

    @OnClick({R.id.mvc, R.id.mvp, R.id.mvvm, R.id.request, R.id.download})
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
                load("http://gdown.baidu.com/data/wisegame/a2cd8828b227b9f9/neihanduanzi_692.apk");
                break;
            case R.id.request:
                setText(R.id.message, "开始请求");
                HttpUtils.getInstance(this)
                        .setLoadMemoryCache(true)//是否加载内存缓存数据
                        .setLoadDiskCache(true)//是否加载内存缓存数据
                        .getRetrofitClient()
                        .setBaseUrl("http://op.juhe.cn/onebox/")
                        .builder(MovieApi.class)
                        .getMovieC("eff63ec0285b079f8fe418a13778a10d", "杭州")
                        .enqueue(new RxCallback<MovieResult>() {
                            @Override
                            protected void onFailure(ResponseThrowable ex) {
                                setText(R.id.message, ex.message);
                            }

                            @Override
                            protected void onSuccess(MovieResult movieResult) {
                                setText(R.id.message, new Gson().toJson(movieResult));
                            }
                        });
                break;
        }


    }

    //下载
    public void load(String downUrl) {
        String destFileDir = Environment.getExternalStorageDirectory().getPath();
        String destFileName = System.currentTimeMillis() + ".apk";
        DownLoadManager.getInstance().downLoad(downUrl, new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
            @Override
            public void progress(long total, long progress, String tag) {
                progressBar.setMax((int) total);
                progressBar.setProgress((int) progress);
                int percent = (int) ((new BigDecimal((float) progress / total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100);
                setText(R.id.message, "已经下载：" + percent + "%");
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                setText(R.id.message, "完成");
            }

            @Override
            public void onError(Throwable e) {
                ResponseThrowable throwable = ExceptionHandle.handleException(e);
                setText(R.id.message, throwable.message);
            }
        });
    }
}
