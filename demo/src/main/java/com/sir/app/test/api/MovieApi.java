package com.sir.app.test.api;

import com.sir.app.test.entity.MovieResult;
import com.sir.library.retrofit.response.HttpResponse;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
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


/**
 * Created by zhuyinan on 2019/6/25.
 */
public interface MovieApi {

    @GET("movie/pmovie")
    Flowable<HttpResponse<MovieResult>> getMovieA(@Query("key") String key, @Query("city") String city);

    @GET("movie/pmovie")
    Observable<HttpResponse<MovieResult>> getMovieB(@Query("key") String key, @Query("city") String city);

    @GET("movie/pmovie")
    Call<HttpResponse<MovieResult>> getMovieC(@Query("key") String key, @Query("city") String city);
}

