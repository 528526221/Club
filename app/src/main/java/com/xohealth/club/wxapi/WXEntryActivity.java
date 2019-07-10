package com.xohealth.club.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.xohealth.club.net.Constant;
import com.xohealth.club.ui.login.LoginActivity;
import com.xohealth.club.utils.WxApiUtils;


/**
 * Desc :
 * Created by xulc on 2018/12/18.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WxApiUtils.getInstance().getWxApi(this).handleIntent(getIntent(), this);

    }


    //微信发送的请求将回调到onReq方法，发送到微信请求的响应结果将回调到onResp方法
    @Override
    public void onReq(BaseReq baseReq) {
        Log.i(Constant.TAG,baseReq.toString());

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i(Constant.TAG,baseResp.toString());
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp)baseResp).code;
//                https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code

                //第二步：通过code获取access_token
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("code",code);
                startActivity(intent);

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtils.showShort("用户拒绝授权");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.showShort("用户取消登录");
                break;
        }
        finish();
    }

}
