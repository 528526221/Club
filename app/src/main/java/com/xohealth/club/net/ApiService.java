package com.xohealth.club.net;


import com.xohealth.club.base.BaseResponse;
import com.xohealth.club.bean.CurUserBody;
import com.xohealth.club.bean.RefreshTokenData;
import com.xohealth.club.bean.RegisterBody;
import com.xohealth.club.bean.UserData;
import com.xohealth.club.bean.WxChatRegisterBody;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by xulc on 2018/11/16.
 */

public interface ApiService {



    /**
     * 用户刷新token
     * @param version
     * @return
     */
    @GET("/sh/user/free/api/{version}/token/refresh")
    Call<BaseResponse<RefreshTokenData>> refreshToken(@Path("version") String version, @Query("refresh_token") String refresh_token);

    /**
     * 注册
     * @param version
     * @param body
     * @return
     */
    @POST("/sh/user/free/api/{version}/users")
    Observable<BaseResponse<String>> registerAccount(@Path("version") String version, @Body RegisterBody body);

    /**
     *获取手机号是否已经注册
     * @param version
     * @param mobile
     * @return
     */
    @GET("/sh/user/free/api/{version}/users/exists")
    Observable<BaseResponse<String>> registerSms(@Path("version") String version, @Query("mobile") String mobile);

    /**
     * 发送注册验证码
     * @param version
     * @param mobile
     * @return
     */
    @GET("/sh/user/free/api/{version}/send/register/sms")
    Observable<BaseResponse<String>> registerSms(@Path("version") String version, @Query("mobile") String mobile ,@Query("templateId") int templateId);


    /**
     * 登录
     * @param version
     * @param auth_type 登录类型(手机号+密码登录(1)和微信登录(3))
     * @param username
     * @param password
     * @return
     */
    @GET("/sh/user/free/api/{version}/login")
    Observable<BaseResponse<UserData>> login(@Path("version") String version, @Query("auth_type") int auth_type, @Query("username") String username, @Query("password") String password);


    /**
     * 获取当前用户
     * @param version
     * @return
     */
    @GET("/sh/user/api/{version}/curUser")
    Observable<BaseResponse<String>> getCurUser(@Path("version") String version);


    /**
     * 变更用户信息
     * @param version
     * @param body
     * @return
     */
    @PUT("/sh/user/api/{version}/curUser")
    Observable<BaseResponse<String>> curUser(@Path("version") String version,@Body CurUserBody body);


    /**
     *获取七牛云上传凭证
     * @return
     */
    @GET("/basic/free/api/v1/qiniu/uploadToken")
    Observable<BaseResponse<String>> getQiniuToken();


    /**
     * 发送微信注册绑定手机号码的短信验证码
     * @param version
     * @param mobile
     * @return
     */
    @GET("/sh/user/free/api/{version}/send/wechat/register/sms")
    Observable<BaseResponse<String>> registerWxSms(@Path("version") String version, @Query("mobile") String mobile);


    /**
     *两性健康用户(微信)注册
     * @param version
     * @param body
     * @return
     */
    @POST("/sh/user/free/api/{version}/users/via/wechat")
    Observable<BaseResponse<String>> registerWxAccount(@Path("version") String version, @Body WxChatRegisterBody body);
}






