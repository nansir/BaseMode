package com.sir.app.test.mvvm.model;


import com.sir.app.test.api.MovieApi;
import com.sir.app.test.common.MyApplication;
import com.sir.library.mvvm.base.BaseRepository;
import com.sir.library.mvvm.event.LiveBus;
import com.sir.library.retrofit.HttpUtils;

/**
 * @author：tqzhang on 18/7/26 16:15
 */
public class Repository extends BaseRepository {

    protected MovieApi apiService;

    public Repository() {
        this.apiService = HttpUtils.getInstance(MyApplication.getContext())
                .getRetrofitClient()
                .setBaseUrl("http://op.juhe.cn/onebox/")
                .builder(MovieApi.class);
    }

    protected void showPageState(Object eventKey, String state) {
        postData(eventKey, state);
    }

    protected void postData(Object eventKey, Object t) {
        postData(eventKey, null, t);
    }

    protected void postData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }

    protected void showPageState(Object eventKey, String tag, String state) {
        postData(eventKey, tag, state);
    }
}
