package com.example.du.hienglish.mvvm.viewmodel;

import com.example.du.hienglish.mvvm.view.MainActivity;

import javax.inject.Inject;

/**
 * Created by Bob Du on 2019/04/04 18:20
 */
public class MainViewModel extends BaseViewModel {
    private final MainActivity activity;

    @Inject
    public MainViewModel(MainActivity activity) {
        this.activity = activity;
    }
}
