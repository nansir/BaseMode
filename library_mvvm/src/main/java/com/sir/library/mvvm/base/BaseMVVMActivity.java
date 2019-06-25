package com.sir.library.mvvm.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.sir.library.base.BaseActivity;
import com.sir.library.mvvm.TUtil;

/**
 * Created by zhuyinan on 2019/6/21.
 */
public abstract class BaseMVVMActivity<T extends BaseViewModel> extends BaseActivity {

    protected T mViewModel;

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        dataObserver();
    }

    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);
    }

    protected void dataObserver() {

    }

}
