package com.xohealth.club.utils;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xohealth.club.net.Constant;

/**
 * Desc :
 * Created by xulc on 2018/12/18.
 */
public class WxApiUtils {
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    private static WxApiUtils apiUtils;

    public static WxApiUtils getInstance(){
        if (apiUtils == null){
            synchronized (WxApiUtils.class){
                if (apiUtils == null){
                    apiUtils = new WxApiUtils();
                }
            }
        }
        return apiUtils;
    }

    /**
     * 注册到微信
     * 该方法建议在启动时调用 不过你真没有调用也没什么关系
     * @param context
     */
    public void regToWx(Context context){
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, Constant.APP_ID, false);
        //将应用的appid注册到微信
        api.registerApp(Constant.APP_ID);
    }

    /**
     * 获取wxapi
     * @param context
     * @return
     */
    public IWXAPI getWxApi(Context context) {
        if (api == null) {
            regToWx(context);
        }
        return api;
    }

    /**
     * 微信登录
     */
    public void wxLogin(Context context) {
        if (getWxApi(context).isWXAppInstalled()){
            //第一步：请求CODE
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        }else{
            ToastUtils.showShort("请先安装微信");
        }
    }
}
