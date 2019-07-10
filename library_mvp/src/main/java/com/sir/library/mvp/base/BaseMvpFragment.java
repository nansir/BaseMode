package com.sir.library.mvp.base;

import android.os.Bundle;

import com.sir.library.base.BaseFragment;
import com.sir.library.mvp.ContractProxy;

/**
 * MVP 模式的 Fragment
 * Created by zhuyinan on 2017/4/5.
 */
public abstract class BaseMvpFragment<M extends BaseModel, P extends BasePresenter> extends BaseFragment {

    protected P mPresenter;

    @Override
    public void initView(Bundle savedInstanceState) {
        initMvpView(savedInstanceState);
        bindMVP();
    }

    public abstract void initMvpView(final Bundle savedInstanceState);

    private void bindMVP() {
        if (getPresenterClazz() != null) {
            mPresenter = getPresenterImpl();
            mPresenter.mContext = getActivity();
            bindVM();
        }
    }

    protected Class getPresenterClazz() {
        return ContractProxy.getPresenterClazz(getClass(), 1);
    }

    private <T> T getPresenterImpl() {
        return ContractProxy.getInstance().presenter(getPresenterClazz());
    }

    private void bindVM() {
        if (mPresenter != null && !mPresenter.isViewBind() && getModelClazz() != null && getViewImp() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().bindView(getViewImp(), mPresenter);
            mPresenter.mContext = getActivity();
        }
    }

    protected Class getModelClazz() {
        return ContractProxy.getModelClazz(getClass(), 0);
    }

    protected abstract BaseView getViewImp();

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            ContractProxy.getInstance().unbindView(getViewImp(), mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().unbindPresenter(getViewImp(), mPresenter);
            mPresenter = null;
        }
        super.onDestroy();
    }
}
