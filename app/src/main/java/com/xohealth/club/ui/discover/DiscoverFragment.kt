package com.xohealth.club.ui.discover


import android.os.Bundle
import android.view.View
import android.widget.LinearLayout

import com.xohealth.club.R
import com.xohealth.club.base.BaseLazyFragment
import com.xohealth.club.ui.home.DiscoverPresenter
import com.xohealth.club.ui.home.HomePresenter
import com.xohealth.club.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_discover.*


class DiscoverFragment : BaseLazyFragment<DiscoverPresenter>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_discover
    }

    override fun initView(mRootView: View) {

    }

    override fun initInjector() {
        mFragmentComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //针对fragment fitSystemWindow不生效的问题 手动加一个View
        if (StatusBarUtils.isTranslucentStatus()){
            val lp = fillStatusBar.layoutParams
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT
            lp.height = StatusBarUtils.getStatusBarHeight(activity)
            fillStatusBar.layoutParams = lp
        }else{
            fillStatusBar.visibility = View.GONE
        }
    }
}
