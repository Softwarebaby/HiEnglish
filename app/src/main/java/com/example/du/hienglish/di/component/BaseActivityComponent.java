package com.example.du.hienglish.di.component;

import com.example.du.hienglish.mvvm.view.BaseActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Bob Du on 2019/04/04 17:53
 */
@Subcomponent(modules = AndroidInjectionModule.class)
public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity> {
    }
}
