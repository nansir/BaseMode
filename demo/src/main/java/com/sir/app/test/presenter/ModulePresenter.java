package com.sir.app.test.presenter;

import android.content.Context;

import com.sir.app.retrofit.callback.RxSubscriber;
import com.sir.app.retrofit.exception.ResponseThrowable;
import com.sir.app.test.contract.ModuleContract;
import com.sir.app.test.model.bean.NewsChannelList;

/**
 * Created by zhuyinan on 2017/8/8.
 * Contact by 445181052@qq.com
 */
public class ModulePresenter extends ModuleContract.Presenter {

    @Override
    public void getNewsChannelList(Context context) {
        addSubscribe(mModel.getNewsChannelList(context).subscribe(new RxSubscriber<NewsChannelList>() {
            @Override
            public void onSuccess(NewsChannelList newsChannelList) {
                mView.onSuccess(100, newsChannelList);
            }

            @Override
            protected void onError(ResponseThrowable ex) {
                mView.onFailure(ex.message);
            }
        }));
    }
}
