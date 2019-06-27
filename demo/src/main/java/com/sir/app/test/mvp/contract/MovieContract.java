package com.sir.app.test.mvp.contract;

import com.sir.app.test.mvvm.model.bean.MovieResult;
import com.sir.library.mvp.base.BaseModel;
import com.sir.library.mvp.base.BasePresenter;
import com.sir.library.mvp.base.BaseView;

import rx.Observable;

/**
 * Created by zhuyinan on 2017/8/8.
 */
public interface MovieContract {

    interface View extends BaseView {

    }

    interface Model extends BaseModel {
        Observable<MovieResult> getMovie(String city);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getMovie(String city);
    }
}
