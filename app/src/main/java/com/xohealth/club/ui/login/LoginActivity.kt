package com.xohealth.club.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View

import com.xohealth.club.R
import com.xohealth.club.base.BaseActivity
import com.xohealth.club.base.BaseObserver
import com.xohealth.club.base.BaseResponse
import com.xohealth.club.bean.RxLoginEvent
import com.xohealth.club.bean.UserData
import com.xohealth.club.bean.WxTokenResponse
import com.xohealth.club.bean.WxUserInfoResponse
import com.xohealth.club.net.Constant
import com.xohealth.club.net.RetrofitManager
import com.xohealth.club.ui.bindmobile.BindMobileActivity
import com.xohealth.club.ui.completinginfo.CompletingInfoActivity
import com.xohealth.club.ui.register.RegisterActivity
import com.xohealth.club.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by xulc on 2018/12/1.
 */
class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View, View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {

        tvRegister!!.setOnClickListener(this)
        ivLogin!!.setOnClickListener(this)
        ivHidden?.setOnClickListener(this)
        ivWechat?.setOnClickListener(this)
        UserUtil.getInstance().clearUserLoginInfo()
        RxBus.getInstance().post(RxLoginEvent("已经退出"))

        setDefaultValue()
    }

    private fun setDefaultValue() {
        val loginInfo = UserUtil.getInstance().userInfo
        etPhone?.setText(loginInfo?.mobile)
        etPhone?.setSelection(etPhone.text.length)
        etPassword?.setText(loginInfo?.password)
        etPassword?.setSelection(etPassword.text.length)

    }

    override fun onClick(view: View) {
        when (view) {
            tvRegister -> startActivity(Intent(this, RegisterActivity::class.java))
            ivLogin -> {
                mPresenter.login(1, etPhone, etPassword)
            }
            ivHidden -> {
                if (ivHidden.isSelected) {
                    ivHidden.isSelected = false
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    ivHidden.isSelected = true
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
                etPassword.setSelection(etPassword.text.length)
            }
            ivWechat -> {
                WxApiUtils.getInstance().wxLogin(this)

            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setDefaultValue()
        val code = intent?.getStringExtra("code")
        if (!TextUtils.isEmpty(code)) {
            getAccessTokenByCode(code!!)
        }
    }

    override fun loginSuccess(userData: UserData) {
        UserUtil.getInstance().setUserInfo(userData)
        if (UserUtil.getInstance().isLogin){
            finishWithResult()
        }else{
            startActivity(Intent(this@LoginActivity, CompletingInfoActivity::class.java))
            finish()
        }
    }

    private fun getAccessTokenByCode(code: String) {
        showLoading("获取微信授权信息...")
        val okHttpClient = OkHttpClient.Builder().build()

        val url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", Constant.APP_ID, Constant.WX_SECRET, code)

        val request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("xlc", "result:" + e.message)
                hideLoadingInThread()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body()!!.string()
                Log.i("xlc", "result:" + jsonString)
                val tokenResponse = GsonUtil.parseJsonWithGson(jsonString, WxTokenResponse::class.java)
                UserUtil.getInstance().setWxRefreshToken(tokenResponse)
                //第三步：通过access_token调用接口
                getWxUserInfo(tokenResponse.access_token, tokenResponse.openid)
            }
        })
    }

    private fun hideLoadingInThread() {
        Handler().post {
            hideLoading()
        }
    }

    private fun getWxUserInfo(access_token: String, openid: String) {
        val okHttpClient = OkHttpClient.Builder().build()

        val url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s", access_token, openid)

        val request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("xlc", "result:" + e.message)
                hideLoadingInThread()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body()!!.string()
                Log.i("xlc", "result:" + jsonString)
                val tokenResponse = GsonUtil.parseJsonWithGson(jsonString, WxUserInfoResponse::class.java)
                UserUtil.getInstance().setWxUserInfo(tokenResponse)

                //使用openId登录看看 失败就去绑定手机号
                loginWithWxOpenId(tokenResponse.openid)
            }
        })
    }

    private fun loginWithWxOpenId(openId: String) {
        RetrofitManager.getApiService().login("v1", 3, openId, null).compose(RxSchedulers.applySchedulers<BaseResponse<UserData>>()).subscribe(object : BaseObserver<UserData>(null) {
            override fun onSuccess(userDataBaseResponse: BaseResponse<UserData>) {
                loginSuccess(userDataBaseResponse.data)

            }

            override fun onFail(userDataBaseResponse: BaseResponse<UserData>) {
//                ToastUtils.showShort(userDataBaseResponse.message)
                startActivity(Intent(this@LoginActivity,BindMobileActivity::class.java))
            }

            override fun onComplete() {
                super.onComplete()
                hideLoading()
            }
        })
    }

    private fun finishWithResult(){
        setResult(Activity.RESULT_OK)
        finish()
    }

}
