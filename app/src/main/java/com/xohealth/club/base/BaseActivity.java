package com.xohealth.club.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xohealth.club.R;
import com.xohealth.club.di.component.ActivityComponent;
import com.xohealth.club.di.component.DaggerApplicationComponent;
import com.xohealth.club.di.module.ApplicationModule;
import com.xohealth.club.utils.StatusBarUtils;
import com.xohealth.club.widget.LoadingDialog;

import javax.inject.Inject;

/**
 * Created by xulc on 2018/11/16.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseContract.BaseView {
    @Inject
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        if (StatusBarUtils.isTranslucentStatus()){
            StatusBarUtils.setStatusBarTrans(getWindow());
            StatusBarUtils.setStatusTextColorWhite(getWindow());
        }
        //一个component依赖另一个component的方法有两种，一种通过Subcomponent,一种通过dependencies
        mActivityComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(App.getAppContext())).build().buildActivityComponent().build();

        initInjector();

        attachView();

        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachView();
    }


    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();

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

    @Override
    public void showLoading(String message) {
        if (loadingDialog == null){
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.setLoadingMessage(message);
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    public void finishBack(View view) {
        finish();
    }

}