package com.sir.library.mvp.base;

import android.os.Bundle;

import com.sir.library.base.BaseActivity;

/**
 * MVP 模式的 activity_skin
 * Created by zhuyinan on 2017/4/5.
 * Contact by 445181052@qq.com
 */
public abstract class BaseMvpActivity<M extends BaseModel, P extends BasePresenter> extends BaseActivity {

    //  定义Presenter
    protected P mPresenter;

    @Override
    public void initView(Bundle savedInstanceState) {
        initMvpView(savedInstanceState);
        bindMVP();
    }

    public abstract void initMvpView(final Bundle savedInstanceState);

    //获取presenter 实例
    private void bindMVP() {
        if (getPresenterClazz() != null) {
            mPresenter = getPresenterImpl();
            mPresenter.mContext = this;
            bindVM();
        }
    }

    //   获得抽取接口Presenter对象
    protected Class getPresenterClazz() {
        return ContractProxy.getPresnterClazz(getClass(), 1);
    }

    //  获得实现接口Presenter对象
    private <T> T getPresenterImpl() {
        return ContractProxy.getInstance().presenter(getPresenterClazz());
    }

    //结合Model and View
    private void bindVM() {
        if (mPresenter != null && !mPresenter.isViewBind() && getModelClazz() != null && getViewImp() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().bindView(getViewImp(), mPresenter);
            mPresenter.mContext = this;
        }
    }

    //   获得抽取接口Model对象
    protected Class getModelClazz() {
        return ContractProxy.getModelClazz(getClass(), 0);
    }

    protected abstract BaseView getViewImp();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            ContractProxy.getInstance().unbindView(getViewImp(), mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), mPresenter);
            mPresenter = null;
        }
        super.onDestroy();
    }
}
