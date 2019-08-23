package com.sir.app.test.mvvm.view;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.sir.app.test.R;
import com.sir.app.test.common.AppLog;
import com.sir.library.mvvm.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhuyinan on 2019/8/22.
 */
public class MVVMViewHolder extends BaseViewHolder {

    @BindView(R.id.progress)
    ProgressBar progressBar;

    Unbinder unbinder;

    @Override
    public void initView(Activity content,View view) {
        super.initView(content,view);
        unbinder = ButterKnife.bind(this, mView);
    }

    @Override
    public void doBusiness() {

    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        AppLog.e("BaseViewHolder onDestroy");
    }
}
