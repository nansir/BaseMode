package com.sir.app.test.mvvm.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.Gson;
import com.sir.app.test.R;
import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.app.test.mvvm.model.source.MovieRepository;
import com.sir.app.test.mvvm.vm.MovieViewModel;
import com.sir.library.mvvm.base.BaseMVVMActivity;
import com.sir.library.mvvm.event.LiveBus;

import butterknife.OnClick;

/**
 * Created by zhuyinan on 2019/6/24.
 */
public class MVVMActivity extends BaseMVVMActivity<MovieViewModel> {

    @Override
    public int bindLayout() {
        return R.layout.activity_model;
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @OnClick(R.id.request)
    public void onClickBtn(View view) {
        mViewModel.getMovie("北京");
    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe(MovieRepository.EVENT_KEY_LIVE, MovieResult.class).observe(this, new Observer<MovieResult>() {
            @Override
            public void onChanged(@Nullable MovieResult movieResult) {
                setText(R.id.content, new Gson().toJson(movieResult));
            }
        });
    }
}
