package com.sir.library.mvc.event;

/**
 * Created by zhuyinan on 2019/6/27.
 */
public class ResState {

    private int code;

    private String msg;

    public ResState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }
}
