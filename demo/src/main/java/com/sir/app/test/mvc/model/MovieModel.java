package com.sir.app.test.mvc.model;

import com.sir.app.test.api.MovieApi;
import com.sir.app.test.common.AppConstant;
import com.sir.app.test.common.MyApplication;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.entity.UploadFileResponse;
import com.sir.app.test.mvvm.contract.MovieContract;
import com.sir.library.mvc.base.BaseModel;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.callback.RxSubscriber;
import com.sir.library.retrofit.download.DownLoadSubscriber;
import com.sir.library.retrofit.download.ProgressCallBack;
import com.sir.library.retrofit.exception.ResponseThrowable;
import com.sir.library.retrofit.transformer.ComposeTransformer;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by zhuyinan on 2019/6/27.
 */
public class MovieModel extends BaseModel implements MovieContract {

    public String EVENT_KEY_LIVE;
    public String EVENT_UPLOAD_FILE;

    private MovieApi apiService;

    public MovieModel() {

        this.apiService = HttpUtils.getInstance(MyApplication.getContext())
                .getRetrofitClient()
                .setBaseUrl(AppConstant.URL_HOST)
                .builder(MovieApi.class);

        this.EVENT_KEY_LIVE = getEventKey();

    }

    @Override
    public void getMovie(String city) {
        postState(ON_LOADING);
        addSubscribe(apiService.getMovieA("eff63ec0285b079f8fe418a13778a10d", city)
                .compose(ComposeTransformer.<MovieResult>Flowable())
                .subscribeWith(new RxSubscriber<MovieResult>() {
                    @Override
                    public void onSuccess(MovieResult movieResult) {
                        postState(ON_SUCCESS);
                        postData(EVENT_KEY_LIVE, movieResult);
                    }

                    @Override
                    protected void onFailure(ResponseThrowable ex) {
                        postState(ON_FAILURE, ex.message);
                    }
                }));

    }

    @Override
    public void downloadFile(String filePath, final ProgressCallBack callBack) {
        apiService.downloadFile(filePath)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        callBack.saveFile(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DownLoadSubscriber<ResponseBody>(callBack));
    }

    @Override
    public void uploadFile(String filePath) {
        postState(ON_LOADING, "开始上传录音文件..");
        // 上传单一文件
        File file = new File(filePath);
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用file
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        //文件描述
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), "des");

        addSubscribe(apiService.uploadFile(description, body)
                .compose(ComposeTransformer.<UploadFileResponse>Flowable())
                .subscribeWith(new RxSubscriber<UploadFileResponse>() {
                    @Override
                    protected void onSuccess(UploadFileResponse response) {
                        postData(EVENT_UPLOAD_FILE, response);
                    }

                    @Override
                    protected void onFailure(ResponseThrowable ex) {
                        postState(ON_FAILURE, ex.message);
                    }
                }));
    }
}
