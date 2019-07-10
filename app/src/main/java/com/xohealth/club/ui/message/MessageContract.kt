package com.xohealth.club.ui.home

import com.xohealth.club.base.BaseContract

/**
 * Desc :
 * Created by xulc on 2018/12/16.
 */
interface MessageContract {
    interface View : BaseContract.BaseView
    interface Presenter: BaseContract.BasePresenter<MessageContract.View>
}