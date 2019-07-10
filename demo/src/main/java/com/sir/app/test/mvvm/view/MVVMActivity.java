package com.sir.app.test.mvvm.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.sir.app.test.R;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvvm.model.source.MovieRepository;
import com.sir.app.test.mvvm.vm.MovieViewModel;
import com.sir.library.mvvm.base.BaseMVVMActivity;
import com.sir.library.retrofit.download.ProgressCallBack;
import com.sir.library.retrofit.exception.ExceptionHandle;
import com.sir.library.retrofit.exception.ResponseThrowable;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by zhuyinan on 2019/6/24.
 */
public class MVVMActivity extends BaseMVVMActivity<MovieViewModel> {

    String destFileDir;
    String destFileName;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Override
    public int bindLayout() {
        return R.layout.activity_model;
    }

    @Override
    public void doBusiness(Bundle savedInstanceState) {
        destFileDir = Environment.getExternalStorageDirectory().getPath();
        destFileName = System.currentTimeMillis() + ".apk";
    }

    @OnClick({R.id.request, R.id.download})
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.request:
                mViewModel.getMovie("北京");
                break;
            case R.id.download:
                setText(R.id.message, "开始下载");
                mViewModel.download("http://gdown.baidu.com/data/wisegame/a2cd8828b227b9f9/neihanduanzi_692.apk", new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
                    @Override
                    public void progress(long total, long progress, String tag) {
                        progressBar.setMax((int) total);
                        progressBar.setProgress((int) progress);
                        int percent = (int) ((new BigDecimal((float) progress / total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100);
                        setText(R.id.message, "已经下载：" + percent + "%");
                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        setText(R.id.message, "成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ResponseThrowable throwable = ExceptionHandle.handleException(e);
                        setText(R.id.message, throwable.message);
                    }
                });
                break;
        }
    }


    @Override
    protected void dataObserver() {
        mViewModel.subscribe(MovieRepository.EVENT_KEY_LIVE, MovieResult.class)
                .observe(this, new Observer<MovieResult>() {
                    @Override
                    public void onChanged(@Nullable MovieResult result) {
                        setText(R.id.message, new Gson().toJson(result));
                    }
                });
    }
}
