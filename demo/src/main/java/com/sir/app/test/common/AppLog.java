package com.sir.app.test.common;

import android.app.Application;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

/**
 * 应用日志
 * Created by zhuyinan on 2018/11/21.
 * Contact by 445181052@qq.com
 */
public class AppLog {

    public static void i(String msg) {
        Log.i(AppKey.TAG, msg);
    }

    public static void d(String msg) {
        Log.d(AppKey.TAG, msg);
    }

    public static void e(String msg) {
        Log.e(AppKey.TAG, msg);
    }

    public static void toast(@StringRes int id) {
        toast(MyApplication.getContext().getResources().getString(id));
    }

    public static void toast(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
