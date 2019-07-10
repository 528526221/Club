package com.xohealth.club.ui.main;


import android.text.TextUtils;

import com.xohealth.club.base.BaseObserver;
import com.xohealth.club.base.BasePresenter;
import com.xohealth.club.base.BaseResponse;
import com.xohealth.club.bean.UserData;
import com.xohealth.club.net.RetrofitManager;
import com.xohealth.club.utils.RxSchedulers;
import com.xohealth.club.utils.UserUtil;

import javax.inject.Inject;

/**
 * Created by xulc on 2018/11/17.
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter() {
    }

    @Override
    public void checkLogin() {
        //判断登录逻辑
        if(UserUtil.getInstance().isLogin()){
            String phone = UserUtil.getInstance().getUserInfo().getMobile();
            String password = UserUtil.getInstance().getUserInfo().getPassword();
            String wx_openid = UserUtil.getInstance().getUserInfo().getWx_openid();

            if (TextUtils.isEmpty(phone)){
                UserUtil.getInstance().clearUserLoginInfo();
                return;
            }

            if (!TextUtils.isEmpty(password)){
                //使用手机号和密码登录
                RetrofitManager.getApiService().login("v1",1,phone,password)
                        .compose(RxSchedulers.<BaseResponse<UserData>>applySchedulers())
                        .subscribe(new BaseObserver<UserData>(mDisposable) {
                            @Override
                            protected void onSuccess(BaseResponse<UserData> userDataBaseResponse) {
                                UserUtil.getInstance().setUserInfo(userDataBaseResponse.getData());
                            }

                            @Override
                            protected void onFail(BaseResponse<UserData> userDataBaseResponse) {
                            }

                        });
            }else if (!TextUtils.isEmpty(wx_openid)){
                //使用微信openId登录
                RetrofitManager.getApiService().login("v1",3,wx_openid,null)
                        .compose(RxSchedulers.<BaseResponse<UserData>>applySchedulers())
                        .subscribe(new BaseObserver<UserData>(mDisposable) {
                            @Override
                            protected void onSuccess(BaseResponse<UserData> userDataBaseResponse) {
                                UserUtil.getInstance().setUserInfo(userDataBaseResponse.getData());
                            }

                            @Override
                            protected void onFail(BaseResponse<UserData> userDataBaseResponse) {
                            }
                        });
            }else {
                UserUtil.getInstance().clearUserLoginInfo();
            }
        }
    }
}
