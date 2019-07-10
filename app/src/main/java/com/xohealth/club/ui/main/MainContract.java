package com.xohealth.club.ui.main;


import com.xohealth.club.base.BaseContract;

/**
 * Created by xulc on 2018/11/17.
 */
public interface MainContract {
    interface View extends BaseContract.BaseView{

    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void checkLogin();
    }
}
