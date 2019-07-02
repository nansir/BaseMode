package com.sir.app.test.mvvm.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.sir.app.test.R;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvvm.model.source.MovieRepository;
import com.sir.app.test.mvvm.vm.MovieViewModel;
import com.sir.library.mvvm.base.BaseMVVMActivity;
import com.sir.library.retrofit.download.ProgressCallBack;

import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by zhuyinan on 2019/6/24.
 */
public class MVVMActivity extends BaseMVVMActivity<MovieViewModel> {

    String destFileDir;
    String destFileName;

    @Override
    public int bindLayout() {
        return R.layout.activity_model;
    }

    @Override
    public void doBusiness(Context mContext) {
        destFileDir = getApplication().getCacheDir().getPath();
        destFileName = System.currentTimeMillis() + ".apk";
    }

    @OnClick({R.id.request, R.id.download})
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.request:
                mViewModel.getMovie("北京");
                break;
            case R.id.download:
                setText(R.id.content, "开始下载");
                mViewModel.download("http://gdown.baidu.com/data/wisegame/a2cd8828b227b9f9/neihanduanzi_692.apk", new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
                    @Override
                    public void progress(long progress, long total) {
                        Log.e("TAG", progress + "===" + total);
                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        setText(R.id.content, "成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        setText(R.id.content, "onError");
                    }
                }.subscribeLoadProgress(this));
                break;
        }
    }


    @Override
    protected void dataObserver() {
        mViewModel.subscribe(MovieRepository.EVENT_KEY_LIVE, MovieResult.class)
                .observe(this, new Observer<MovieResult>() {
                    @Override
                    public void onChanged(@Nullable MovieResult result) {
                        setText(R.id.content, new Gson().toJson(result));
                    }
                });
    }
}
