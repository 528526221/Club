package com.xohealth.club.net;

import android.support.annotation.Nullable;

import com.xohealth.club.base.BaseResponse;
import com.xohealth.club.bean.RefreshTokenData;
import com.xohealth.club.utils.UserUtil;

import java.io.IOException;


import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

/**
 * Desc : 授权状态失效处理 这个必须要服务器返回401才可用 在本项目中不适用
 * Created by xulc on 2018/12/16.
 */
public class TokenAuthenticator implements Authenticator {

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        //取出本地的refreshToken
        String refreshToken = UserUtil.getInstance().getUserInfo().getRefresh_token();
        Call<BaseResponse<RefreshTokenData>> call = RetrofitManager.getApiService().refreshToken("v1",refreshToken);
        BaseResponse<RefreshTokenData> response1 = call.execute().body();
        return response.request().newBuilder()
                .header("Authorization", response1.getData().getJwt().getAccess_token())
                .build();

    }
}
