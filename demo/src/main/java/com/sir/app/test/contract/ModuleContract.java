package com.sir.app.test.contract;

import android.content.Context;

import com.sir.app.retrofit.mvp.BaseModel;
import com.sir.app.retrofit.mvp.BasePresenter;
import com.sir.app.retrofit.mvp.BaseView;
import com.sir.app.test.model.bean.NewsChannelList;

import rx.Observable;

/**
 * Created by zhuyinan on 2017/8/8.
 * Contact by 445181052@qq.com
 */
public interface ModuleContract {

    interface View extends BaseView{

    }

    interface Model extends BaseModel{
        Observable<NewsChannelList> getNewsChannelList(Context context);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getNewsChannelList(Context context);
    }
}
