package com.xohealth.club.net;


import com.xohealth.club.base.App;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xulc on 2018/11/16.
 */

public class RetrofitManager {
    private static long CONNECT_TIMEOUT = 60L;//连接超时
    private static long READ_TIMEOUT = 10L;//读超时
    private static long WRITE_TIMEOUT = 10L;//写超时

    private static volatile OkHttpClient mOkHttpClient;
    private static ApiService apiService;
    /**
     * 获取OkHttpClient实例
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);//缓存文件大小10M
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(new TokenInterceptor())
                            .addInterceptor(new RewriteCacheControlInterceptor())
                            .addInterceptor(new LoggingInterceptor())
                            .cookieJar(new CookiesManager())
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }


    /**
     * 获取Service
     *
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.REQUEST_BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        return retrofit.create(clazz);
    }

    public static ApiService getApiService(){
        if (apiService == null){
            synchronized (RetrofitManager.class){
                if (apiService == null){
                    apiService = create(ApiService.class);
                }
            }
        }
        return apiService;
    }




}
