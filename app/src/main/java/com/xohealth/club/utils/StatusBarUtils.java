package com.xohealth.club.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by xulc on 2018/12/1.
 */
public class StatusBarUtils {
    private static int statusBarHeight = 0;
    /**
     * 状态栏全透明
     * @param window
     */
    public static void setStatusBarTrans(Window window){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //由于5.0以下不可以设置状态栏文字颜色或者状态栏背景色，因此本应用中的沉浸式状态栏只支持到5.0以上
        }
    }

    /**
     * 状态栏文字颜色白色
     * 目前适配到5.0
     * @param window
     */
    public static void setStatusTextColorWhite(Window window){
        //全屏状态栏暗色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * 状态栏文字颜色黑色
     * 目前适配到5.0
     * @param window
     */
    public static void setStatusTextColorBlack(Window window){
        //状态栏全屏浅色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.setStatusBarColor(Color.parseColor("#33000000"));
        }
    }

    /**
     * 由于5.0以下不可以设置状态栏文字颜色或者状态栏背景色，建议应用中的沉浸式状态栏只支持到5.0以上
     * @return
     */
    public static boolean isTranslucentStatus(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight(Activity activity){
        if (statusBarHeight == 0){
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }


}
