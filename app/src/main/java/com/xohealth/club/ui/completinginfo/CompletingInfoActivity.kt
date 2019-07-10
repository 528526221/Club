package com.xohealth.club.ui.completinginfo


import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.tools.DateUtils
import com.xohealth.club.R
import com.xohealth.club.base.BaseActivity
import com.xohealth.club.base.BaseObserver
import com.xohealth.club.base.BaseResponse
import com.xohealth.club.bean.CurUserBody
import com.xohealth.club.bean.QiniuUpload
import com.xohealth.club.net.Constant
import com.xohealth.club.net.RetrofitManager
import com.xohealth.club.ui.main.MainActivity
import com.xohealth.club.utils.DateFormatUtil
import com.xohealth.club.utils.RxSchedulers
import com.xohealth.club.utils.UserUtil
import kotlinx.android.synthetic.main.activity_completing_info.*
import java.io.File
import java.util.*

/**
 * Created by xulc on 2018/12/1.
 */
class CompletingInfoActivity : BaseActivity<CompletingInfoPresenter>(), View.OnClickListener, CompletingInfoContract.View {

    private var timePicker: TimePickerView? = null
    private var selectAvatarPath: String? = null
    var body: CurUserBody? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_completing_info
    }

    override fun initInjector() {
        mActivityComponent.inject(this)
    }

    override fun initView() {
        ivCamera.setOnClickListener(this)
        tvBirthday.setOnClickListener(this)
        ivMale.setOnClickListener(this)
        ivFemale.setOnClickListener(this)
        ivNext.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0){
            ivCamera -> {
                PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                .compress(true)
                .forResult(PictureConfig.CHOOSE_REQUEST)
            }
            tvBirthday -> {
                if (timePicker == null){
                    val startDate = Calendar.getInstance()
                    //startDate.set(2013,1,1);
                    val endDate = Calendar.getInstance()
                    //endDate.set(2020,1,1);
                    //正确设置方式 原因：注意事项有说明
                    startDate.set(1949, 9, 1)
                    endDate.set(endDate.get(Calendar.YEAR),endDate.get(Calendar.MONTH),endDate.get(Calendar.DAY_OF_MONTH))
                    timePicker = TimePickerBuilder(this,object : OnTimeSelectListener{
                        override fun onTimeSelect(date: Date?, v: View?) {
                            tvBirthday.text = DateFormatUtil.getFormatDateYearMonthDay(date)
                        }

                    }).setRangDate(startDate,endDate).build()

                }
                timePicker?.show()
            }
            ivMale -> {
                ivFemale.isSelected = false
                ivMale.isSelected = true
            }
            ivFemale -> {
                ivFemale.isSelected = true
                ivMale.isSelected = false
            }
            ivNext -> {
                body = CurUserBody()
                body?.nickname = etNick.text.toString().trim()
                body?.birthday = tvBirthday.text.toString().trim()
                if (ivMale.isSelected){
                    body?.gender = 1
                }
                if (ivFemale.isSelected){
                    body?.gender = 2
                }
                if (TextUtils.isEmpty(body?.nickname)){
                    ToastUtils.showShort("昵称")
                    return
                }
                if (body?.gender == 0){
                    ToastUtils.showShort("性别")
                    return
                }
                if (TextUtils.isEmpty(body?.birthday)){
                    ToastUtils.showShort("生日")
                    return
                }

                if (TextUtils.isEmpty(selectAvatarPath) ){
                    mPresenter.next(body)
                }else{
                    mPresenter.getQiniuToken()
                }
            }
        }
    }

    override fun qiniuTokenSuccess(qiniuToken: String) {
        mPresenter.uploadImage(qiniuToken,selectAvatarPath!!)
    }

    override fun uploadSuccess(upload: QiniuUpload?) {
        body?.avatarKey = upload?.key
        mPresenter.next(body)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == PictureConfig.CHOOSE_REQUEST){
                val selectList = PictureSelector.obtainMultipleResult(data)
                selectAvatarPath = selectList[0].path
                Glide.with(this).load(File(selectAvatarPath)).into(ivCameraResult)
            }
        }
    }

    override fun completeSuccess() {
        val userInfo = UserUtil.getInstance().userInfo
        userInfo.isLogin = true
        UserUtil.getInstance().userInfo = userInfo
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
