package com.sir.library.mvp.base;

/**
 * MVP 模式的 View
 * Created by zhuyinan on 2017/4/5.
 * Contact by 445181052@qq.com
 */
public interface BaseView {

    void onFailure(int code, String msg);

    void onSuccess(int code, Object object);
}
