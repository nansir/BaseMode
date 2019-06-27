package com.sir.library.mvvm.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.sir.library.base.BaseActivity;
import com.sir.library.mvvm.ContractProxy;
import com.sir.library.mvvm.event.ResState;

/**
 * Created by zhuyinan on 2019/6/21.
 */
public abstract class BaseMVVMActivity<T extends BaseViewModel> extends BaseActivity {

    protected T mViewModel;

    /**
     * 状态页面监听
     */
    protected Observer observer = new Observer<ResState>() {
        @Override
        public void onChanged(@Nullable ResState state) {
            if (!TextUtils.isEmpty(state.getMsg())) {
                Toast.makeText(getActivity(), state.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) ContractProxy.getInstance(this, 0));
        dataObserver();
        mViewModel.mRepository.loadState.observe(this, observer);
    }

    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);
    }

    protected void dataObserver() {

    }

}
