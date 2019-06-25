package com.sir.library.mvvm.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sir.library.base.BaseFragmentV4;
import com.sir.library.mvvm.TUtil;
import com.sir.library.mvvm.event.LiveBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuyinan on 2019/6/21.
 */
public abstract class BaseMVVMFragment<T extends BaseViewModel> extends BaseFragmentV4 {

    protected T mViewModel;
    /**
     * 状态页面监听
     */
    protected Observer observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            if (!TextUtils.isEmpty(state)) {


            }
        }
    };

    private List<Object> eventKeys = new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        if (null != mViewModel) {
            dataObserver();
            mViewModel.mRepository.loadState.observe(this, observer);
        }
    }

    protected <T extends ViewModel> T VMProviders(BaseFragmentV4 fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);
    }

    protected <T> MutableLiveData<T> registerSubscriber(Object eventKey, Class<T> tClass) {
        return registerSubscriber(eventKey, null, tClass);
    }

    protected <T> MutableLiveData<T> registerSubscriber(Object eventKey, String tag, Class<T> tClass) {
        String event;
        if (TextUtils.isEmpty(tag)) {
            event = (String) eventKey;
        } else {
            event = eventKey + tag;
        }
        eventKeys.add(event);
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
    }

    protected abstract void dataObserver();

}
