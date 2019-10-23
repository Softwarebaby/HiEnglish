package com.example.du.hienglish.di.module;

import com.example.du.hienglish.di.component.BaseActivityComponent;
import com.example.du.hienglish.di.scope.ActivityScope;
import com.example.du.hienglish.mvvm.view.DataActivity;
import com.example.du.hienglish.mvvm.view.LoginActivity;
import com.example.du.hienglish.mvvm.view.MainActivity;
import com.example.du.hienglish.mvvm.view.RegisterActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Bob Du on 2019/04/04 17:51
 */
@Module(subcomponents = BaseActivityComponent.class)
public abstract class AllActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributeLoginActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = DataActivityModule.class)
    abstract DataActivity contributeDataActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity contributeRegisterActivityInjector();
}
