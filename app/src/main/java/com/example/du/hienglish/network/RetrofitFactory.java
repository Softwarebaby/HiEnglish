package com.example.du.hienglish.network;

import com.example.du.hienglish.network.api.Api;
import com.example.du.hienglish.network.config.HttpConfig;
import com.example.du.hienglish.network.util.InterceptorUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bob Du on 2019/04/05 16:30
 */
public class RetrofitFactory {
    private static RetrofitFactory mRetrofitFactory;
    private static Api mApi;

    private RetrofitFactory() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.HeaderInterceptor())
                .addInterceptor(InterceptorUtil.LogInterceptor())
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  //添加gson转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mApi = mRetrofit.create(Api.class);
    }

    public static RetrofitFactory getInstance() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null) {
                    mRetrofitFactory = new RetrofitFactory();
                }
            }
        }
        return mRetrofitFactory;
    }

    public Api API() {
        return mApi;
    }
}
