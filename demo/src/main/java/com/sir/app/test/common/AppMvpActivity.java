package com.sir.app.test.common;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sir.app.test.R;
import com.sir.app.test.dialog.MessageDialog;
import com.sir.library.mvp.base.BaseModel;
import com.sir.library.mvp.base.BaseMvpActivity;
import com.sir.library.mvp.base.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by zhuyinan on 2019/8/16.
 */
public abstract class AppMvpActivity<M extends BaseModel, P extends BasePresenter> extends BaseMvpActivity<M, P> {

    protected MessageDialog mDialog;
    protected int page = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this);
        mDialog = new MessageDialog(getActivity());
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
