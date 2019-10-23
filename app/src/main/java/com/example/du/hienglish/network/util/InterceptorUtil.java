package com.example.du.hienglish.network.util;

import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Bob Du on 2019/04/05 17:51
 */
public class InterceptorUtil {
    private static final String TAG = "InterceptorUtil";
    public final static Charset UTF8 = Charset.forName("UTF-8");

    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);  //设置打印数据的级别
    }

    public static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request mRequest = chain.request();
                return chain.proceed(mRequest);
            }
        };
    }

}
