package com.sir.library.retrofit.exception;

/**
 * 响应异常处理
 * Created by zhuyinan on 2017/3/28.
 */
public class ResponseThrowable extends Exception {

    public int code;

    public String message;

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
