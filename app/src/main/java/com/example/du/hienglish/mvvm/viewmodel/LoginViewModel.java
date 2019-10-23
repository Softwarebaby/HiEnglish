package com.example.du.hienglish.mvvm.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.view.LoginActivity;
import com.example.du.hienglish.network.http.HttpSubscriber;
import com.example.du.hienglish.network.http.SubscriberOnNextListener;

/**
 * Created by Bob Du on 2019/04/09 12:38
 */
public class LoginViewModel extends BaseViewModel {
    private SubscriberOnNextListener<User> loginOnNext;
    private MutableLiveData<User> content;
    private final LoginActivity activity;

    public LoginViewModel(LoginActivity activity) {
        this.activity = activity;
    }

    public MutableLiveData<User> getContent() {
        if (content == null) {
            content = new MutableLiveData<>();
        }
        return content;
    }

    public void setContent(MutableLiveData<User> content) {
        this.content = content;
    }

    public void loginUser(User user) {
        loginOnNext = new SubscriberOnNextListener<User>() {
            @Override
            public void onNext(User user) {
                content.setValue(user);
            }
        };
        HttpMethod.getInstance().login(new HttpSubscriber<>(loginOnNext, activity), user);
    }
}
