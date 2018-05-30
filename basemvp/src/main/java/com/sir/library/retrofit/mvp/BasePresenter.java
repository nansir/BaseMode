package com.sir.library.retrofit.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends BaseView, M extends BaseModel> implements Presenter<V, M> {

    protected Context mContext;

    protected V mView;

    protected M mModel;

    protected CompositeSubscription mCompositeSubscription;

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    //获取绑定View实例
    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    //获取绑定Model层实例
    @Override
    public void attachModel(M m) {
        this.mModel = m;
    }

    //注销View实例
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    //注销mModel实例
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
