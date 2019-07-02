package com.sir.library.mvvm.base;

import android.arch.lifecycle.MutableLiveData;

import com.sir.library.retrofit.event.LiveBus;
import com.sir.library.retrofit.event.ResState;

import java.util.UUID;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 知识库
 * Created by zhuyinan on 2019/6/21.
 */
public abstract class BaseRepository {

    public static final int ON_LOADING = 0x11;
    public static final int ON_SUCCESS = 0x22;
    public static final int ON_FAILURE = 0x33;

    public MutableLiveData<ResState> loadState;

    protected CompositeDisposable mCompositeDisposable;

    public BaseRepository() {
        this.loadState = new MutableLiveData<>();
    }

    /**
     * 获取事件密钥
     *
     * @return
     */
    protected static String getEventKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * 发布状态
     *
     * @param state
     */
    protected void postState(int state) {
        postState(state, null);
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
    public void postData(Object eventKey, Object t) {
        postData(eventKey, null, t);
    }

    /**
     * 发布数据
     *
     * @param eventKey
     * @param t
     */
    public void postData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }

    /**
     * 添加一个订阅
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 解除订阅
     */
    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
}
