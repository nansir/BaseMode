package com.sir.app.test.api;

import com.sir.app.test.mvvm.model.bean.MovieResponse;
import com.sir.app.test.mvvm.model.bean.MovieResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuyinan on 2019/6/25.
 */
public interface MovieApi {

    @GET("movie/pmovie")
    Observable<MovieResponse<MovieResult>> getMovie(@Query("key") String key, @Query("city") String city);
}
