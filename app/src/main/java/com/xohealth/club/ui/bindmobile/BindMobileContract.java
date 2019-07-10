package com.xohealth.club.ui.bindmobile;

import android.widget.EditText;

import com.xohealth.club.base.BaseContract;
import com.xohealth.club.bean.UserData;

/**
 * Created by xulc on 2018/12/2.
 */
public interface BindMobileContract {
    interface View extends BaseContract.BaseView{
        void setCountDown(Long countDown);
        void registerSuccess();
        void loginSuccess(UserData userData);

    }

    interface Presenter extends BaseContract.BasePresenter<BindMobileContract.View>{
        void next(EditText etPhone, EditText etCode);
        void sendCode(EditText etPhone);
        void login(String openId);
    }
}
