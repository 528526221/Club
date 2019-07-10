package com.xohealth.club.net;

import android.text.TextUtils;
import android.util.Log;

import com.xohealth.club.base.BaseResponse;
import com.xohealth.club.bean.RefreshTokenData;
import com.xohealth.club.utils.GsonUtil;
import com.xohealth.club.utils.UserUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Desc : Token拦截器
 * Created by xulc on 2018/12/16.
 */
public class TokenInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //登录后需要带上token
        if (!TextUtils.isEmpty(UserUtil.getInstance().getUserInfo().getAppToken())){
            request = request.newBuilder().addHeader("Authorization",UserUtil.getInstance().getUserInfo().getAppToken()).build();
        }

        Response originalResponse = chain.proceed(request);

        ResponseBody responseBody = originalResponse.peekBody(1024 * 1024);
        BaseResponse baseResponse = GsonUtil.parseJsonWithGson(responseBody.string(), BaseResponse.class);
        if (UserUtil.getInstance().isLogin() && baseResponse.getStatusCode() == 401){
            //token需要重新获取
            //取出本地的refreshToken
            String refreshToken = UserUtil.getInstance().getUserInfo().getRefresh_token();
            Call<BaseResponse<RefreshTokenData>> call = RetrofitManager.getApiService().refreshToken("v1",refreshToken);
            BaseResponse<RefreshTokenData> refreshTokenResponse = call.execute().body();
            if (refreshTokenResponse != null && refreshTokenResponse.isStatus() && refreshTokenResponse.getData() != null){
                //重新设置Token
                UserUtil.getInstance().setRefreshToken(refreshTokenResponse.getData());
                return chain.proceed(originalResponse.request().newBuilder()
                        .header("Authorization", UserUtil.getInstance().getUserInfo().getAppToken())
                        .build());
            }else {
                Log.i(Constant.TAG,"太久没登陆了:)");
            }

        }


        return originalResponse;
    }
}
