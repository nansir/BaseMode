package com.sir.app.test.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sir.app.test.R;
import com.sir.app.test.mvp.contract.MovieContract;
import com.sir.app.test.mvp.model.MovieModel;
import com.sir.app.test.mvp.presenter.MoviePresenter;
import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.library.mvp.base.BaseMvpActivity;
import com.sir.library.mvp.base.BaseView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * MVP 模式
 * Created by zhuyinan on 2017/8/8.
 */
public class MVPActivity extends BaseMvpActivity<MovieModel, MoviePresenter> implements MovieContract.View {

    @BindView(R.id.content)
    TextView content;

    @Override
    public int bindLayout() {
        return R.layout.activity_model;
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void initMvpView(Bundle savedInstanceState) {

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
    public void onFailure(int code, String msg) {
        content.setText(msg);
    }

    @Override
    public void onSuccess(int code, Object object) {
        if (code == 100) {
            MovieResult result = (MovieResult) object;
            content.setText(new Gson().toJson(result));
        }
    }

    @Override
    public void onProgress(String pro) {
        content.setText(pro);
    }

}
