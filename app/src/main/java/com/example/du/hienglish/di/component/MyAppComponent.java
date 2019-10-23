package com.example.du.hienglish.di.component;

import com.example.du.hienglish.MyApplication;
import com.example.du.hienglish.di.module.AllActivitiesModule;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Bob Du on 2019/04/04 17:54
 */
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AllActivitiesModule.class
})
public interface MyAppComponent extends AndroidInjector<MyApplication> {
}
