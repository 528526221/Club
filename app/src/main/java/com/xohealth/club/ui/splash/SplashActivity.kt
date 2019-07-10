package com.xohealth.club.ui.splash

import android.content.Intent

import com.xohealth.club.R
import com.xohealth.club.base.BaseActivity
import com.xohealth.club.ui.home.SplashPresenter
import com.xohealth.club.ui.main.MainActivity
import com.xohealth.club.utils.WxApiUtils

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashPresenter>() {
    private var disposable: Disposable? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
        WxApiUtils.getInstance().regToWx(this)
        disposable = Observable.interval(0L, 1L, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe { aLong ->
            val offset = 3 - aLong
            if (offset <= 0) {
                //取消
                disposable?.dispose()
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                this@SplashActivity.finish()
            } else {
                tvCount.text = "倒计时${offset}秒"
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
