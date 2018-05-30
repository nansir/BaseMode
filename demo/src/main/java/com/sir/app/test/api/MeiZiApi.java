package com.sir.app.test.api;

import com.sir.app.test.model.bean.MeiZiResponse;
import com.sir.app.test.model.bean.MeiZi;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dell on 2017/4/1.
 */

public interface MeiZiApi {


    @GET("福利/10/1")
    Observable<MeiZiResponse<List<MeiZi>>> getMezi();

    /**
     * @param page
     * @param number
     * @return
     */
    @Headers("Cache-Control: public, max-age=100")//设置缓存 缓存时间为100s
    @GET("everySay/selectAll.do")
    Observable<MeiZiResponse<List<MeiZi>>> lookBack(@Query("page") int page, @Query("rows") int number);


    @POST("upload/uploadFile.do")
    Observable<MeiZiResponse> uploadFiles(@Part("filename") String description,
                                          @Part("pic\"; filename=\"image1.png") RequestBody imgs1,
                                          @Part("pic\"; filename=\"image2.png") RequestBody imgs2);

    @POST("upload/uploadFile.do")
    Observable<MeiZiResponse> uploadFiles(@Part("filename") String description, @PartMap() Map<String, RequestBody> maps);

}
