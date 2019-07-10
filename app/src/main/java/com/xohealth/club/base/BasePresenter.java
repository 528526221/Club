package com.xohealth.club.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by xulc on 2018/11/16.
 */
public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T>{

    protected T mView;
    protected CompositeDisposable mDisposable;

    @Override
    public void attachView(T view) {
        this.mView = view;
        if (mDisposable == null){
            mDisposable = new CompositeDisposable();
        }
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
        if (mDisposable != null){
            mDisposable.clear();
            mDisposable = null;
        }
    }
}
