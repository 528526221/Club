package com.xohealth.club.bean;


/**
 * Date：2018/4/16
 * Desc：
 * Created by xuliangchun.
 */

public class UserData {
    private String nickname;
    private String avatarUrl;
    private String userId;
    private String mobile;
    private Gender gender;
    private String birthday;
    private int age;
    private String avatarKey;
    private Location location;
    private boolean isOperatingUser;
    private IdentityAuthStatus identityAuthStatus;
    private Jwt jwt;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isOperatingUser() {
        return isOperatingUser;
    }

    public void setOperatingUser(boolean operatingUser) {
        isOperatingUser = operatingUser;
    }

    public IdentityAuthStatus getIdentityAuthStatus() {
        return identityAuthStatus;
    }

    public void setIdentityAuthStatus(IdentityAuthStatus identityAuthStatus) {
        this.identityAuthStatus = identityAuthStatus;
    }

    public Jwt getJwt() {
        return jwt == null ? new Jwt() : jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarKey() {
        return avatarKey;
    }

    public void setAvatarKey(String avatarKey) {
        this.avatarKey = avatarKey;
    }
}
