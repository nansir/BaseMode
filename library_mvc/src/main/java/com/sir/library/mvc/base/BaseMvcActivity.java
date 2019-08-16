package com.sir.library.mvc.base;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sir.library.base.BaseActivity;
import com.sir.library.mvc.ContractProxy;
import com.sir.library.retrofit.event.ResState;

/**
 * MVC 模式的 Control
 * Created by zhuyinan on 2019/6/27.
 */
public abstract class BaseMvcActivity<M extends BaseModel> extends BaseActivity {

    protected M mModel;

    protected Observer observer = new Observer<ResState>() {
        @Override
        public void onChanged(@Nullable ResState state) {
            notification(state);
        }
    };

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
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    protected abstract void dataObserver();

    protected abstract void notification(ResState state);

    @Override
    protected void onDestroy() {
        if (mModel != null) {
            mModel.unDisposable();
        }
        super.onDestroy();
    }
}
