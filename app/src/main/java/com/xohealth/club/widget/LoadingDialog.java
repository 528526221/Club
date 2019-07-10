package com.xohealth.club.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.xohealth.club.R;

/**
 * Desc : 等待加载
 * Created by xulc on 2018/12/16.
 */
public class LoadingDialog extends Dialog {
    private TextView tvTip;
    private String message;

    public LoadingDialog(Context context) {
        this(context,R.style.loading_dialog);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);
        tvTip = findViewById(R.id.tvTip);
        if (message == null){
            message = getContext().getString(R.string.loading);
        }
        tvTip.setText(message);
    }

    /**
     * 设置提示信息
     * @param loadingMessage
     */
    public void setLoadingMessage(String loadingMessage){
        if (loadingMessage == null){
            loadingMessage = getContext().getString(R.string.loading);
        }
        this.message = loadingMessage;
        if (tvTip != null){
            tvTip.setText(loadingMessage);
        }
    }



}
