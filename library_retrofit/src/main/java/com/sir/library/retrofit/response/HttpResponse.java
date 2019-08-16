package com.sir.library.retrofit.response;

import com.google.gson.annotations.SerializedName;

/**
 * 约定服务器公共的json数据
 * Created by zhuyinan on 2017/3/28.
 */
public class HttpResponse<T> {

    @SerializedName("error_code")
    private int resCode;

    @SerializedName("reason")
    private String resMsg;

    @SerializedName("result")
    private T resBody;

    public int getResCode() {
        return resCode;
    }

    public String getResMsg() {
        return resMsg == null ? "" : resMsg;
    }

    public T getResBody() {
        return resBody;
    }


}
