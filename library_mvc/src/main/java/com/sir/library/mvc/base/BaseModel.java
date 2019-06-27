package com.sir.library.mvc.base;

import android.arch.lifecycle.MutableLiveData;

import com.sir.library.mvc.event.LiveBus;
import com.sir.library.mvc.event.ResState;

import java.util.UUID;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * MVC 模式的 Model
 * Created by zhuyinan on 2017/4/5.
 */
public class BaseModel {

    public MutableLiveData<ResState> loadState;

    protected CompositeSubscription mCompositeSubscription;

    public BaseModel() {
        this.loadState = new MutableLiveData<>();
    }

    protected static String getEventKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * 发布状态
     *
     * @param state
     */
    protected void postState(int state, String msg) {
        if (loadState != null) {
            loadState.postValue(new ResState(state, msg));
        }
    }

    /**
     * 发布数据
     *
     * @param eventKey
     * @param t
     */
    protected void postData(Object eventKey, Object t) {
        postData(eventKey, null, t);
    }

    /**
     * 发布数据
     *
     * @param eventKey
     * @param t
     */
    protected void postData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }

    /**
     * 订阅
     *
     * @param eventKey
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> MutableLiveData<T> subscribe(Object eventKey, Class<T> tClass) {
        return LiveBus.getDefault().subscribe(eventKey, tClass);
    }

    /**
     * 订阅
     *
     * @param eventKey
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> MutableLiveData<T> subscribe(Object eventKey, String tag, Class<T> tClass) {
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
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
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
