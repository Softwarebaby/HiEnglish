package com.example.du.hienglish;

import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.model.Video;
import com.example.du.hienglish.network.RetrofitFactory;
import com.example.du.hienglish.network.api.Api;
import com.example.du.hienglish.mvvm.model.DataInfo;
import com.example.du.hienglish.network.api.ApiException;
import com.example.du.hienglish.network.http.HttpResult;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HttpMethod {
    private Api mApi = RetrofitFactory.getInstance().API();

    public void getDataInfoList(Subscriber<List<DataInfo>> subscriber) {
        mApi.getDataInfoList()
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getVideoList(Subscriber<List<Video>> subscriber) {
        mApi.getVideoList()
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void register(Subscriber subscriber, User user) {
        mApi.register(user)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void login(Subscriber<User> subscriber, User user) {
        mApi.login(user)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getTeacherList(Subscriber<List<User>> subscriber) {
        mApi.getTeacherList()
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void updateUserStatus(Subscriber subscriber, User user) {
        mApi.updateUserStatus(user)
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     * 用来统一处理Http的status，并将HttpResult的Data部分剥离出来返回给subsciber
     * @param <T>
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if(httpResult.getStatus() != 200) {
                throw new ApiException(httpResult.getStatus());
            }
            return httpResult.getData();
        }
    }

    public static HttpMethod getInstance () {
        return Singleton.INSTANCE;
    }

    public static class Singleton {
        private static final HttpMethod INSTANCE = new HttpMethod();
    }
}
