package com.xohealth.club.ui.bindmobile;

import android.text.TextUtils;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.xohealth.club.base.BaseObserver;
import com.xohealth.club.base.BasePresenter;
import com.xohealth.club.base.BaseResponse;
import com.xohealth.club.bean.UserData;
import com.xohealth.club.bean.WxChatRegisterBody;
import com.xohealth.club.net.RetrofitManager;
import com.xohealth.club.utils.RxSchedulers;
import com.xohealth.club.utils.UserUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by xulc on 2018/12/2.
 */
public class BindMobilePresenter extends BasePresenter<BindMobileContract.View> implements BindMobileContract.Presenter {
    private boolean isCountDowning = false;
    public static final int COUNT_DOWN_SECONDS = 60;
    @Inject
    public BindMobilePresenter() {
    }

    @Override
    public void next(EditText etPhone, EditText etCode) {
        String phone = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();

        if (TextUtils.isEmpty(phone)){
            etPhone.requestFocus();
            etPhone.setError("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code)){
            etCode.requestFocus();
            etCode.setError("请输入验证码");
            return;
        }
        mView.showLoading(null);
        WxChatRegisterBody body = new WxChatRegisterBody();
        body.setUsername(phone);
        body.setVerificationCode(code);
        body.setWechatOpenId(UserUtil.getInstance().getUserInfo().getWx_openid());
        RetrofitManager.getApiService().registerWxAccount("v1",body)
                .compose(RxSchedulers.<BaseResponse<String>>applySchedulers())
                .subscribe(new BaseObserver<String>(mDisposable) {
                    @Override
                    protected void onSuccess(BaseResponse<String> stringBaseResponse) {
                        mView.registerSuccess();
                    }

                    @Override
                    protected void onFail(BaseResponse<String> stringBaseResponse) {
                        ToastUtils.showShort(stringBaseResponse.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mView.hideLoading();
                    }
                });



    }

    @Override
    public void sendCode(EditText etPhone) {

        if (isCountDowning){
            return;
        }
        String phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(phone)){
            etPhone.requestFocus();
            etPhone.setError("请输入手机号");
            return;
        }


        final int count = COUNT_DOWN_SECONDS;//倒计时60秒
        isCountDowning = true;
        Observable.interval(0, 1, TimeUnit.SECONDS)//延迟0，间隔1s，单位秒
                .take(count + 1)//限制发射次数（因为倒计时要显示 3 2 1 0 四个数字）
                //使用map将数字转换，这里aLong是从0开始增长的,所以减去aLong就会输出3 2 1 0这种样式
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .compose(RxSchedulers.<Long>applySchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long num) {
                        mView.setCountDown(num);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        isCountDowning = false;
                    }
                });

        RetrofitManager.getApiService().registerWxSms("v1",phone)
                .compose(RxSchedulers.<BaseResponse<String>>applySchedulers())
                .subscribe(new BaseObserver<String>(mDisposable) {
                    @Override
                    protected void onSuccess(BaseResponse<String> stringBaseResponse) {
                        ToastUtils.showShort(stringBaseResponse.getData());
                    }

                    @Override
                    protected void onFail(BaseResponse<String> stringBaseResponse) {
                        ToastUtils.showShort(stringBaseResponse.getMessage());

                    }
                });
    }

    @Override
    public void login(String openId) {
        mView.showLoading(null);
        RetrofitManager.getApiService().login("v1",3,openId,null)
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
