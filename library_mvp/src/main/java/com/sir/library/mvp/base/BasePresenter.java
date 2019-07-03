package com.sir.library.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<V extends BaseView, M extends BaseModel> implements Presenter<V, M> {

    protected Context mContext;

    protected V mView;

    protected M mModel;

    protected CompositeDisposable mCompositeDisposable;

    /**
     * 添加一个订阅
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void attachModel(M m) {
        this.mModel = m;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unDisposable();
    }

    /**
     * 解除订阅
     */
    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void detachModel() {
        this.mModel = null;
    }

    public M getModel() {
        return mModel;
    }

    public V getView() {
        return mView;
    }

    public boolean isViewBind() {
        return mView != null;
    }

    public void startActivity(Class<? extends Activity> activity) {
        Intent mIntent = new Intent();
        mIntent.setClass(mContext, activity);
        mContext.startActivity(mIntent);
    }
}
