package com.sir.app.test.api;

import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.entity.UploadFileResponse;
import com.sir.library.retrofit.response.HttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by zhuyinan on 2019/6/25.
 */
public interface MovieApi {

    @GET("onebox/movie/pmovie")
    Flowable<HttpResponse<MovieResult>> getMovieA(@Query("key") String key, @Query("city") String city);

    @GET("onebox/movie/pmovie")
    Observable<HttpResponse<MovieResult>> getMovieB(@Query("key") String key, @Query("city") String city);

    @GET("onebox/movie/pmovie")
    Call<HttpResponse<MovieResult>> getMovieC(@Query("key") String key, @Query("city") String city);


    /**
     * 下载文件
     * @param
     * @return
     */
    @Streaming
    @Headers("baseUrl:http://gdown.baidu.com/")
    @GET("data/wisegame/{path}")
    Observable<ResponseBody> downloadFile(@Path("path") String fileUrl);

    /**
     * 单文件上传
     *
     * @param description
     * @param file        @Part MultipartBody.Part file 使用MultipartBody.Part类发送文件file到服务器
     * @return 状态信息String
     *      
     */
    @Multipart
    @POST("api/file-manage/upload")
    Flowable<HttpResponse<UploadFileResponse>> uploadFile(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    /**
     * 多文件上传：通过 List<MultipartBody.Part> 传入多个part实现
     *
     * @param arts 每一个part代表一个文件 @return 状态信息String
     *                
     */
    @Multipart
    @POST("api/file-manage/upload")
    Flowable<HttpResponse<UploadFileResponse>> uploadFiles(@Part() List<MultipartBody.Part> arts);
}

