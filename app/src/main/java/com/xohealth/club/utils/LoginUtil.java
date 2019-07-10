package com.xohealth.club.utils;

import com.blankj.utilcode.util.SPUtils;


/**
 * Created by xulc on 2018/11/16.
 */

public class LoginUtil {
    private LoginUtil(){

    }
    private static class LoginUtilInstance{
        public static LoginUtil instance = new LoginUtil();
    }

    public static LoginUtil getInstance(){
        return LoginUtilInstance.instance;
    }

    public void login(){


    }

}
