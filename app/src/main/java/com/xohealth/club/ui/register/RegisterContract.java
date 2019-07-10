package com.xohealth.club.ui.register;

import com.xohealth.club.base.BaseContract;

/**
 * Created by xulc on 2018/12/2.
 */
public interface RegisterContract {
    interface View extends BaseContract.BaseView{
        void registerSuccess();
        void setCountDown(Long countDown);
    }
    interface Presenter extends BaseContract.BasePresenter<RegisterContract.View>{
        void sendCode(String mobile);
        void register(String name,String password,String psd,String code);
    }


}
