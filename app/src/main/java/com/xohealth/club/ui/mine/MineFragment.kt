package com.xohealth.club.ui.mine


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import com.bumptech.glide.Glide
import com.xohealth.club.R
import com.xohealth.club.base.BaseLazyFragment
import com.xohealth.club.bean.RxLoginEvent
import com.xohealth.club.net.Constant
import com.xohealth.club.ui.home.MinePresenter
import com.xohealth.club.ui.login.LoginActivity
import com.xohealth.club.utils.RxBus
import com.xohealth.club.utils.UserUtil

import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : BaseLazyFragment<MinePresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            ivSetup -> {
                val localBuilder = AlertDialog.Builder(context)
                localBuilder.setTitle("提示")
                localBuilder.setIcon(R.mipmap.ic_launcher)
                localBuilder.setMessage("退出吗？")
                localBuilder.setPositiveButton("yes"
                ) { paramAnonymousDialogInterface, paramAnonymousInt ->
                    startActivity(Intent(context,LoginActivity::class.java))
                }
                localBuilder.setNegativeButton("no"
                ) { paramAnonymousDialogInterface, paramAnonymousInt -> }

                /***
                 * 设置点击返回键不会消失
                 * */
                localBuilder.create()

                localBuilder.show()

            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(mRootView: View) {
        ivSetup?.setOnClickListener(this)

    }

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.getInstance().addSubscribe(this, RxLoginEvent::class.java) { Log.i(Constant.TAG, "登录状态改变") }
    }

    override fun onResume() {
        super.onResume()
        if (isViewCreated && userVisibleHint){
            checkLoginStatus()
        }
    }

    private fun checkLoginStatus() {

        //检查登录状态
        Log.i(Constant.TAG, "检查登录状态")
        if (UserUtil.getInstance().isLogin) {
            Glide.with(this).load(UserUtil.getInstance().userInfo.avatarUrl).into(ivHeader)
            tvNickName?.text = UserUtil.getInstance().userInfo.nickname
        }else{

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getInstance().unSubscribe(this)
    }
}
