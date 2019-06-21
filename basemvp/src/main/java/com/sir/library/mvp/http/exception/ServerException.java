package com.sir.library.mvp.http.exception;

/**
 * 服务异常处理
 * Created by zhuyinan on 2017/3/28.
 */
public class ServerException extends RuntimeException {

    public int code;

    public String message;

    public ServerException(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
