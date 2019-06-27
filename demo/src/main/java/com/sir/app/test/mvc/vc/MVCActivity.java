package com.sir.app.test.mvc.vc;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.Gson;
import com.sir.app.test.R;
import com.sir.app.test.mvc.model.MovieModel;

import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.library.mvc.base.BaseMvcActivity;

import butterknife.OnClick;

/**
 * Created by zhuyinan on 2019/6/27.
 */
public class MVCActivity extends BaseMvcActivity<MovieModel> {

    @Override
    public int bindLayout() {
        return R.layout.activity_model;
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @OnClick(R.id.request)
    public void onClickBtn(View view) {
        mModel.getMovie("深圳");
    }

    @Override
    protected void dataObserver() {
        mModel.subscribe(mModel.EVENT_KEY_LIVE, MovieResult.class)
                .observe(this, new Observer<MovieResult>() {
                    @Override
                    public void onChanged(@Nullable MovieResult result) {
                        setText(R.id.content, new Gson().toJson(result));
                    }
                });
    }
}
