package com.sir.app.test.api;

import com.sir.app.test.mvvm.model.bean.MovieResponse;
import com.sir.app.test.mvvm.model.bean.MovieResult;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zhuyinan on 2019/6/25.
 */
public interface MovieApi {

    @GET("movie/pmovie")
    Observable<MovieResponse<MovieResult>> getMovie(@Query("key") String key, @Query("city") String city);

    /**
     * 上传文件请求
     *
     * @param url      URL路径
     * @param paramMap 请求参数
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody> upload(@Url String url, @PartMap Map<String, RequestBody> paramMap);

    /**
     * 下载文件get请求
     *
     * @param url 链接地址
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);


}

