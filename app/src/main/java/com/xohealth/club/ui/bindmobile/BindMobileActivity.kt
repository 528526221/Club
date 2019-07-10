package com.xohealth.club.ui.bindmobile


import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.xohealth.club.R
import com.xohealth.club.base.BaseActivity
import com.xohealth.club.bean.UserData
import com.xohealth.club.bean.UserLoginInfo
import com.xohealth.club.ui.completinginfo.CompletingInfoActivity
import com.xohealth.club.ui.main.MainActivity
import com.xohealth.club.utils.UserUtil
import kotlinx.android.synthetic.main.activity_bind_mobile.*


/**
 * Created by xulc on 2018/12/2.
 */
class BindMobileActivity : BaseActivity<BindMobilePresenter>(),BindMobileContract.View, View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_bind_mobile
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
        Glide.with(this).load(UserUtil.getInstance().userInfo.wx_headimgurl).into(ivHeader)
        ivNext?.setOnClickListener(this)
        tvGetCode?.setOnClickListener(this)
    }

    override fun loginSuccess(userData: UserData) {
        UserUtil.getInstance().setUserInfo(userData)
        if (!UserUtil.getInstance().isLogin){
            startActivity(Intent(this, CompletingInfoActivity::class.java))
        }
        finish()
    }

    override fun registerSuccess() {
        mPresenter.login(UserUtil.getInstance().userInfo.wx_openid)
    }

    override fun setCountDown(countDown: Long?) {
        if (countDown?.toInt() == 0){
            tvGetCode?.text = "获取验证码"
        }else{
            tvGetCode?.text = countDown.toString()
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            ivNext -> {
                mPresenter?.next(etPhone,etCode)
            }
            tvGetCode -> {
                mPresenter?.sendCode(etPhone)
            }
        }
    }
}
