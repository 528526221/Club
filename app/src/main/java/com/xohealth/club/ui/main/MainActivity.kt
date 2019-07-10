package com.xohealth.club.ui.main


import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log

import com.xohealth.club.R
import com.xohealth.club.base.BaseActivity
import com.xohealth.club.bean.RxLoginEvent
import com.xohealth.club.net.Constant
import com.xohealth.club.ui.login.LoginActivity
import com.xohealth.club.ui.discover.DiscoverFragment
import com.xohealth.club.ui.home.HomeFragment
import com.xohealth.club.ui.mall.MallFragment
import com.xohealth.club.ui.message.MessageFragment
import com.xohealth.club.ui.mine.MineFragment
import com.xohealth.club.utils.RxBus
import com.xohealth.club.utils.UserUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity<MainPresenter>(),MainContract.View {

    private var lastIntentPosition = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mPresenter?.checkLogin()
    }

    override fun initView() {
        RxBus.getInstance().addSubscribe(this, RxLoginEvent::class.java) {
            Log.i(Constant.TAG, "登录状态改变")
            if (!UserUtil.getInstance().isLogin){
                selectTab(0)
            }
        }

        val fragments = ArrayList<Fragment>()
        fragments.add(HomeFragment())
        fragments.add(MessageFragment())
        fragments.add(DiscoverFragment())
        fragments.add(MallFragment())
        fragments.add(MineFragment())
        val adapter = MainFragmentAdapter(supportFragmentManager,fragments)
        viewPager.adapter = adapter

        selectTab(0)

        for (i in 0 until lyBottom.childCount) {

            val child = lyBottom.getChildAt(i)
            child.setOnClickListener { selectTab(i) }
        }
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectTab(position)
            }
        })
        mPresenter?.checkLogin()

    }

    private fun selectTab(position: Int) {
        if (position == 4){
            //我的需要登录
            if (!UserUtil.getInstance().isLogin){
                lastIntentPosition = position
                startActivityForResult(Intent(this,LoginActivity::class.java),Constant.REQUEST_CODE_LOGIN)
                return
            }
        }
        viewPager.setCurrentItem(position,false)
        for (i in 0 until lyBottom.childCount) {
            lyBottom.getChildAt(i).isSelected = i == position
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                Constant.REQUEST_CODE_LOGIN -> {
                    selectTab(lastIntentPosition)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getInstance().unSubscribe(this)
    }
}
