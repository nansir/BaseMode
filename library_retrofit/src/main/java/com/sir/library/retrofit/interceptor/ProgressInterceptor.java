package com.sir.library.retrofit.interceptor;

import com.sir.library.retrofit.download.ProgressResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 进度截器
 * Created by zhuyinan on 2018/3/28.
 */
public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponse(originalResponse.body()))
                .build();
    }
}
