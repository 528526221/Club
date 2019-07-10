package com.xohealth.club.base;

import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.xohealth.club.net.Constant;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by xulc on 2018/11/16.
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private Long requestStartTime;
    protected CompositeDisposable mDisposable;

    public BaseObserver(CompositeDisposable mDisposable) {
        this.mDisposable = mDisposable;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        requestStartTime = System.nanoTime();
        if (mDisposable != null){
            mDisposable.add(d);
        }
    }

    @Override
    public void onNext(@NonNull BaseResponse<T> tBaseResponse) {
        if (tBaseResponse.isStatus()){
            onSuccess(tBaseResponse);
        }else {
            onFail(tBaseResponse);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        BaseResponse<T> errorResponse = new BaseResponse<>();
        errorResponse.setStatusCode(-999);
        errorResponse.setMessage(e.getMessage());
        onFail(errorResponse);
        onComplete();
    }

    @Override
    public void onComplete() {
        Log.i(Constant.TAG,String.format("本次请求完整耗时：%.1fms",(System.nanoTime() -requestStartTime)/1e6d));
    }

    protected abstract void onSuccess(BaseResponse<T> tBaseResponse);

    protected abstract void onFail(BaseResponse<T> tBaseResponse);

}
