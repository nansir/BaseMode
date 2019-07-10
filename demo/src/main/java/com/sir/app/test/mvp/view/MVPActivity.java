package com.sir.app.test.mvp.view;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.sir.app.test.R;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvp.contract.MovieContract;
import com.sir.app.test.mvp.model.MovieModel;
import com.sir.app.test.mvp.presenter.MoviePresenter;
import com.sir.library.mvp.base.BaseMvpActivity;
import com.sir.library.mvp.base.BaseView;

import butterknife.OnClick;

/**
 * MVP 模式
 * Created by zhuyinan on 2017/8/8.
 */
public class MVPActivity extends BaseMvpActivity<MovieModel, MoviePresenter> implements MovieContract.View {

    @Override
    public int bindLayout() {
        return R.layout.activity_model;
    }

    @Override
    public void doBusiness(Bundle savedInstanceState) {

    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @OnClick(R.id.request)
    public void onClickBtn(View view) {
        mPresenter.getMovie("深圳市");
    }

    @Override
    public void onLoading(String pro) {
        setText(R.id.message, pro);
    }

    @Override
    public void onSuccess(int code, Object object) {
        if (code == 100) {
            MovieResult result = (MovieResult) object;
            setText(R.id.message, new Gson().toJson(result));
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        setText(R.id.message, msg);
    }
}
