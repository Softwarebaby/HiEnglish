package com.example.du.hienglish.mvvm.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.view.RegisterActivity;
import com.example.du.hienglish.network.http.HttpSubscriber;
import com.example.du.hienglish.network.http.SubscriberOnNextListener;

public class RegisterViewModel extends BaseViewModel {
    private SubscriberOnNextListener registerOnNext;
    private MutableLiveData<Boolean> content;
    private final RegisterActivity activity;

    public RegisterViewModel(RegisterActivity activity) {
        this.activity = activity;
    }

    public MutableLiveData<Boolean> getContent() {
        if (content == null) {
            content = new MutableLiveData<>();
        }
        return content;
    }

    public void setContent(MutableLiveData<Boolean> content) {
        this.content = content;
    }

    public void registerUser(User user) {
        registerOnNext = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                content.setValue(true);
            }
        };
        HttpMethod.getInstance().register(new HttpSubscriber(registerOnNext, activity), user);
    }
}
