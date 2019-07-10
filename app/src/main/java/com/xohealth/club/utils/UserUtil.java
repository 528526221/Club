package com.xohealth.club.utils;


import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.xohealth.club.bean.RefreshTokenData;
import com.xohealth.club.bean.UserData;
import com.xohealth.club.bean.UserLoginInfo;
import com.xohealth.club.bean.WxTokenResponse;
import com.xohealth.club.bean.WxUserInfoResponse;
import com.xohealth.club.net.Constant;


/**
 * Created by xulc on 2018/11/16.
 */

public class UserUtil {
    private UserLoginInfo loginInfo;
    private UserUtil(){

    }
    private static class UserUtilInstance{
        public static UserUtil instance = new UserUtil();
    }

    public static UserUtil getInstance(){
        return UserUtilInstance.instance;
    }


    /**
     * 获取本地用户信息
     * @return
     */
    public UserLoginInfo getUserInfo(){
        if (loginInfo == null){
            String s = SPUtils.getInstance().getString(Constant.LOGIN_USER_INFO);
            loginInfo = GsonUtil.parseJsonWithGson(s,UserLoginInfo.class);
        }
        if (loginInfo == null){
            loginInfo = new UserLoginInfo();
        }
        return loginInfo;
    }


    /**
     * 更新本地用户信息
     * @param loginInfo
     */
    public void setUserInfo(UserLoginInfo loginInfo){
        this.loginInfo = loginInfo;
        SPUtils.getInstance().put(Constant.LOGIN_USER_INFO,GsonUtil.beanToJson(loginInfo));
    }

    /**
     * 清除用户登录信息
     * 保留手机号 和 密码
     */
    public void clearUserLoginInfo(){
        loginInfo = getUserInfo();
        String mobile = loginInfo.getMobile();
        String password = loginInfo.getPassword();
        loginInfo = new UserLoginInfo();
        if (!TextUtils.isEmpty(mobile)){
            loginInfo.setMobile(mobile);
            loginInfo.setPassword(password);
        }

        SPUtils.getInstance().put(Constant.LOGIN_USER_INFO,GsonUtil.beanToJson(loginInfo));
    }

    /**
     * 保存登录接口的信息
     * @param userData
     */
    public void setUserInfo(UserData userData){
        loginInfo = getUserInfo();
        loginInfo.setNickname(userData.getNickname());
        loginInfo.setAvatarUrl(userData.getAvatarUrl());
        loginInfo.setMobile(userData.getMobile());
        loginInfo.setAccess_token(userData.getJwt().getAccess_token());
        loginInfo.setToken_type(userData.getJwt().getToken_type());
        loginInfo.setRefresh_token(userData.getJwt().getRefresh_token());
        if (!TextUtils.isEmpty(userData.getBirthday().trim())){
            loginInfo.setLogin(true);
        }else {
            loginInfo.setLogin(false);
        }
        UserUtil.getInstance().setUserInfo(loginInfo);
    }



    /**
     * 更新本地用户信息
     * @param mobile
     * @param password
     */
    public void setUserInfo(String mobile,String password){
        loginInfo = getUserInfo();
        loginInfo.setMobile(mobile);
        loginInfo.setPassword(password);

        SPUtils.getInstance().put(Constant.LOGIN_USER_INFO,GsonUtil.beanToJson(loginInfo));
    }

    /**
     * 更新Token信息
     * @param data
     */
    public void setRefreshToken(RefreshTokenData data){
        if (loginInfo == null){
            loginInfo = getUserInfo();
        }
        loginInfo.setAccess_token(data.getJwt().getAccess_token());
        loginInfo.setToken_type(data.getJwt().getToken_type());
        loginInfo.setRefresh_token(data.getJwt().getRefresh_token());
        SPUtils.getInstance().put(Constant.LOGIN_USER_INFO,GsonUtil.beanToJson(loginInfo));
    }

    /**
     * 保存微信Token信息
     * @param data
     */
    public void setWxRefreshToken(WxTokenResponse data){
        if (loginInfo == null){
            loginInfo = getUserInfo();
        }
        loginInfo.setWx_access_token(data.getAccess_token());
        loginInfo.setWx_refresh_token(data.getRefresh_token());
        loginInfo.setWx_openid(data.getOpenid());
        SPUtils.getInstance().put(Constant.LOGIN_USER_INFO,GsonUtil.beanToJson(loginInfo));
    }

    /**
     * 保存微信用户信息
     * @param data
     */
    public void setWxUserInfo(WxUserInfoResponse data){
        if (loginInfo == null){
            loginInfo = getUserInfo();
        }
        loginInfo.setWx_nickname(data.getNickname());
        loginInfo.setWx_headimgurl(data.getHeadimgurl());
        loginInfo.setWx_sex(data.getSex());
        loginInfo.setWx_country(data.getCountry());
        loginInfo.setWx_city(data.getCity());
        loginInfo.setWx_province(data.getProvince());

        SPUtils.getInstance().put(Constant.LOGIN_USER_INFO,GsonUtil.beanToJson(loginInfo));
    }

    /**
     * 判断是否登录
     * @return
     */
    public boolean isLogin(){
        if (loginInfo == null){
            loginInfo = getUserInfo();
        }
        if (loginInfo.isLogin()){
            return true;
        }
        return false;
    }

}
