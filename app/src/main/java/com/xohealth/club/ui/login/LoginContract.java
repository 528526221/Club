package com.xohealth.club.ui.login;

import android.widget.EditText;

import com.xohealth.club.base.BaseContract;
import com.xohealth.club.bean.UserData;

/**
 * Created by xulc on 2018/12/1.
 */
public interface LoginContract {
    interface View extends BaseContract.BaseView{
        void loginSuccess(UserData userData);
    }

    interface Presenter extends BaseContract.BasePresenter<LoginContract.View>{
        void login(int auth_type, EditText etPhone, EditText etPassword);
    }
}
