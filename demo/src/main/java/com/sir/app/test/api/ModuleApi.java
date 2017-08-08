package com.sir.app.test.api;

import com.sir.app.test.model.bean.NewsChannelList;
import com.sir.app.test.model.bean.NewsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuyinan on 2017/8/8.
 * Contact by 445181052@qq.com
 */
public interface ModuleApi {

    @GET("109-34")
    Observable<NewsResponse<NewsChannelList>> getNewsChannelList(@Query("showapi_appid") String appId,
                                                                 @Query("showapi_sign") String apiKey);

}
