package com.sir.library.mvp.base;

import android.os.Bundle;

import com.sir.library.base.BaseActivity;
import com.sir.library.mvp.ContractProxy;

/**
 * MVP 模式的 activity_skin
 * Created by zhuyinan on 2017/4/5.
 */
public abstract class BaseMvpActivity<M extends BaseModel, P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    public void initView(Bundle savedInstanceState) {
        bindMVP();
    }

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

    /**
     * 结合Model and View
     */
    private void bindVM() {
        if (mPresenter != null && !mPresenter.isViewBind() && getModelClazz() != null && getViewImp() != null) {
            ContractProxy.getInstance().bindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().bindView(getViewImp(), mPresenter);
            mPresenter.mContext = this;
        }
    }

    protected Class getModelClazz() {
        return ContractProxy.getModelClazz(getClass(), 0);
    }

    protected abstract BaseView getViewImp();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            ContractProxy.getInstance().unbindView(getViewImp(), mPresenter);
            ContractProxy.getInstance().unbindModel(getModelClazz(), mPresenter);
            ContractProxy.getInstance().unbindPresenter(getViewImp(), mPresenter);
            mPresenter = null;
        }
        super.onDestroy();
    }
}
