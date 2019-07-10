package com.xohealth.club.net;

import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Desc : 云端响应头拦截器，用来配置缓存策略
 * Created by xulc on 2018/12/16.
 */
public class RewriteCacheControlInterceptor implements Interceptor{

    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;//缓存记录有效期1天
    private static final int CACHE_NET_SEC = 10;//在10s内的GET请求不会重复向网络提交，会优先从缓存中读取

    @Override
    public Response intercept(Chain chain) throws IOException {

        //http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html
        //为了缓存响应，你需要一个你可以读写的缓存目录，和缓存大小的限制。这个缓存目录应该是私有的，不信任的程序应不能读取缓存内容。
        //响应缓存使用HTTP头作为配置。你可以在请求头中添加Cache-Control: max-stale=3600 ,OkHttp缓存会支持。你的服务通过响应头确定响应缓存多长时间，例如使用Cache-Control: max-age=9600。

        Request request = chain.request();
        if (NetworkUtils.isConnected()){
            request = request.newBuilder()
                    .cacheControl(new CacheControl.Builder().maxStale(CACHE_NET_SEC, TimeUnit.SECONDS).build())
//                        .addHeader("Cache-Control","max-stale=10")
                    .build();
            Response response = chain.proceed(request);

            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control","public, max-age=" + CACHE_NET_SEC)
                    .build();
        }else {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);

            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control","public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .build();
        }


    }
}
