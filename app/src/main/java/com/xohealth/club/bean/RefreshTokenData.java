package com.xohealth.club.bean;

/**
 * Desc : 刷新Token
 * Created by xulc on 2018/12/16.
 */
public class RefreshTokenData {
    private Jwt jwt;
    private String mobile;
    private String personalSignature;
    private String userId;

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
