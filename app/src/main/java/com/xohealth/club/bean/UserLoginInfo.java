package com.xohealth.club.bean;

import android.text.TextUtils;

/**
 * Desc : 用户登录相关信息
 * Created by xulc on 2018/12/9.
 */
public class UserLoginInfo {
    private String nickname;
    private String avatarUrl;
    private String mobile;
    private String password;
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String qiniu_token;
    private boolean login;

    private String wx_access_token;
    private String wx_refresh_token;
    private String wx_openid;
    private String wx_nickname;
    private int wx_sex;
    private String wx_headimgurl;

    private String wx_country;
    private String wx_city;
    private String wx_province;


    public String getNickname() {
        return TextUtils.isEmpty(nickname)? getWx_nickname():nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return TextUtils.isEmpty(avatarUrl)? getWx_headimgurl():avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    //为了拼接
    public String getAppToken(){
        return token_type + " " + access_token;
    }


    public String getQiniu_token() {
        return qiniu_token;
    }

    public void setQiniu_token(String qiniu_token) {
        this.qiniu_token = qiniu_token;
    }

    public String getWx_access_token() {
        return wx_access_token;
    }

    public void setWx_access_token(String wx_access_token) {
        this.wx_access_token = wx_access_token;
    }

    public String getWx_refresh_token() {
        return wx_refresh_token;
    }

    public void setWx_refresh_token(String wx_refresh_token) {
        this.wx_refresh_token = wx_refresh_token;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
    }

    public String getWx_nickname() {
        return wx_nickname;
    }

    public void setWx_nickname(String wx_nickname) {
        this.wx_nickname = wx_nickname;
    }

    public int getWx_sex() {
        return wx_sex;
    }

    public void setWx_sex(int wx_sex) {
        this.wx_sex = wx_sex;
    }

    public String getWx_headimgurl() {
        return wx_headimgurl;
    }

    public void setWx_headimgurl(String wx_headimgurl) {
        this.wx_headimgurl = wx_headimgurl;
    }

    public String getWx_country() {
        return wx_country;
    }

    public void setWx_country(String wx_country) {
        this.wx_country = wx_country;
    }

    public String getWx_city() {
        return wx_city;
    }

    public void setWx_city(String wx_city) {
        this.wx_city = wx_city;
    }

    public String getWx_province() {
        return wx_province;
    }

    public void setWx_province(String wx_province) {
        this.wx_province = wx_province;
    }
}
