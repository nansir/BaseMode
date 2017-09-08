package com.sir.app.retrofit;

import android.content.Context;
import android.util.Log;

import com.sir.app.retrofit.config.NetWorkConfiguration;
import com.sir.app.retrofit.cookie.SimpleCookieJar;
import com.sir.app.retrofit.interceptor.LogInterceptor;
import com.sir.app.retrofit.request.RetrofitClient;
import com.sir.app.retrofit.utils.NetworkUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zhuyinan on 2017/3/28.
 */
public class HttpUtils {

    public static final String TAG = "HttpUtils";

    //获得HttpUtils实例
    private static HttpUtils mInstance;
    //OkHttpClient对象
    private OkHttpClient mOkHttpClient;
    // 网络配置
    private static NetWorkConfiguration configuration;

    /**
     * 是否加载本地缓存数据
     * 默认为TRUE
     */
    private boolean isLoadDiskCache = true;

    /**
     * 针对无网络情况
     * 是否加载本地缓存数据
     *
     * @param isCache true为加载 false不进行加载
     * @return
     */
    public HttpUtils setLoadDiskCache(boolean isCache) {
        this.isLoadDiskCache = isCache;
        return this;
    }

    /**
     * 针对有网络情况
     * 是否加载内存缓存数据
     * 默认为False
     */
    private boolean isLoadMemoryCache = false;

    /**
     * 是否加载内存缓存数据
     * 针对有网络情况
     *
     * @param isCache true为加载 false不进行加载
     *                用okhttp自带的缓存策略，因为这需要服务端配合处理缓存请求头
     *                不然会抛出： HTTP 504 Unsatisfiable Request (only-if-cached)
     * @return
     */
    public HttpUtils setLoadMemoryCache(boolean isCache) {
        this.isLoadMemoryCache = isCache;
        return this;
    }


    /**
     * 设置Token
     *
     * @param authToken
     * @return
     */
    public HttpUtils setAuthToken(String authToken) {
        configuration.setAuthToken(authToken);
        return this;
    }


    /**
     * 进行默认配置
     * 未配置configuration
     */
    public HttpUtils(Context context) {
        //创建默认 okHttpClient对象
        if (configuration == null) {
            configuration = new NetWorkConfiguration(context);
        }
        if (configuration.getIsCache()) {
            mOkHttpClient = new OkHttpClient.Builder()
                    //网络缓存拦截器
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(interceptor)
                    //自定义网络Log显示
                    .addInterceptor(new LogInterceptor())
                    .cache(configuration.getDiskCache())
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();
        } else {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LogInterceptor())
                    .addNetworkInterceptor(interceptor)
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();

        }
        /**
         *
         *  判断是否在AppLication中配置Https证书
         *
         */
        if (configuration.getCertificates() != null) {
            mOkHttpClient = getOkHttpClient().newBuilder()
                    .sslSocketFactory(HttpsUtils.getSslSocketFactory(configuration.getCertificates(), null, null))
                    .build();
        }
    }

    /**
     * 设置网络配置参数
     *
     * @param configuration
     */
    public static void setConFiguration(NetWorkConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("ImageLoader configuration can not be initialized with null");
        } else {
            if (HttpUtils.configuration == null) {
                Log.d(TAG, "Initialize NetWorkConfiguration with configuration");
                HttpUtils.configuration = configuration;
            } else {
                Log.e(TAG, "Try to initialize NetWorkConfiguration which had already been initialized before. To re-init NetWorkConfiguration with new configuration ");
            }
        }
        if (configuration != null) {
            Log.i(TAG, "ConFiguration" + configuration.toString());
        }
    }

    public RetrofitClient getRetorfitClinet() {
        Log.i(TAG, "configuration:" + configuration.toString());
        return new RetrofitClient(configuration.getBaseUrl(), mOkHttpClient);
    }

    public RetrofitClient getRetorfitClinet(OkHttpClient client) {
        Log.i(TAG, "configuration:" + configuration.toString());
        return new RetrofitClient(configuration.getBaseUrl(), client);
    }

    /**
     * 设置HTTPS客户端带证书访问
     *
     * @param certificates 本地证书
     */
    public HttpUtils setCertificates(InputStream... certificates) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null))
                .build();
        return this;
    }

    /**
     * 设置是否打印网络日志
     *
     * @param flag
     */
    public HttpUtils setDBugLog(boolean flag) {
        if (flag) {
            mOkHttpClient = getOkHttpClient().newBuilder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return this;
    }

    /**
     * 设置Coolie
     *
     * @return
     */
    public HttpUtils addCookie() {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .cookieJar(new SimpleCookieJar())
                .build();
        return this;
    }

    /**
     * 获得OkHttpClient实例
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 网络拦截器
     * 进行网络操作的时候进行拦截
     */
    final Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // 断网后是否加载本地缓存数据
            if (!NetworkUtil.isNetworkAvailable(configuration.getContext()) && isLoadDiskCache) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            } else if (isLoadMemoryCache) {//加载内存缓存数据
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            } else {//加载网络数据
                request = request.newBuilder()
                        .addHeader("Authorization", configuration.getAuthToken())
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            }

            Response response = chain.proceed(request);
            //有网进行内存缓存数据
            if (NetworkUtil.isNetworkAvailable(configuration.getContext()) && configuration.getIsMemoryCache()) {
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + configuration.getmemoryCacheTime())
                        .removeHeader("Pragma")
                        .build();
            } else {//进行本地缓存数据
                if (configuration.getIsDiskCache()) {
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + configuration.getDiskCacheTime())
                            .removeHeader("Pragma")
                            .build();
                }
            }
            return response;
        }
    };

    /**
     * 获取请求网络实例
     *
     * @return
     */
    public static HttpUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils(context);
                }
            }
        }
        return mInstance;
    }
}
