package com.xohealth.club.base;

/**
 * Created by xulc on 2018/11/16.
 */
public interface BaseContract {
    interface BaseView{
        void showLoading(String message);
        void hideLoading();
    }

    interface BasePresenter<T extends BaseView>{
        void attachView(T view);

        void detachView();
    }
}
