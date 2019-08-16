package com.sir.app.test.common;

import android.os.Bundle;

import com.sir.app.test.dialog.MessageDialog;
import com.sir.library.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by zhuyinan on 2019/8/16.
 */
public abstract class AppActivity extends BaseActivity {

    protected MessageDialog mDialog;
    protected int page = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
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
}
