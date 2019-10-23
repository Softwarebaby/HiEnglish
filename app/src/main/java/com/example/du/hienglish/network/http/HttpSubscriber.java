package com.example.du.hienglish.network.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.du.hienglish.mvvm.view.widget.ProgressDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

public class HttpSubscriber<T> extends Subscriber<T> {
    private static final String TAG = "HttpSubscriber";
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context mContext;

    public HttpSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        mSubscriberOnNextListener = subscriberOnNextListener;
        mContext = context;
    }

    /**
     * 订阅前开始调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 订阅完成时调用
     * 隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 发生错误时调用
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if(e instanceof SocketTimeoutException) {
            Toast.makeText(mContext, "连接超时，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else if(e instanceof ConnectException) {
            Toast.makeText(mContext, "连接异常，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onError: " + e.getMessage());
        }
        dismissProgressDialog();
    }

    /**
     * 将方法中的返回结果交给Activity或Fragment自己处理
     * @param t  创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if(mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    public void showProgressDialog() {
        ProgressDialog.show(mContext, false, "请稍候...");
    }

    public void dismissProgressDialog() {
        ProgressDialog.cancel();
    }
}
