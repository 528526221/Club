package com.xohealth.club.base;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xohealth.club.R;

/**
 * Created by xulc on 2018/11/16.
 */
public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CrashReport.initCrashReport(getApplicationContext(), "7c22ff2be7", false);
        Utils.init(this);
        CrashHandler.getsInstance().init(this);
    }

    public static Context getAppContext() {
        return context;
    }


}
