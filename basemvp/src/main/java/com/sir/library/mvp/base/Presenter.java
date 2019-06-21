package com.sir.library.mvp.base;

/**
 * MVP 模式的 Presenter
 * Created by zhuyinan on 2017/4/5.
 * Contact by 445181052@qq.com
 */
public interface Presenter<View, Model> {
    //绑定View控件
    void attachView(View view);

    //绑定Model
    void attachModel(Model model);

    //注销View控件
    void detachView();

    //注销Model对象
    void detachModel();
}
