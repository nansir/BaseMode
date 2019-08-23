package com.sir.library.retrofit.request;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 客户端
 * Created by zhuyinan on 2017/4/1.
 */
public class RetrofitClient {

    private static OkHttpClient mOkHttpClient;

    private static String mBaseUrl;

    private static Retrofit mRetrofit;

    private static RetrofitClient mInstance;

    private RetrofitClient() {

    }

    /**
     * 获取请求网络实例
     *
     * @return
     */
    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取OKHttpClient 实例
     *
     * @param mOkHttpClient
     */
    public RetrofitClient setOkHttpClient(OkHttpClient mOkHttpClient) {
        this.mOkHttpClient = mOkHttpClient;
        return this;
    }

    /**
     * 修改BaseUrl地址
     *
     * @param baseUrl
     */
    public RetrofitClient setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
        return this;
    }

    /**
     * 获得对应的ApiServcie对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T builder(Class<T> service) {
        if (mBaseUrl == null) {
            throw new RuntimeException("BaseUrl is null!");
        }
        if (service == null) {
            throw new RuntimeException("api Service is null!");
        }
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit.create(service);
    }
}
