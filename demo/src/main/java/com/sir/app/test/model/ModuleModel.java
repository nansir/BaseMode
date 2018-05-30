package com.sir.app.test.model;

import android.content.Context;

import com.sir.app.test.api.ModuleApi;
import com.sir.app.test.contract.ModuleContract;
import com.sir.app.test.model.bean.NewsChannelList;
import com.sir.app.test.transformer.NewsTransformer;
import com.sir.library.retrofit.HttpUtils;

import rx.Observable;

/**
 * Created by zhuyinan on 2017/8/8.
 * Contact by 445181052@qq.com
 */
public class ModuleModel implements ModuleContract.Model {

    @Override
    public Observable<NewsChannelList> getNewsChannelList(Context context) {
        return HttpUtils.getInstance(context)
                .setLoadMemoryCache(false)//是否加载内存缓存数据
                .setLoadDiskCache(true)//是否加载内存缓存数据
                .getRetorfitClinet()
                .setBaseUrl("http://route.showapi.com/")
                .builder(ModuleApi.class)
                .getNewsChannelList("34835", "a7b50249ea4c47ff98bc3b579e20328d")
                .compose(new NewsTransformer<NewsChannelList>());// 进行预处理;
    }
}
