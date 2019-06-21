package com.sir.app.test.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sir.library.mvp.base.BaseMvpActivity;
import com.sir.library.mvp.base.BaseView;
import com.sir.app.test.R;
import com.sir.app.test.contract.ModuleContract;
import com.sir.app.test.model.ModuleModel;
import com.sir.app.test.model.bean.NewsChannelList;
import com.sir.app.test.presenter.ModulePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * MVP 模式
 * Created by zhuyinan on 2017/8/8.
 * Contact by 445181052@qq.com
 */
public class ModuleActivity extends BaseMvpActivity<ModuleModel, ModulePresenter> implements ModuleContract.View {

    @BindView(R.id.content)
    TextView content;

    @Override
    public int bindLayout() {
        return R.layout.activity_module;
    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @Override
    public void initMvpView(Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }


    @OnClick(R.id.request)
    public void onClickBtn(View view) {
        mPresenter.getNewsChannelList(getActivity());
    }

    @Override
    public void onFailure(int code, String msg) {
        content.setText(msg);
    }

    @Override
    public void onSuccess(int code, Object object) {
        if (code == 100) {
            NewsChannelList newsChannelList = (NewsChannelList) object;
            content.setText(new Gson().toJson(newsChannelList));
        }
    }

    @Override
    public void inProgress() {
        content.setText("正在请求..");
    }
}
