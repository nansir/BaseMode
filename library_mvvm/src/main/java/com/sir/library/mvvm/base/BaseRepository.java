package com.sir.library.mvvm.base;

import android.arch.lifecycle.MutableLiveData;

import java.util.UUID;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhuyinan on 2019/6/21.
 */
public abstract class BaseRepository {

    public MutableLiveData<String> loadState;

    private CompositeDisposable mCompositeDisposable;

    protected CompositeSubscription mCompositeSubscription;

    public BaseRepository() {
        this.loadState = new MutableLiveData<>();
    }

    /**
     * 发送状态
     *
     * @param state
     */
    protected void postState(String state) {
        if (loadState != null) {
            loadState.postValue(state);
        }
    }

    /**
     * 添加订阅
     *
     * @param disposable
     */
    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 添加一个订阅
     */
    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * 解除订阅
     */
    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 获取事件密钥
     * @return
     */
    protected static String getEventKey() {
        return UUID.randomUUID().toString();
    }
}
