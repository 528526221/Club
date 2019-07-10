package com.xohealth.club.ui.completinginfo;

import com.xohealth.club.base.BaseContract;
import com.xohealth.club.bean.CurUserBody;
import com.xohealth.club.bean.QiniuUpload;

/**
 * Created by xulc on 2018/12/2.
 */
public interface CompletingInfoContract {
    interface View extends BaseContract.BaseView{
        void qiniuTokenSuccess(String qiniuToken);
        void uploadSuccess(QiniuUpload qiniuUpload);
        void completeSuccess();
    }

    interface Presenter extends BaseContract.BasePresenter<CompletingInfoContract.View>{
        void next(CurUserBody body);
        void uploadImage(String qiniuToken,String imagePath);
        void getQiniuToken();
    }
}
