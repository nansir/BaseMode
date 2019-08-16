package com.sir.app.test.mvvm.view;

import android.arch.lifecycle.Observer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.sir.app.test.R;
import com.sir.app.test.common.AppMVVMActivity;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvvm.model.source.MovieRepository;
import com.sir.app.test.mvvm.vm.MovieViewModel;
import com.sir.library.retrofit.download.DownLoadStateBean;
import com.sir.library.retrofit.download.ProgressCallBack;
import com.sir.library.retrofit.exception.ExceptionHandle;
import com.sir.library.retrofit.exception.ResponseThrowable;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by zhuyinan on 2019/6/24.
 */
public class MVVMActivity extends AppMVVMActivity<MovieViewModel> {

    String destFileDir;
    String destFileName;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Override
    public int bindLayout() {
        return R.layout.activity_model;
    }

    @Override
    public void doBusiness() {
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
                setTextVal(R.id.message, "开始下载");
                mViewModel.downloadFile("a2cd8828b227b9f9/neihanduanzi_692.apk", new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {

                    @Override
                    public void progress(DownLoadStateBean bean) {
                        progressBar.setMax((int) bean.getTotal());
                        progressBar.setProgress((int) bean.getLoaded());
                        setTextVal(R.id.message, "已经下载：" + bean.getPercent() + "%");
                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        setTextVal(R.id.message, "成功");
                    }

                    @Override
                    public void onError(ResponseThrowable throwable) {
                        setTextVal(R.id.message, throwable.message);
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
                        setTextVal(R.id.message, new Gson().toJson(result));
                    }
                });


        mViewModel.subscribe(MovieRepository.EVENT_PROGRESS, DownLoadStateBean.class)
                .observeForever(new Observer<DownLoadStateBean>() {
                    @Override
                    public void onChanged(@Nullable DownLoadStateBean stateBean) {


                    }
                });
    }
}
