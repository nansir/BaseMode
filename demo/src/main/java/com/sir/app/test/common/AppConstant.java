package com.sir.app.test.common;

import android.os.Environment;

/**
 * Created by zhuyinan on 2019/7/15.
 */
public class AppConstant {

    private static final String ADDRESS = Environment.getExternalStorageDirectory() + "/demo";
    public static final String DEFAULT_CACHE = ADDRESS + "/cache/";
    public static final String DOWNLOAD_ADDRESS = ADDRESS + "/download/";

    //HOST
    public static String URL_HOST = "http://op.juhe.cn/";

}
