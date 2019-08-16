package com.sir.library.retrofit.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 异常处理(自定义常码)
 * Created by zhuyinan on 2017/3/28.
 */
public class ExceptionHandle {

    /**
     * 定义网络异常码
     */
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ErrorCode.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    ex.message = "未经授权";
                    break;
                case NOT_FOUND:
                    ex.message = "404未找到";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "请求超时";
                    break;
                case GATEWAY_TIMEOUT:
                    ex.message = "网关超时";
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.message = "服务器内部错误";
                    break;
                case BAD_GATEWAY:
                    ex.message = "网关错误";
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.message = "暂停服务";
                    break;
                default:
                    ex.message = "网络异常";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ErrorCode.PARSE_ERROR);
            ex.message = "解析异常";
            return ex;
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            ex = new ResponseThrowable(e, ErrorCode.NETWORK_ERROR);
            ex.message = "网络异常";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ErrorCode.SSL_ERROR);
            ex.message = "证书验证异常";
            return ex;
        } else if (e instanceof ConnectTimeoutException || e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseThrowable(e, ErrorCode.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else {
            ex = new ResponseThrowable(e, ErrorCode.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }
}

