package com.sir.app.retrofit.config;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;

/**
 * 网络配置
 * Created by zhuyinan on 2017/3/28.
 */
public final class NetWorkConfiguration {

    private String TAG = "NetWorkConfiguration";

    /**
     * 默认缓存
     */
    private boolean isCache;
    //是否进行磁盘缓存
    private boolean isDiskCache;
    //是否进行内存缓存
    private boolean isMemoryCache;
    //内存缓存时间单位S （默认为60s）
    private int memoryCacheTime;
    //本地缓存时间单位S (默认为4周)
    private int diskCacheTime;
    //缓存本地大小 单位字节（默认为30M）
    private int maxDiskCacheSize;
    //缓存路径
    private Cache diskCache;
    //设置超时时间
    private int connectTimeout;
    //设置网络最大连接数
    private ConnectionPool connectionPool;
    //设置HttpS客户端带证书访问
    private InputStream[] certificates;
    //上下文
    private Context mContext;
    //设置网络BaseUrl地址
    private String baseUrl;
    //设置token
    private String authToken;

    public NetWorkConfiguration(Context content) {
        this.isCache = true;
        this.isDiskCache = true;
        this.isMemoryCache = true;
        this.memoryCacheTime = 60;
        this.diskCacheTime = 60 * 60 * 24 * 28;
        this.maxDiskCacheSize = 30 * 1024 * 1024;
        this.mContext = content.getApplicationContext();
        this.diskCache = new Cache(new File(this.mContext.getCacheDir(), "network"), maxDiskCacheSize);
        this.connectTimeout = 10000;
        this.connectionPool = new ConnectionPool(50, 60, TimeUnit.SECONDS);
        this.certificates = null;
        this.baseUrl = null;
        this.authToken = "";
    }

    /**
     * 设置是否进行缓存
     *
     * @param iscache
     * @return
     */
    public NetWorkConfiguration isCache(boolean iscache) {
        this.isCache = iscache;
        return this;
    }

    public boolean getIsCache() {
        return this.isCache;
    }

    /**
     * 是否进行磁盘缓存
     *
     * @param diskCache
     * @return
     */
    public NetWorkConfiguration isDiskCache(boolean diskCache) {
        this.isDiskCache = diskCache;
        return this;
    }

    public boolean getIsDiskCache() {
        return this.isDiskCache;
    }

    /**
     * 是否进行内存缓存
     *
     * @param memoryCache
     * @return
     */
    public NetWorkConfiguration isMemoryCache(boolean memoryCache) {
        this.isMemoryCache = memoryCache;
        return this;
    }

    public boolean getIsMemoryCache() {
        return this.isMemoryCache;
    }

    /**
     * 设置内存缓存时间
     * 用okhttp自带的缓存策略，因为这需要服务端配合处理缓存请求头不然会抛出：
     * HTTP 504 Unsatisfiable Request (only-if-cached)
     *
     * @param memoryCacheTime
     * @return
     */
    public NetWorkConfiguration memoryCacheTime(int memoryCacheTime) {
        if (memoryCacheTime <= 0) {
            Log.e(TAG, "configure memoryCacheTime  exception!");
            return this;
        }
        this.memoryCacheTime = memoryCacheTime;
        return this;
    }

    public int getMemoryCacheTime() {
        return this.memoryCacheTime;
    }

    /**
     * 设置本地缓存时间
     *
     * @param diskCacheTime
     * @return
     */
    public NetWorkConfiguration diskCacheTime(int diskCacheTime) {
        if (diskCacheTime <= 0) {
            Log.e(TAG, " configure diskCacheTime  exception!");
            return this;
        }
        this.diskCacheTime = diskCacheTime;
        return this;
    }

    public int getDiskCacheTime() {
        return this.diskCacheTime;
    }

    /**
     * 设置本地缓存路径以及 缓存大小
     *
     * @param saveFile         本地路径
     * @param maxDiskCacheSize 大小
     * @return
     */
    public NetWorkConfiguration diskCaChe(File saveFile, int maxDiskCacheSize) {
        if (!saveFile.exists() && maxDiskCacheSize <= 0) {
            Log.e(TAG, " configure connectTimeout  exception!");
            return this;
        }
        diskCache = new Cache(saveFile, maxDiskCacheSize);
        return this;
    }

    public Cache getDiskCache() {
        return this.diskCache;
    }

    /**
     * 设置网络超时时间
     *
     * @param timeout
     * @return
     */
    public NetWorkConfiguration connectTimeOut(int timeout) {
        if (connectTimeout <= 0) {
            Log.e(TAG, " configure connectTimeout  exception!");
            return this;
        }
        this.connectTimeout = timeout;
        return this;
    }

    public int getConnectTimeOut() {
        return this.connectTimeout;
    }

    /**
     * 设置网络线程池
     *
     * @param connectionCount 线程个数
     * @param connectionTime  连接时间
     * @param unit            时间单位
     * @return
     */
    public NetWorkConfiguration connectionPool(int connectionCount, int connectionTime, TimeUnit unit) {
        /**
         *    线程池 线程个数和连接时间设置过小
         */
        if (connectionCount <= 0 && connectionTime <= 0) {
            Log.e(TAG, " configure connectionPool  exception!");
            return this;
        }
        this.connectionPool = new ConnectionPool(connectionCount, connectionTime, unit);
        return this;
    }

    public ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    /**
     * 设置Https客户端带证书访问
     *
     * @param certificates
     * @return
     */
    public NetWorkConfiguration certificates(InputStream... certificates) {
        if (certificates != null) {
            this.certificates = certificates;
        } else {
            Log.e(TAG, "no  certificates");
        }
        return this;
    }

    public InputStream[] getCertificates() {
        return this.certificates;
    }

    /**
     * 设置网络BaseUrl地址
     *
     * @param url
     * @return
     */
    public NetWorkConfiguration baseUrl(String url) {
        if (url != null) {
            this.baseUrl = url;
        } else {
            Log.e(TAG, "NetWorkConfiguration no baseUrl");
        }
        return this;
    }

    /***
     * 设置token
     * @param authToken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public String getBaseUrl() {
        return this.baseUrl;
    }


    public String getAuthToken() {
        return authToken;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public String toString() {
        return "NetWorkConfiguration{" +
                "isCache=" + isCache +
                ", isDiskCache=" + isDiskCache +
                ", isMemoryCache=" + isMemoryCache +
                ", memoryCacheTime=" + memoryCacheTime +
                ", diskCacheTime=" + diskCacheTime +
                ", maxDiskCacheSize=" + maxDiskCacheSize +
                ", diskCache=" + diskCache +
                ", connectTimeout=" + connectTimeout +
                ", connectionPool=" + connectionPool +
                ", certificates=" + Arrays.toString(certificates) +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
