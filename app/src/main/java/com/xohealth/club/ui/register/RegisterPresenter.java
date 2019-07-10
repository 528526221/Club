package com.xohealth.club.ui.register;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xohealth.club.base.BaseObserver;
import com.xohealth.club.base.BasePresenter;
import com.xohealth.club.base.BaseResponse;
import com.xohealth.club.bean.RegisterBody;
import com.xohealth.club.bean.UserLoginInfo;
import com.xohealth.club.net.RetrofitManager;
import com.xohealth.club.utils.RxSchedulers;
import com.xohealth.club.utils.UserUtil;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by xulc on 2018/12/2.
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter{
    private boolean isCountDowning = false;
    public static final int COUNT_DOWN_SECONDS = 60;


    @Inject
    public RegisterPresenter() {}

    @Override
    public void sendCode(String mobile) {

        if (isCountDowning){
            return;
        }

        if (TextUtils.isEmpty(mobile)){
            ToastUtils.showShort("请输入手机号");
            return;
        }

        UserUtil.getInstance().setUserInfo(mobile,null);

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

        RetrofitManager.getApiService().registerSms("v1",mobile,1)
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
    public void register(String mobile, String password,String psd,String code) {
        if (TextUtils.isEmpty(mobile)){
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtils.showShort("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(psd)){
            ToastUtils.showShort("请输入确认密码");
            return;
        }
        if (!password.equals(psd)){
            ToastUtils.showShort("两次密码不一致");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtils.showShort("请输入验证码");
            return;
        }
        mView.showLoading("正在注册...");
        UserUtil.getInstance().setUserInfo(mobile,password);
        RegisterBody body = new RegisterBody();
        body.setUsername(mobile);
        body.setPassword(password);
        body.setVerificationCode(code);
        RetrofitManager.getApiService().registerAccount("v1",body)
                .compose(RxSchedulers.<BaseResponse<String>>applySchedulers())
                .subscribe(new BaseObserver<String>(mDisposable) {
                    @Override
                    protected void onSuccess(BaseResponse<String> stringBaseResponse) {
                        ToastUtils.showShort(stringBaseResponse.getData());
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

}
