package com.sir.app.test.common;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sir.app.test.R;
import com.sir.app.test.dialog.MessageDialog;
import com.sir.library.mvvm.base.BaseMVVMActivity;
import com.sir.library.mvvm.base.BaseRepository;
import com.sir.library.mvvm.base.BaseViewModel;
import com.sir.library.retrofit.event.ResState;

import butterknife.ButterKnife;

/**
 * Created by zhuyinan on 2018/8/1.
 * Contact by 445181052@qq.com
 */
public abstract class AppMVVMActivity<T extends BaseViewModel> extends BaseMVVMActivity<T> {

    protected MessageDialog mDialog;
    protected int page = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this);
        mDialog = new MessageDialog(getActivity());
    }

    @Override
    protected void notification(ResState state) {
        if (state.getCode() == BaseRepository.ON_LOADING) {
            mDialog.showLoading(state.getMsg());
        } else if (state.getCode() == BaseRepository.ON_SUCCESS) {
            mDialog.showSuccess(state.getMsg());
        } else if (state.getCode() == BaseRepository.ON_FAILURE) {
            mDialog.showError(state.getMsg());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 设置工具标题
     *
     * @param title
     */
    public void setToolbarTitle(CharSequence title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(title);
    }
}
