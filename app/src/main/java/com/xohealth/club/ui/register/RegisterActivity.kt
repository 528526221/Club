package com.xohealth.club.ui.register


import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View

import com.xohealth.club.R
import com.xohealth.club.base.BaseActivity
import com.xohealth.club.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by xulc on 2018/12/1.
 */
class RegisterActivity : BaseActivity<RegisterPresenter>(),RegisterContract.View, View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {

        ivBack?.setOnClickListener(this)
        ivRegister?.setOnClickListener(this)
        ivHidden?.setOnClickListener(this)
        ivHiddenConfirm?.setOnClickListener(this)
        tvGetCode?.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view) {
            ivBack -> finish()
            ivRegister -> {
                mPresenter.register(etPhone?.text.toString(), etPassword?.text.toString(), etPasswordConfirm?.text.toString(),etCode?.text.toString())
            }
            ivHidden -> {
                if (ivHidden.isSelected){
                    ivHidden.isSelected = false
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }else{
                    ivHidden.isSelected = true
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
                etPassword.setSelection(etPassword.text.length)

            }
            ivHiddenConfirm -> {
                if (ivHiddenConfirm.isSelected){
                    ivHiddenConfirm.isSelected = false
                    etPasswordConfirm.transformationMethod = PasswordTransformationMethod.getInstance()
                }else{
                    ivHiddenConfirm.isSelected = true
                    etPasswordConfirm.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
                etPasswordConfirm.setSelection(etPasswordConfirm.text.length)
            }
            tvGetCode -> {
                mPresenter.sendCode(etPhone?.text.toString())
            }
        }
    }
    override fun registerSuccess() {
        startActivity(Intent(this,LoginActivity::class.java))
    }


    override fun setCountDown(countDown: Long?) {
        if (countDown?.toInt() == 0){
            tvGetCode?.text = "获取验证码"
        }else{
            tvGetCode?.text = countDown.toString()
        }
    }
}


