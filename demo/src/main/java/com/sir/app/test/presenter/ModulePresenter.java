package com.sir.app.test.presenter;

import android.content.Context;

import com.sir.app.test.contract.ModuleContract;
import com.sir.app.test.model.bean.NewsChannelList;
import com.sir.library.mvp.http.callback.RxSubscriber;
import com.sir.library.mvp.http.exception.ResponseThrowable;

/**
 * Created by zhuyinan on 2017/8/8.
 * Contact by 445181052@qq.com
 */
public class ModulePresenter extends ModuleContract.Presenter {

    @Override
    public void getNewsChannelList(Context context) {
        mView.inProgress();
        addSubscribe(mModel.getNewsChannelList(context).subscribe(new RxSubscriber<NewsChannelList>() {
            @Override
            public void onSuccess(NewsChannelList newsChannelList) {
                mView.onSuccess(100, newsChannelList);
            }

            @Override
            protected void onError(ResponseThrowable ex) {
                mView.onFailure(100, ex.message);
            }
        }));
    }
}
