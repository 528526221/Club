package com.xohealth.club.ui.login;

import android.text.TextUtils;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.xohealth.club.base.BaseObserver;
import com.xohealth.club.base.BasePresenter;
import com.xohealth.club.base.BaseResponse;
import com.xohealth.club.bean.UserData;
import com.xohealth.club.net.RetrofitManager;
import com.xohealth.club.utils.RxSchedulers;

import javax.inject.Inject;

/**
 * Created by xulc on 2018/12/1.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Inject
    public LoginPresenter() {
    }

    @Override
    public void login(int auth_type,EditText etPhone, EditText etPassword) {

        String mobile = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(mobile)){
            etPhone.requestFocus();
            etPhone.setError("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)){
            etPassword.requestFocus();
            etPassword.setError("请输入密码");
            return;
        }
        mView.showLoading("正在登录...");
        RetrofitManager.getApiService().login("v1",auth_type,mobile,password)
                .compose(RxSchedulers.<BaseResponse<UserData>>applySchedulers())
                .subscribe(new BaseObserver<UserData>(mDisposable) {
                    @Override
                    protected void onSuccess(BaseResponse<UserData> userDataBaseResponse) {
                        mView.loginSuccess(userDataBaseResponse.getData());
                    }

                    @Override
                    protected void onFail(BaseResponse<UserData> userDataBaseResponse) {
                        ToastUtils.showShort(userDataBaseResponse.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mView.hideLoading();
                    }
                });
    }
}
