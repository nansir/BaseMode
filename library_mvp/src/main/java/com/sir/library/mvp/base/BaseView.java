package com.sir.library.mvp.base;

/**
 * MVP 模式的 View
 * Created by zhuyinan on 2017/4/5.
 */
public interface BaseView {

    void onProgress(String pro);

    void onSuccess(int code, Object object);

    void onFailure(int code, String msg);
}
