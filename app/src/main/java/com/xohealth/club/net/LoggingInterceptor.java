package com.xohealth.club.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Desc : 日志拦截器
 * Created by xulc on 2018/12/16.
 */
public class LoggingInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间

        StringBuilder sb = new StringBuilder();
        if (request.body() != null){
            if (request.body() instanceof FormBody){
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
            }else {
                sb.append(bodyToString(request));
            }
        }


        Log.i("xlc",String.format("发送请求:[%s] %n body:[%s] %n headers:%s",
                request.url(), sb.toString(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理


        ResponseBody responseBody = response.peekBody(1024 * 1024);

        Log.i("xlc",String.format("接收响应:[%s]%n返回json:[%s]%n时长:[%.1fms]%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()));

        return response;

    }

    private String bodyToString(Request request){

        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (Exception e) {
            return "did not work";
        }
    }
}
