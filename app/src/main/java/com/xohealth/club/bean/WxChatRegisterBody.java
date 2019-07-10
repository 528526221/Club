package com.xohealth.club.bean;

/**
 * Desc :
 * Created by xulc on 2018/12/21.
 */
public class WxChatRegisterBody {
    private String username;
    private String verificationCode;
    private String wechatOpenId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }
}
