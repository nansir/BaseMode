package com.sir.app.retrofit.mvp;

/**
 * MVP 模式的 View
 * Created by zhuyinan on 2017/4/5.
 */
public interface BaseView {
    void onFailure(int code, String msg);

    void onSuccess(int code, Object object);
}
