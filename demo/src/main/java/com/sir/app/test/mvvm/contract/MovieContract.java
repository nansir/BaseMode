package com.sir.app.test.mvvm.contract;

import com.sir.library.retrofit.download.ProgressCallBack;

/**
 * Created by zhuyinan on 2019/6/25.
 */
public interface MovieContract {
    void getMovie(String city);

    void downloadFile(String filePath, ProgressCallBack callBack);

    void uploadFile(String filePath);

}
