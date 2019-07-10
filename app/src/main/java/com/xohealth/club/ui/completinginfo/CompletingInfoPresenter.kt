package com.xohealth.club.ui.completinginfo

import android.util.Log
import com.blankj.utilcode.util.ToastUtils

import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.xohealth.club.base.BaseObserver
import com.xohealth.club.base.BasePresenter
import com.xohealth.club.base.BaseResponse
import com.xohealth.club.bean.CurUserBody
import com.xohealth.club.bean.QiniuUpload
import com.xohealth.club.net.RetrofitManager
import com.xohealth.club.utils.GsonUtil
import com.xohealth.club.utils.RxSchedulers

import org.json.JSONObject

import javax.inject.Inject

/**
 * Created by xulc on 2018/12/2.
 */
class CompletingInfoPresenter @Inject
constructor() : BasePresenter<CompletingInfoContract.View>(), CompletingInfoContract.Presenter {


    override fun next(body: CurUserBody?) {
        mView?.showLoading("正在同步...")
        RetrofitManager.getApiService().curUser("v1", body).compose(RxSchedulers.applySchedulers<BaseResponse<String>>()).subscribe(object : BaseObserver<String>(mDisposable) {
            override fun onSuccess(stringBaseResponse: BaseResponse<String>) {
                mView?.completeSuccess()
            }

            override fun onFail(stringBaseResponse: BaseResponse<String>) {

            }

            override fun onComplete() {
                super.onComplete()
                mView?.hideLoading()
            }
        })
    }

    override fun uploadImage(qiniuToken: String, imagePath: String) {
        mView?.showLoading("正在上传...")
        val fileKey: String? = null
        val uploadManager = UploadManager()
        uploadManager.put(imagePath, fileKey, qiniuToken,
                { key, info, res ->
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    Log.i("qiniu", "$key,\r\n $info,\r\n $res")
                    if (info.isOK) {
                        Log.i("qiniu", "Upload Success")
                        mView?.uploadSuccess(GsonUtil.parseJsonWithGson(res.toString(), QiniuUpload::class.java))
                    } else {
                        mView?.hideLoading()
                        ToastUtils.showShort("Upload Fail:"+info.error)
                        Log.i("qiniu", "Upload Fail")
                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    }
                }, null)
    }

    override fun getQiniuToken() {
        mView?.showLoading(null)
        RetrofitManager.getApiService().qiniuToken.compose(RxSchedulers.applySchedulers<BaseResponse<String>>())
                .subscribe(object : BaseObserver<String>(mDisposable) {
            override fun onSuccess(stringBaseResponse: BaseResponse<String>) {
                mView?.qiniuTokenSuccess(stringBaseResponse.data)
            }

            override fun onFail(stringBaseResponse: BaseResponse<String>) {
                mView?.hideLoading()
            }
        })
    }

}
