package com.sir.app.retrofit.mvp;

import android.os.Bundle;

import com.sir.app.base.BaseFragmentV4;


/**
 * MVP 模式的 Fragment
 * Created by zhuyinan on 2017/4/5.
 */

public abstract class BaseMvpFragment<M extends BaseModel, P extends BasePresenter> extends BaseFragmentV4 {

    protected abstract BaseView getViewImp();

    public abstract void initMvpView(final Bundle savedInstanceState);

    //  定义Presenter
    protected P mPresenter;

    @Override
    public void initView(Bundle savedInstanceState) {
        initMvpView(savedInstanceState);
        bindMVP();
    }

    //获取presenter 实例
    private void bindMVP() {
        if (getPresenterClazz() != null) {
            mPresenter = getPresenterImpl();
            mPresenter.mContext = getActivity();
            bindVM();
        }
    }

    //结合Model and View
    private void bindVM() {
        if (mPresenter != null && !mPresenter.isViewBind() && getModelClazz() != null && getViewImp() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().bindView(getViewImp(), mPresenter);
            mPresenter.mContext = getActivity();
        }
    }

    //   获得抽取接口Presenter对象
    protected Class getPresenterClazz() {
        return ContractProxy.getPresnterClazz(getClass(), 1);
    }

    //   获得抽取接口Model对象
    protected Class getModelClazz() {
        return ContractProxy.getModelClazz(getClass(), 0);
    }

    //  获得实现接口Presenter对象
    private <T> T getPresenterImpl() {
        return ContractProxy.getInstance().presenter(getPresenterClazz());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            ContractProxy.getInstance().unbindView(getViewImp(), mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), mPresenter);
            mPresenter = null;
        }
    }


}
