package com.xohealth.club.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xohealth.club.di.component.DaggerApplicationComponent;
import com.xohealth.club.di.component.FragmentComponent;
import com.xohealth.club.di.module.ApplicationModule;
import com.xohealth.club.widget.LoadingDialog;

import javax.inject.Inject;

/**
 * Created by xulc on 2018/11/16.
 */
public abstract class BaseLazyFragment<T extends BaseContract.BasePresenter> extends Fragment implements BaseContract.BaseView {
    private View mRootView;
    @Inject
    protected T mPresenter;
    protected FragmentComponent mFragmentComponent;
    private boolean isFirst = true;//fragment是否第一次可见
    protected boolean isViewCreated = false;
    protected LoadingDialog loadingDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(App.getAppContext())).build().buildFragmentComponent().build();
        initInjector();
        attachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirst = true;
        isViewCreated = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            lazyLoad();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (isFirst && isViewCreated){
            isFirst = false;
            initView(mRootView);
        }
    }

    @Override
    public void showLoading(String message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.setLoadingMessage(message);
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View mRootView);

    protected abstract void initInjector();

    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
