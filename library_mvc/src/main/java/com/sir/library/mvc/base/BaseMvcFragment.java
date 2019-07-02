package com.sir.library.mvc.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.sir.library.base.BaseFragmentV4;
import com.sir.library.mvc.ContractProxy;
import com.sir.library.retrofit.event.ResState;

import java.util.ArrayList;
import java.util.List;

/**
 * MVC 模式的 Control
 * Created by zhuyinan on 2019/6/27.
 */
public abstract class BaseMvcFragment<M extends BaseModel> extends BaseFragmentV4 {

    protected M mModel;

    protected Observer observer = new Observer<ResState>() {
        @Override
        public void onChanged(@Nullable ResState state) {
            if (!TextUtils.isEmpty(state.getMsg())) {
                Toast.makeText(getContext(), state.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    };


    private List<Object> eventKeys = new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
        intModelClazz();
        dataObserver();
    }

    protected void intModelClazz() {
        Class clazz = ContractProxy.getInstance().getModelClazz(getClass(), 0);
        try {
            mModel = ((M) clazz.newInstance());
            mModel.loadState.observe(this, observer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    protected void dataObserver() {

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
        return mModel.subscribe(eventKey, tag, tClass);
    }

    @Override
    public void onDestroyView() {
        if (mModel != null) {
            mModel.unDisposable();
        }
        super.onDestroyView();
    }
}
